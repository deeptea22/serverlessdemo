package com.hcl.adi.chf.util;

import com.amazonaws.regions.Regions;

/**
 * This class contains constants used in the code
 *
 * @author AyushRa
 */
public final class Constants {
	private Constants() {
		// private constructor
	}

	// constants related to DB connection
	public static final String DB_DRIVER_CLASS_NAME = System.getenv("DB_DRIVER_CLASS_NAME");
	public static final String DB_PROTOCOL = System.getenv("DB_PROTOCOL");
	public static final String DB_HOSTNAME = System.getenv("DB_HOSTNAME");
	public static final String DB_PORT = System.getenv("DB_PORT");
	public static final String DB_SCHEMA_NAME = System.getenv("DB_SCHEMA_NAME");
	public static final String DB_USER_NAME = System.getenv("DB_USER_NAME");
	public static final String DB_PASSWORD = System.getenv("DB_PASSWORD");

	// constant related to sql.properties file location
	// For AWS value would be "resources/sql" & For local value would be "sql"
	public static final String SQL_PROPERTIES_FILE_LOCATION = System.getenv("SQL_PROPERTIES_FILE_LOCATION");

	// constants related to Key Store for SSL connection
	public static final String KEY_STORE_FILE = "truststore.jks";
	public static final String KEY_STORE_TYPE = "JKS";
	public static final String KEY_STORE_PASSWORD = "changeit";

	// constants for S3 signed URL generation functionality
	public static final String S3_BUCKET_NAME = System.getenv("S3_BUCKET_NAME");
	public static final String OBJECT_KEY = System.getenv("OBJECT_KEY");
	public static final String ALLOWED_TIME = System.getenv("ALLOWED_TIME");
	public static final String REGION = System.getenv("REGION");

	// constants for AWS COGNITO user pool
	public static final String COGNITO_POOL_ID = System.getenv("COGNITO_POOL_ID");
	public static final String COGNITO_POOL_REGION_NAME = System.getenv("COGNITO_POOL_REGION_NAME");
	public static final String COGNITO_POOL_SUPER_ADMIN_GROUP = "super_admin";
	public static final String COGNITO_POOL_INSTITUTION_ADMIN_GROUP = "institution_admin";
	public static final String COGNITO_POOL_WEB_CLINICIAN_GROUP = "web_clinician";
	public static final String COGNITO_POOL_MOBILE_CLINICIAN_GROUP = "mobile_clinician";
	public static final String COGNITO_POOL_WEB_MOBILE_CLINICIAN_GROUP = "BOTH";
	public static final String COGNITO_POOL_USER_STATUS_FORCE_CHANGE_PASSWORD = "FORCE_CHANGE_PASSWORD";

	// constants for AWS SQS
	public static final String QUEUE_NAME_FOR_PWD_EXPIRY_INFO = System.getenv("QUEUE_NAME_FOR_PWD_EXPIRY_INFO");
	public static final String QUEUE_NAME_FOR_UPDATE_CLINICIAN = System.getenv("QUEUE_NAME_FOR_UPDATE_CLINICIAN");
	public static final String QUEUE_NAME_FOR_AUDIT_LOGS = System.getenv("QUEUE_NAME_FOR_AUDIT_LOGS");
	public static final String REGION_NAME_FOR_INST_SIDE_QUEUE = System.getenv("REGION_NAME_FOR_INST_SIDE_QUEUE");

	// constants for AWS SES
	public static final String FROM = "AyushRa@hcl.com";
	public static final String SUBJECT = "Password about to expire";
	public static final String MAILBODY_STATIC = "Your password would be expired on ";

	// constants for contact-us page
	public static final String CONTACT_US_FROM_EMAIL = System.getenv("CONTACT_US_FROM_EMAIL");
	public static final String CONTACT_US_TO_EMAIL = System.getenv("CONTACT_US_TO_EMAIL");
	public static final String CONTACT_US_CUSTOM_SUBJECT = System.getenv("CONTACT_US_CUSTOM_SUBJECT");
	public static final String CONTACT_US_CUSTOM_BODY = System.getenv("CONTACT_US_CUSTOM_BODY");
	public static final String CONTACT_US_USERID_PREFIX = "@UserId";
	public static final String CONTACT_US_SUBJECT_PREFIX = "@Subject";
	public static final String CONTACT_US_BODY_PREFIX = "@Body";

