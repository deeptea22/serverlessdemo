package com.hcl.adi.chf.authorizer.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AuthPolicy receives a set of allowed and denied methods and generates a valid
 * AWS policy for the API Gateway authorizer. The constructor receives the calling
 * user principal, the AWS account ID of the API owner, and an apiOptions object.
 * The apiOptions can contain an API Gateway RestApi Id, a region for the RestApi, and a
 * stage that calls should be allowed/denied for. For example
 *
 * new AuthPolicy(principalId, AuthPolicy.PolicyDocument.getDenyAllPolicy(region, awsAccountId, restApiId, stage));
 *
 * @author AyushRa
 */
public final class AuthPolicy {
	// IAM Policy Constants
	public static final String VERSION = "Version";
	public static final String STATEMENT = "Statement";
	public static final String EFFECT = "Effect";
	public static final String ACTION = "Action";
	public static final String NOT_ACTION = "NotAction";
	public static final String RESOURCE = "Resource";
	public static final String NOT_RESOURCE = "NotResource";
	public static final String CONDITION = "Condition";

	private String principalId;
	private transient AuthPolicy.PolicyDocument policyDocumentObject;

	public AuthPolicy(final String principalId, final AuthPolicy.PolicyDocument policyDocumentObject) {
		this.principalId = principalId;
		this.policyDocumentObject = policyDocumentObject;
	}

	public AuthPolicy() {
	}

