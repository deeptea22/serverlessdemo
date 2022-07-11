package com.hcl.adi.chf.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.AdminAddUserToGroupRequest;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserResult;
import com.amazonaws.services.cognitoidp.model.AdminDeleteUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminDisableUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminEnableUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminGetUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminGetUserResult;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.GetGroupRequest;
import com.amazonaws.services.cognitoidp.model.GetGroupResult;
import com.amazonaws.services.cognitoidp.model.UserType;
import com.hcl.adi.chf.enums.Actions;

/**
 * This utility class will perform operations on AWS COGNITO pool
 *
 * @author AyushRa
 */
public final class CognitoUtil {
	private static final Logger LOGGER = LogManager.getLogger(CognitoUtil.class.getName());
	private static final String COGNITO_POOL_ID = Constants.COGNITO_POOL_ID;

	private CognitoUtil() {
		// private constructor
	}

	/**
	 * This method will return AWSCognitoIdentityProvider object to perform
	 * operations on AWS COGNITO pool
	 *
	 * @return
	 */
	private static AWSCognitoIdentityProvider getCognitoClient() {
		AWSCognitoIdentityProvider cognitoClient = AWSCognitoIdentityProviderClientBuilder.standard()
				.withRegion(Constants.COGNITO_POOL_REGION_NAME).build();

		return cognitoClient;
	}

	/**
	 * This method will add an user into AWS COGNITO pool and will add that user
	 * into specified group
	 *
	 * @param userName
	 * @param emailId
	 * @param groupName
	 */
	public static void addUser(final String userName, final String emailId, final String groupName) {
		AWSCognitoIdentityProvider cognitoClient = getCognitoClient();

		checkGroupExistence(groupName);

		AdminCreateUserRequest createUserRequest = new AdminCreateUserRequest().withUserPoolId(COGNITO_POOL_ID)
				.withUsername(userName).withTemporaryPassword("India@123")
				.withUserAttributes(new AttributeType().withName("email").withValue(emailId),
						new AttributeType().withName("email_verified").withValue("true"),
						new AttributeType().withName("profile").withValue("mobile"));

		AdminCreateUserResult createUserResult = cognitoClient.adminCreateUser(createUserRequest);

		UserType cognitoUser = createUserResult.getUser();
		LOGGER.info("User: " + userName + " Created on: " + cognitoUser.getUserCreateDate());

		// Now, add the user into specified group
		addUserIntoGroup(userName, groupName);
	}

	/**
	 * This method will disable an user into AWS COGNITO pool
	 *
	 * @param userName
	 */
	public static void disableUser(final String userName) {
		AWSCognitoIdentityProvider cognitoClient = getCognitoClient();
		cognitoClient
				.adminDisableUser(new AdminDisableUserRequest().withUserPoolId(COGNITO_POOL_ID).withUsername(userName));

		LOGGER.info("Disabled user: " + userName);
	}

	/**
	 * This method will enable an user into AWS COGNITO pool
	 *
	 * @param userName
	 */
	public static void enableUser(final String userName) {
		AWSCognitoIdentityProvider cognitoClient = getCognitoClient();
		cognitoClient
				.adminEnableUser(new AdminEnableUserRequest().withUserPoolId(COGNITO_POOL_ID).withUsername(userName));

		LOGGER.info("Enabled user: " + userName);
	}

	/**
	 * This method will delete an user from AWS COGNITO pool
	 *
	 * @param userName
	 */
	public static void deleteUser(final String userName) {
		AWSCognitoIdentityProvider cognitoClient = getCognitoClient();
		cognitoClient
				.adminDeleteUser(new AdminDeleteUserRequest().withUserPoolId(COGNITO_POOL_ID).withUsername(userName));

		LOGGER.info("Deleted user: " + userName);
	}

	/**
	 * This method will return the details of an user from AWS COGNITO pool
	 *
	 * @param userName
	 * @return
	 */
	public static AdminGetUserResult getUser(final String userName) {
		AWSCognitoIdentityProvider cognitoClient = getCognitoClient();
		AdminGetUserResult getUserResult = cognitoClient
				.adminGetUser(new AdminGetUserRequest().withUserPoolId(COGNITO_POOL_ID).withUsername(userName));

		return getUserResult;
	}

	/**
	 * This method will invoke the required action to perform on AWS COGNITO
	 * pool for an user
	 *
	 * @param action
	 * @param userName
	 */
	public static void performActionOnCognitoPool(final Actions action, final String userName) {
		if (action != null) {
			switch (action) {
			case DEACTIVATE:
				disableUser(userName);
				break;
			case ACTIVATE:
				enableUser(userName);
				break;
			case DELETE:
				deleteUser(userName);
				break;
			default:
				break;
			}
		}
	}

	/**
	 * This method will check the existence of the specified group into AWS
	 * COGNITO
	 *
	 * @param groupName
	 */
	private static void checkGroupExistence(final String groupName) {
		AWSCognitoIdentityProvider cognitoClient = getCognitoClient();
		GetGroupRequest getGroupRequest = new GetGroupRequest().withUserPoolId(COGNITO_POOL_ID);

		if (Constants.COGNITO_POOL_WEB_MOBILE_CLINICIAN_GROUP.equalsIgnoreCase(groupName)) {
			getGroupRequest.setGroupName(Constants.COGNITO_POOL_WEB_CLINICIAN_GROUP);
			GetGroupResult getWebGroupResult = cognitoClient.getGroup(getGroupRequest);

			if (getWebGroupResult != null) {
				LOGGER.info("Group: " + getWebGroupResult.getGroup().getGroupName() + " exists");
			}

			getGroupRequest.setGroupName(Constants.COGNITO_POOL_MOBILE_CLINICIAN_GROUP);
			GetGroupResult getMobileGroupResult = cognitoClient.getGroup(getGroupRequest);

			if (getMobileGroupResult != null) {
				LOGGER.info("Group: " + getMobileGroupResult.getGroup().getGroupName() + " exists");
			}

		} else {
			getGroupRequest.setGroupName(groupName);
			GetGroupResult getGroupResult = cognitoClient.getGroup(getGroupRequest);

			if (getGroupResult != null) {
				LOGGER.info("Group: " + getGroupResult.getGroup().getGroupName() + " exists");
			}
		}
	}

	/**
	 * This method will add the user into specified COGNITO GROUP
	 *
	 * @param userName
	 * @param groupName
	 */
	private static void addUserIntoGroup(final String userName, final String groupName) {
		AWSCognitoIdentityProvider cognitoClient = getCognitoClient();

		if (Constants.COGNITO_POOL_WEB_MOBILE_CLINICIAN_GROUP.equalsIgnoreCase(groupName)) {
			AdminAddUserToGroupRequest addUserToGroupRequest = new AdminAddUserToGroupRequest()
					.withUserPoolId(COGNITO_POOL_ID).withUsername(userName);

			addUserToGroupRequest.setGroupName(Constants.COGNITO_POOL_WEB_CLINICIAN_GROUP);
			cognitoClient.adminAddUserToGroup(addUserToGroupRequest);

			addUserToGroupRequest.setGroupName(Constants.COGNITO_POOL_MOBILE_CLINICIAN_GROUP);
			cognitoClient.adminAddUserToGroup(addUserToGroupRequest);

		} else {
			AdminAddUserToGroupRequest addUserToGroupRequest = new AdminAddUserToGroupRequest()
					.withUserPoolId(COGNITO_POOL_ID).withUsername(userName);
			addUserToGroupRequest.setGroupName(groupName);

			cognitoClient.adminAddUserToGroup(addUserToGroupRequest);
		}
	}
}