	//constants for provider details 
	public static final String PROVIDER_DOCTOR_NAME = "doctorName";
	public static final String PROVIDER_DOCTOR_EMAIL = "doctorEmail";
	public static final String PROVIDER_CONTACT_NO = "doctorContactNumber";
	public static final String PROVIDER_OTHER_CONTACT_NUMBER = "otherContactNumber";
	public static final String HOSPITAL_AFFILATION = "hospitalAffilation";
	public static final String PROVIDER_NAME = "providerName";



	// constants related to query parameters that we are expecting in request
	public static final String QUERY_PARAM_INSTITUTION_ID = "institutionId";
	public static final String QUERY_PARAM_ADMIN_ID = "adminId";
	public static final String QUERY_PARAM_EMAIL_ID = "emailId";
	public static final String QUERY_PARAM_START_DATE = "startDate";
	public static final String QUERY_PARAM_END_DATE = "endDate";
	public static final String INPUT_DATE_FORMAT = "yyyy-MM-dd";
	public static final String QUERY_PARAM_LOG_TYPE = "logType";
	public static final String QUERY_PARAM_TYPE = "type";
	public static final String QUERY_PARAM_PWD_EXPIRE_IN_DAYS = "pwdExpireInDays";
	public static final String QUERY_PARAM_PWD_EXPIRY_NOTIFICATION_START_IN_DAYS = "pwdExpiryNotificationStartInDays";
	public static final String QUERY_PARAM_PATIENT_ID = "patientId";
	public static final String QUERY_PARAM_CLINICIAN_ID = "clinicianId";
	public static final String QUERY_PARAM_PATIENT_WATCHLIST = "patientWatchList";
	public static final String QUERY_PARAM_PATIENT_THRESHOLDS = "patientThresholds";
	public static final String QUERY_PARAM_PATIENT_ACTIONS = "patientActions";
	public static final String QUERY_PARAM_NEW_READINGS = "newReadings";
	public static final String QUERY_PARAM_DEVICE_DATA_ID = "deviceDataId";
	public static final String QUERY_PARAM_MRN_PATTERN = "mrnPattern";
	public static final String QUERY_PARAM_PAGE_START_INDEX = "pageStartIndex";
	public static final String QUERY_PARAM_PAGE_COUNT = "pageCount";
	public static final String QUERY_PARAM_CHF_PATIENT_ID = "chfPatientId";
	public static final String QUERY_PARAM_PACKET_ID = "pkId";
	public static final String QUERY_PARAM_READING_ACTIONS = "readingActions";
	public static final String QUERY_PARAM_MARK_AS_BASELINE = "markAsBaseline";
	public static final String QUERY_PARAM_DISCARD_READING = "discardReading";
	public static final String QUERY_PARAM_UPDATED_BY = "updatedBy";



	// constants related to block/unblock admin functionality
	public static final String LOGIN_SUCCESS = "Success";
	public static final String LOGIN_FAILURE = "Failure";
	public static final String AUTO_UNBLOCK = "auto_unblock";

	// constant related to update user profile functionality
	public static final String PROFILE_UPDATED = "profileupdated";

	// constant to identify the current region - we will use it while creating AWS services client and to pass the region name while listening from queue
	public static final Regions CURRENT_REGION = Regions.fromName(System.getenv("AWS_REGION"));

	// constant to specify institution id for super admin
	public static final Integer SA_INSTITUTION_ID = 0;

	// constant to specify audit log type
	public static final String SUPER_ADMIN_ONLY_LOGS = "ADI";

	// constant to specify action on institution threshold policy
	public static final String TOGGLE = "Toggle";
	public static final String POLICY = "Policy";


	// constant to check institution status for the convenience of activate/deactivate toggle on UI
	public static final String INSTITUTION_STATUS_ACTIVE = "A";

	public  static final String EMAIL_REGION=System.getenv("EMAIL_REGION");

	public static final String ACTIVITY_NO_ENTRY_DB = "ACTIVITY_NO_ENTRY_DB";

	// constants for clinician dashboard landing page
	public static final String _BLANK = " ";
	public static final String _DELIMITER = "_";
	public static final String _SINGLE_QUOATE = "'";