	public String getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}

	/**
	 * IAM Policies use capitalized field names, but Lambda by default will serialize object members using camel case
	 *
	 * This method implements a custom serializer to return the IAM Policy as a well-formed JSON document, with the correct field names
	 *
	 * @return IAM Policy as a well-formed JSON document
	 */
	public Map<String, Object> getPolicyDocument() {
		Map<String, Object> serializablePolicy = new HashMap<>();
		serializablePolicy.put(VERSION, policyDocumentObject.version);
		Statement[] statements = policyDocumentObject.getStatement();
		@SuppressWarnings("unchecked")
		Map<String, Object>[] serializableStatementArray = new Map[statements.length];
		for (int i = 0; i < statements.length; i++) {
			Map<String, Object> serializableStatement = new HashMap<>();
			AuthPolicy.Statement statement = statements[i];
			serializableStatement.put(EFFECT, statement.effect);
			serializableStatement.put(ACTION, statement.action);
			serializableStatement.put(RESOURCE, statement.getResource());
			serializableStatement.put(CONDITION, statement.getCondition());
			serializableStatementArray[i] = serializableStatement;
		}
		serializablePolicy.put(STATEMENT, serializableStatementArray);
		return serializablePolicy;
	}

	public void setPolicyDocument(PolicyDocument policyDocumentObject) {
		this.policyDocumentObject = policyDocumentObject;
	}

	/**
	 * PolicyDocument represents an IAM Policy, specifically for the execute-api:Invoke action
	 * in the context of a API Gateway Authorizer
	 *
	 * Initialize the PolicyDocument with
	 *   the region where the RestApi is configured,
	 *   the AWS Account ID that owns the RestApi,
	 *   the RestApi identifier
	 *   and the Stage on the RestApi that the Policy will apply to
	 */
	public static final class PolicyDocument {
		static final String EXECUTE_API_ARN_FORMAT = "arn:aws:execute-api:%s:%s:%s/%s/%s/%s";

		private String version = "2012-10-17"; // override if necessary

		private Statement allowStatement;
		private Statement denyStatement;
		private List<Statement> statements;

		// context metadata
		private transient String region;
		private transient String awsAccountId;
		private transient String restApiId;
		private transient String stage;

		/**
		 * Creates a new PolicyDocument with the given context,
		 * and initializes two base Statement objects for allowing and denying access to API Gateway methods
		 *
		 * @param region the region where the RestApi is configured
		 * @param awsAccountId the AWS Account ID that owns the RestApi
		 * @param restApiId the RestApi identifier
		 * @param stage and the Stage on the RestApi that the Policy will apply to
		 */
		public PolicyDocument(String region, String awsAccountId, String restApiId, String stage) {
			this.region = region;
			this.awsAccountId = awsAccountId;
			this.restApiId = restApiId;
			this.stage = stage;
			allowStatement = Statement.getEmptyInvokeStatement("Allow");
			denyStatement = Statement.getEmptyInvokeStatement("Deny");
			this.statements = new ArrayList<>();
			statements.add(allowStatement);
			statements.add(denyStatement);
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		public AuthPolicy.Statement[] getStatement() {
			return statements.toArray(new AuthPolicy.Statement[statements.size()]);
		}

		public void allowMethod(HttpMethod httpMethod, String resourcePath) {
			addResourceToStatement(allowStatement, httpMethod, resourcePath);
		}

		public void denyMethod(HttpMethod httpMethod, String resourcePath) {
			addResourceToStatement(denyStatement, httpMethod, resourcePath);
		}

		public void addStatement(AuthPolicy.Statement statement) {
			statements.add(statement);
		}

		private void addResourceToStatement(Statement statement, HttpMethod httpMethod, String resourcePath) {
			// resourcePath must start with '/'
			// to specify the root resource only, resourcePath should be an empty string
			if (resourcePath.equals("/")) {
				resourcePath = "";
			}
			String resource = resourcePath.startsWith("/") ? resourcePath.substring(1) : resourcePath;
			String method = httpMethod == HttpMethod.ALL ? "*" : httpMethod.toString();
			statement.addResource(String.format(EXECUTE_API_ARN_FORMAT, region, awsAccountId, restApiId, stage, method, resource));
		}

		// Static methods

		/**
		 * Generates a new PolicyDocument with a single statement that allows the requested method/resourcePath
		 *
		 * @param region API Gateway region
		 * @param awsAccountId AWS Account that owns the API Gateway RestApi
		 * @param restApiId RestApi identifier
		 * @param stage Stage name
		 * @param method HttpMethod to allow
		 * @param resourcePath Resource path to allow
		 * @return new PolicyDocument that allows the requested method/resourcePath
		 */
		public static PolicyDocument getAllowOnePolicy(String region, String awsAccountId, String restApiId, String stage, HttpMethod method, String resourcePath) {
			AuthPolicy.PolicyDocument policyDocument = new AuthPolicy.PolicyDocument(region, awsAccountId, restApiId, stage);
			policyDocument.allowMethod(method, resourcePath);
			return policyDocument;

		}

		/**
		 * Generates a new PolicyDocument with a single statement that denies the requested method/resourcePath
		 *
		 * @param region API Gateway region
		 * @param awsAccountId AWS Account that owns the API Gateway RestApi
		 * @param restApiId RestApi identifier
		 * @param stage Stage name
		 * @param method HttpMethod to deny
		 * @param resourcePath Resource path to deny
		 * @return new PolicyDocument that denies the requested method/resourcePath
		 */
		public static PolicyDocument getDenyOnePolicy(String region, String awsAccountId, String restApiId, String stage, HttpMethod method, String resourcePath) {
			AuthPolicy.PolicyDocument policyDocument = new AuthPolicy.PolicyDocument(region, awsAccountId, restApiId, stage);
			policyDocument.denyMethod(method, resourcePath);
			return policyDocument;

		}

		public static AuthPolicy.PolicyDocument getAllowAllPolicy(String region, String awsAccountId, String restApiId, String stage) {
			return getAllowOnePolicy(region, awsAccountId, restApiId, stage, HttpMethod.ALL, "*");
		}

		public static PolicyDocument getDenyAllPolicy(String region, String awsAccountId, String restApiId, String stage) {
			return getDenyOnePolicy(region, awsAccountId, restApiId, stage, HttpMethod.ALL, "*");
		}

		@Override
		public String toString() {
			return "PolicyDocument [version=" + version + ", allowStatement=" + allowStatement + ", denyStatement="
					+ denyStatement + ", statements=" + statements + ", region=" + region + ", awsAccountId="
					+ awsAccountId + ", restApiId=" + restApiId + ", stage=" + stage + "]";
		}
	}

	public enum HttpMethod {
		GET, POST, PUT, DELETE, PATCH, HEAD, OPTIONS, ALL
	}

	static class Statement {
		private String effect;
		private String action;
		private Map<String, Map<String, Object>> condition;
		private List<String> resourceList;

		Statement() {}

		Statement(String effect, String action, List<String> resourceList, Map<String, Map<String, Object>> condition) {
			this.effect = effect;
			this.action = action;
			this.resourceList = resourceList;
			this.condition = condition;
		}

		public static Statement getEmptyInvokeStatement(String effect) {
			return new Statement(effect, "execute-api:Invoke", new ArrayList<>(), new HashMap<>());
		}

		public String getEffect() {
			return effect;
		}

		public void setEffect(String effect) {
			this.effect = effect;
		}

		public String getAction() {
			return action;
		}

		public void setAction(String action) {
			this.action = action;
		}

		public String[] getResource() {
			return resourceList.toArray(new String[resourceList.size()]);
		}

		public void addResource(String resource) {
			resourceList.add(resource);
		}

		public Map<String, Map<String,Object>> getCondition() {
			return condition;
		}

		public void addCondition(String operator, String key, Object value) {
			condition.put(operator, Collections.singletonMap(key, value));
		}

		@Override
		public String toString() {
			return "Statement [effect=" + effect + ", action=" + action + ", condition=" + condition + ", resourceList="
					+ resourceList + "]";
		}
	}

	@Override
	public String toString() {
		return "AuthPolicy [principalId=" + principalId + ", policyDocumentObject=" + policyDocumentObject + "]";
	}
}