	// constants of ADI module APIs
	public static final String URL_TO_GET_INSTITUTION_DETAILS_BY_INSTITUTION_ID = System.getenv("URL_TO_GET_INSTITUTION_DETAILS_BY_INSTITUTION_ID");
	public static final String URL_TO_GET_MAXIMUM_DETAILS_OF_ADMIN_BY_EMAIL_ID = System.getenv("URL_TO_GET_MAXIMUM_DETAILS_OF_ADMIN_BY_EMAIL_ID");
	public static final String URL_TO_GET_ADMIN_LIST_BY_INSTITUTION_ID = System.getenv("URL_TO_GET_ADMIN_LIST_BY_INSTITUTION_ID");
	public static final String URL_TO_UPDATE_PWD_UPDATED_DATE_IN_ADMINS = System.getenv("URL_TO_UPDATE_PWD_UPDATED_DATE_IN_ADMINS");
	public static final String URL_TO_UPDATE_IS_TNC_ACCEPTED_IN_ADMINS = System.getenv("URL_TO_UPDATE_IS_TNC_ACCEPTED_IN_ADMINS");
	public static final String URL_TO_UPDATE_ADMIN_LOGIN_ATTEMPT = System.getenv("URL_TO_UPDATE_ADMIN_LOGIN_ATTEMPT");

	// Lambda functions name - to be used while invoking lambda from another lambda
	public static final String LAMBDA_UPDATE_CLINICIAN_LOGIN_ATTEMPT = System.getenv("LAMBDA_UPDATE_CLINICIAN_LOGIN_ATTEMPT");
	public static final String LAMBDA_GET_CLINICIAN_BY_EMAIL_ID = System.getenv("LAMBDA_GET_CLINICIAN_BY_EMAIL_ID");
	public static final String LAMBDA_GET_CLINICIAN_AND_ITS_PWD_EXPIRY_INFO_BY_EMAIL_ID_AND_INSTITUTION_SPECIFIC_PARAMS = System.getenv("LAMBDA_GET_CLINICIAN_AND_ITS_PWD_EXPIRY_INFO_BY_EMAIL_ID_AND_INSTITUTION_SPECIFIC_PARAMS");
	public static final String LAMBDA_GET_CLINICIAN_LIST_BY_INSTITUTION_ID = System.getenv("LAMBDA_GET_CLINICIAN_LIST_BY_INSTITUTION_ID");
	public static final String LAMBDA_UPDATE_PWD_UPDATED_DATE_IN_CLINICIAN = System.getenv("LAMBDA_UPDATE_PWD_UPDATED_DATE_IN_CLINICIAN");
	public static final String LAMBDA_UPDATE_IS_TNC_ACCEPTED_IN_CLINICIAN = System.getenv("LAMBDA_UPDATE_IS_TNC_ACCEPTED_IN_CLINICIAN");

	public static final String PASSWORD_CHANGE_EMAIL_SUBJECT = System.getenv("PASSWORD_CHANGE_EMAIL_SUBJECT");
	public static final String PASSWORD_CHANGE_EMAIL_BODY = System.getenv("PASSWORD_CHANGE_EMAIL_BODY");
	public static final String PASSWORD_CHANGE_FROM_EMAIL_ID = System.getenv("PASSWORD_CHANGE_FROM_EMAIL_ID");

	public static final String  RESPONSE_PATIENT_REORD_COUNT = "recordCount";
	public static final String  RESPONSE_PATIENT_COMMENTS_LIST = "patientComments";
	
	// File Upload and parse 
	public static final String CREATED_BY = "createdBy";
	public static final String FILE_OBJECT_KEY = "objectKey";
	public static final String ENCODING = "UTF_8";
	public static final String LINE_SPLITTER = "\\r?\\n";
	public static final String FILE_RECEIVED = "FILE_RECEIVED";
	public static final String FILE_PARSED = "FILE_PARSED";
	public static final String ERROR = "ERROR";
	
	// Max no of records allowed in csv file 
	public static final Integer MAX_RECORD_COUNT = 1500;

	// constant to check Organization status for the convenience of activate/deactivate toggle on UI
	public static final String ORGANIZATION_STATUS_ACTIVE = "A";

	// constant to specify institution id for super admin
	public static final Integer SA_ORGANIZATION_ID = 0;
	
	public static final String QUERY_PARAM_ORGANIZATION_ID = "organizationId";
	
	public static final String QUERY_PARAM_ORGANIZATION_TYPE = "organizationType";

}