package com.hcl.adi.chf.util;

/**
 * This class contains name of columns related to the tables in database
 *
 * @author AyushRa
 */
public final class TableConstants {
	private TableConstants() {
		// private constructor
	}

	// institution table's column name
	public static final String INSTITUTION_ID = "institution_id";
	public static final String INSTITUTION_NAME = "institution_name";
	public static final String INSTITUTION_ADDRESS = "address";
	public static final String INSTITUTION_CONTACT_PERSON = "contact_person";
	public static final String INSTITUTION_CONTACT_NUMBER = "contact_number";
	public static final String INSTITUTION_STATUS = "institution_status";
	public static final String INSTITUTION_DELETE_MARKER = "delete_marker";
	public static final String INSTITUTION_PWD_EXPIRE_IN_DAYS = "pwd_expire_in_days";
	public static final String INSTITUTION_PWD_EXPIRY_NOTIFICATION_START_IN_DAYS = "pwd_expiry_notification_start_in_days";
	public static final String INSTITUTION_CREATED_BY = "created_by";
	public static final String INSTITUTION_CREATED_TIMESTAMP = "created_timestamp";
	public static final String INSTITUTION_UPDATED_BY = "updated_by";
	public static final String INSTITUTION_UPDATED_TIMESTAMP = "updated_timestamp";

	// alias column's name with institution detail to avoid duplicate names
	public static final String INSTITUTION_PWD_POLICY_DELETE_MARKER = "pwd_policy_delete_marker";
	public static final String INSTITUTION_PWD_POLICY_CREATED_BY = "pwd_policy_created_by";
	public static final String INSTITUTION_PWD_POLICY_CREATED_TIMESTAMP = "pwd_policy_created_timestamp";
	public static final String INSTITUTION_PWD_POLICY_UPDATED_BY = "pwd_policy_updated_by";
	public static final String INSTITUTION_PWD_POLICY_UPDATED_TIMESTAMP = "pwd_policy_updated_timestamp";
	public static final String INSTITUTION_TNC_CREATED_BY = "tnc_created_by";
	public static final String INSTITUTION_TNC_CREATED_TIMESTAMP = "tnc_created_timestamp";
	public static final String INSTITUTION_TNC_UPDATED_BY = "tnc_updated_by";
	public static final String INSTITUTION_TNC_UPDATED_TIMESTAMP = "tnc_updated_timestamp";
	public static final String INSTITUTION_TNC_DELETE_MARKER = "tnc_delete_marker";
	public static final String INSTITUTION_DATA_ARCHIVAL_POLICY_DELETE_MARKER = "data_archival_policy_delete_marker";
	public static final String INSTITUTION_DATA_ARCHIVAL_POLICY_CREATED_BY = "data_archival_policy_created_by";
	public static final String INSTITUTION_DATA_ARCHIVAL_POLICY_CREATED_TIMESTAMP = "data_archival_policy_created_timestamp";
	public static final String INSTITUTION_DATA_ARCHIVAL_POLICY_UPDATED_BY = "data_archival_policy_updated_by";
	public static final String INSTITUTION_DATA_ARCHIVAL_POLICY_UPDATED_TIMESTAMP = "data_archival_policy_updated_timestamp";

	// admins table's column name
	public static final String ADMIN_ID = "admin_id";
	public static final String ADMIN_INSTITUTION_ID = "institution_id";
	public static final String ADMIN_EMAIL_ID = "email_id";
	public static final String ADMIN_FIRST_NAME = "first_name";
	public static final String ADMIN_LAST_NAME = "last_name";
	public static final String ADMIN_EMPLOYEE_ID = "employee_id";
	public static final String ADMIN_POOL_ID = "pool_id";
	public static final String ADMIN_STATUS = "status";
	public static final String ADMIN_TYPE = "type";
	public static final String ADMIN_IS_DEFAULT = "is_default";
	public static final String ADMIN_PORTAL_ACCESS = "portal_access";
	public static final String ADMIN_LOCATION = "location";
	public static final String ADMIN_DELETE_MARKER = "delete_marker";
	public static final String ADMIN_IS_TNC_ACCEPTED = "is_tnc_accepted";
	public static final String ADMIN_PWD_UPDATED_DATE = "pwd_updated_date";
	public static final String ADMIN_LOCALE = "locale";
	public static final String ADMIN_TIMEZONE = "timezone";
	public static final String ADMIN_RETRY_LOGIN_ATTEMPT_COUNTER = "retry_login_attempt_counter";
	public static final String ADMIN_LAST_LOGIN_ATTEMPT_TIMESTAMP = "last_login_timestamp";
	public static final String ADMIN_CREATED_BY = "created_by";
	public static final String ADMIN_CREATED_TIMESTAMP = "created_timestamp";
	public static final String ADMIN_UPDATED_BY = "updated_by";
	public static final String ADMIN_UPDATED_TIMESTAMP = "updated_timestamp";

	// temporary generated columns to get password expiry information
	public static final String PWD_EXPIRY_DATE = "pwd_expiry_date";
	public static final String PWD_EXPIRY_NOTIFICATION_START_DATE = "pwd_expiry_notification_start_date";
	public static final String SHOW_NOTIFICATION = "show_notification";
	public static final String PWD_EXPIRED = "pwd_expired";
	public static final String DAYS_LEFT_IN_PWD_EXPIRATION = "days_left_in_pwd_expiration";

	// temporary generated columns to get the latest timeStamps from device_data table
	public static final String BASELINE_LATEST_TIMESTAMP = "baseline_latest_timeStamp";
	public static final String NORMAL_LATEST_TIMESTAMP = "normal_latest_timeStamp";
	public static final String SECOND_NORMAL_LATEST_TIMESTAMP = "second_normal_latest_timeStamp";	
	public static final String PATIENT_READING_COUNT = "readings_count";	

	// institution_pwd_policy_mgmt table's column name
	public static final String PWD_POLICY_ID = "pwd_policy_id";
	public static final String PWD_POLICY_INSTITUTION_ID = "institution_id";
	public static final String PWD_POLICY_ROTATION_IN_DAYS = "pwd_rotation_in_days";
	public static final String PWD_POLICY_MIN_LENGTH = "pwd_min_length";
	public static final String PWD_POLICY_MAX_LENGTH = "pwd_max_length";
	public static final String PWD_POLICY_IS_CAPS_ALLOWED = "is_caps_allowed";
	public static final String PWD_POLICY_IS_LOWER_ALLOWED = "is_lower_allowed";
	public static final String PWD_POLICY_IS_NUMERIC_ALLOWED = "is_numeric_allowed";
	public static final String PWD_POLICY_IS_SPL_CHAR_ALLOWED = "is_spl_char_allowed";
	public static final String PWD_POLICY_RETRY_LOGIN_ATTEMPTS_ALLOWED = "retry_login_attempts_allowed";
	public static final String PWD_POLICY_HISTORY = "pwd_history";
	public static final String PWD_POLICY_PROHIBITED_PASSWORDS = "prohibited_passwords";
	public static final String PWD_POLICY_STATUS = "pwd_policy_status";
	public static final String PWD_POLICY_DELETE_MARKER = "delete_marker";
	public static final String PWD_POLICY_CREATED_BY = "created_by";
	public static final String PWD_POLICY_CREATED_TIMESTAMP = "created_timestamp";
	public static final String PWD_POLICY_UPDATED_BY = "updated_by";
	public static final String PWD_POLICY_UPDATED_TIMESTAMP = "updated_timestamp";

	// data_archival_policy_mgmt table's column name
	public static final String DATA_ARCHIVAL_POLICY_ID = "data_archival_policy_id";
	public static final String DATA_ARCHIVAL_INSTITUTION_ID = "institution_id";
	public static final String DATA_ARCHIVAL_PERIOD = "archival_period_in_months";
	public static final String DATA_ARCHIVAL_FREQUENCY = "auto_archival_frequency";
	public static final String DATA_ARCHIVAL_AUTO_LOG_OFF_TIME = "auto_log_off_time_in_minutes";
	public static final String DATA_ARCHIVAL_DURATION_TO_STORE_AUDIT_LOGS = "duration_to_store_audit_logs_in_months";
	public static final String DATA_ARCHIVAL_POLICY_STATUS = "data_archival_policy_status";
	public static final String DATA_ARCHIVAL_DELETE_MARKER = "delete_marker";
	public static final String DATA_ARCHIVAL_CREATED_BY = "created_by";
	public static final String DATA_ARCHIVAL_CREATED_TIMESTAMP = "created_timestamp";
	public static final String DATA_ARCHIVAL_UPDATED_BY = "updated_by";
	public static final String DATA_ARCHIVAL_UPDATED_TIMESTAMP = "updated_timestamp";

	// healthcare_provider and patient_provider_mapping table column's name
	public static final String PATIENT_PROVIDER_ID = "provider_id";
	public static final String PATIENT_PROVIDER_MAPPING_ID = "patient_provider_mapping_id";
	public static final String PROVIDER_NAME = "provider_name";
	public static final String HOSPITAL_AFFILATION = "hospital_affilation";
	public static final String PROVIDER_DOCTOR_NAME = "doctor_name";
	public static final String PROVIDER_CONTACT_NO = "doctor_contact_no";
	public static final String PROVIDER_DOCTOR_EMAIL= "doctor_email";
	public static final String PROVIDER_OTHER_CONTACT_NUMBER = "other_contact_number";
	public static final String IS_PROVIDER_DEFAULT = "is_default";

	// terms_n_conditions table's column name
	public static final String TNC_ID = "tnc_id";
	public static final String TNC_INSTITUTION_ID = "institution_id";
	public static final String TNC_CREATED_BY = "created_by";
	public static final String TNC_CREATED_TIMESTAMP = "created_timestamp";
	public static final String TNC_UPDATED_BY = "updated_by";
	public static final String TNC_UPDATED_TIMESTAMP = "updated_timestamp";
	public static final String TNC_STATUS = "tnc_status";
	public static final String TNC_DELETE_MARKER = "delete_marker";
	public static final String TNC_TEXT = "tnc_text";

	// help_center table's column name
	public static final String HELP_CENTER_ID = "help_id";
	public static final String HELP_CENTER_HELP_TOPIC = "help_topic";
	public static final String HELP_CENTER_HELP_DESC = "help_desc";
	public static final String HELP_CENTER_HELP_URL = "help_url";
	public static final String HELP_CENTER_HELP_TYPE = "help_type";
	public static final String HELP_CENTER_CREATED_BY = "created_by";
	public static final String HELP_CENTER_CREATED_TIMESTAMP = "created_timestamp";
	public static final String HELP_CENTER_UPDATED_BY = "updated_by";
	public static final String HELP_CENTER_UPDATED_TIMESTAMP = "updated_timestamp";

	// audit_logs table's column name
	public static final String AUDIT_LOG_ID = "log_id";
	public static final String AUDIT_LOG_INSTITUTION_ID = "institution_id";
	public static final String AUDIT_LOG_USER_TYPE = "user_type";
	public static final String AUDIT_LOG_ACTIVITY = "activity";
	public static final String AUDIT_LOG_CREATED_BY = "created_by";
	public static final String AUDIT_LOG_CREATED_TIMESTAMP = "created_timestamp";

	// device_data table's column name
	public static final String DEVICE_PACKET_ID = "pk_id";
	public static final String DEVICE_DATA_MSG_ID = "msg_id";
	public static final String DEVICE_DATA_INSTITUTION_ID = "institution_id";
	public static final String DEVICE_DATA_PATIENT_ID = "patient_id";
	public static final String DEVICE_DATA_DEVICE_ID = "device_id";
	public static final String DEVICE_DATA_ECG = "ecg";
	public static final String DEVICE_DATA_ECG_URL = "ecg_url";
	public static final String DEVICE_DATA_HEART_SOUND_URL = "heart_sound_url";
	public static final String DEVICE_DATA_STATUS = "status";
	public static final String DEVICE_DATA_READING_DATE = "reading_date";
	public static final String DEVICE_DATA_IS_BASELINE_READING = "is_baseline_reading";
	public static final String DEVICE_DATA_CREATED_BY = "created_by";
	public static final String DEVICE_DATA_CREATED_TIMESTAMP = "created_timestamp";
	public static final String DEVICE_DATA_UPDATED_BY = "updated_by";
	public static final String DEVICE_DATA_UPDATED_TIMESTAMP = "updated_timestamp";

	// alert_data table's column name
	public static final String ALERT_DATA_ID = "alert_data_id";
	public static final String ALERT_DATA_MSG_ID = "msg_id";
	public static final String ALERT_DATA_SEVERITY = "severity";
	public static final String ALERT_DATA_MSG = "msg";
	public static final String ALERT_DATA_BREACH_PARAM = "breach_param";
	public static final String ALERT_DATA_BREACH_TEXT = "breach_text";
	public static final String ALERT_DATA_IS_ACTIVE = "is_active";

	// patient_vitals table's column name
	public static final String PATIENT_VITALS_ID = "patient_vitals_id";
	public static final String PATIENT_VITALS_PATIENT_ID = "patient_id";
	public static final String PATIENT_VITALS_SYSTOLIC_BP = "systolic_bp";
	public static final String PATIENT_VITALS_DIASTOLIC_BP = "diastolic_bp";
	public static final String PATIENT_VITALS_WEIGHT = "weight";
	public static final String PATIENT_VITALS_TEMPERATURE = "temperature";
	public static final String PATIENT_VITALS_CUSTOM_VITALS = "custom_vitals";
	public static final String PATIENT_VITALS_CREATED_BY = "created_by";
	public static final String PATIENT_VITALS_CREATED_TIMESTAMP = "created_timestamp";
	public static final String PATIENT_VITALS_UPDATED_BY = "updated_by";
	public static final String PATIENT_VITALS_UPDATED_TIMESTAMP = "updated_timestamp";

	// patient_threshold table's column name
	public static final String PATIENT_THRESHOLD_ID = "threshold_id";
	public static final String PATIENT_THRESHOLD_PATIENT_ID = "patient_id";
	public static final String PATIENT_THRESHOLD_VALUES = "threshold_values";
	public static final String PATIENT_THRESHOLD_CREATED_BY = "created_by";
	public static final String PATIENT_THRESHOLD_UPDATED_BY = "updated_by";
	public static final String PATIENT_THRESHOLD_CREATED_TIMESTAMP = "created_timestamp";
	public static final String PATIENT_THRESHOLD_UPDATED_TIMESTAMP = "updated_timestamp";

	//  threshold_policy table's column name
	public static final String THRESHOLD_POLICY_ID = "threshold_policy_id";
	public static final String THRESHOLD_POLICY_INSTITUTION_ID = "institution_id";
	public static final String THRESHOLD_POLICY = "threshold_policy";
	public static final String THRESHOLD_POLICY_STATUS = "status";
	public static final String THRESHOLD_POLICY_CREATED_BY = "created_by";
	public static final String THRESHOLD_POLICY_CREATED_TIMESTAMP = "created_timestamp";
	public static final String THRESHOLD_POLICY_UPDATED_BY = "updated_by";
	public static final String THRESHOLD_POLICY_UPDATED_TIMESTAMP = "updated_timestamp";

	// help_center_master table's column name
	public static final String CONTACT_US_SUBJECT = "subject";
	public static final String CONTACT_US_MASTER_ID = "master_id";

	// clinician table's column name
	public static final String CLINICIAN_ID = "clinician_id";
	public static final String CLINICIAN_INSTITUTION_ID = "institution_id";
	public static final String CLINICIAN_EMAIL_ID = "email_id";
	public static final String CLINICIAN_FIRST_NAME = "first_name";
	public static final String CLINICIAN_LAST_NAME = "last_name";
	public static final String CLINICIAN_EMPLOYEE_ID = "employee_id";
	public static final String CLINICIAN_POOL_ID = "pool_id";
	public static final String CLINICIAN_STATUS = "status";
	public static final String CLINICIAN_TYPE = "type";
	public static final String CLINICIAN_PORTAL_ACCESS = "portal_access";
	public static final String CLINICIAN_LOCATION = "location";
	public static final String CLINICIAN_DELETE_MARKER = "delete_marker";
	public static final String CLINICIAN_IS_TNC_ACCEPTED = "is_tnc_accepted";
	public static final String CLINICIAN_PWD_UPDATED_DATE = "pwd_updated_date";
	public static final String CLINICIAN_LOCALE = "locale";
	public static final String CLINICIAN_TIMEZONE = "timezone";
	public static final String CLINICIAN_CREATED_BY = "created_by";
	public static final String CLINICIAN_CREATED_TIMESTAMP = "created_timestamp";
	public static final String CLINICIAN_UPDATED_BY = "updated_by";
	public static final String CLINICIAN_UPDATED_TIMESTAMP = "updated_timestamp";
	public static final String CLINICIAN_RETRY_LOGIN_ATTEMPT_COUNTER = "retry_login_attempt_counter";
	public static final String CLNICIAN_LAST_LOGIN_TIMESTAMP = "last_login_timestamp";

	// patient_phi table's column name
	public static final String PATIENT_ID = "patient_id";
	public static final String CHF_PATIENT_ID = "chf_patient_id";
	public static final String PATIENT_FIRST_NAME = "first_name";
	public static final String PATIENT_LAST_NAME = "last_name";
	public static final String PATIENT_GENDER = "gender";
	public static final String PATIENT_CONTACT_NO = "contact_no";
	public static final String PATIENT_DOB = "dob";
	public static final String PATIENT_ZIP = "zip";
	public static final String PATIENT_SSN = "ssn";
	public static final String PATIENT_ADDRESS = "address";
	public static final String PATIENT_INSTITUTION_ID = "institution_id";
	public static final String PATIENT_MR_NO = "mr_no";
	public static final String PATIENT_DETAILS_JSON = "patient_details_json";
	public static final String IS_THRESHOLD_SET = "is_threshold_set";
	public static final String IS_ACTION_OPEN = "is_action_open";
	public static final String PATIENT_DELETE_MARKER = "delete_marker";
	public static final String PATIENT_CREATED_BY = "created_by";
	public static final String PATIENT_CREATED_TIMESTAMP = "created_timestamp";
	public static final String PATIENT_UPDATED_BY = "updated_by";
	public static final String PATIENT_UPDATED_TIMESTAMP = "updated_timestamp";

	// temporary generated columns to get patient count from patient_phi table
	public static final String PATIENT_COUNT = "patient_count";

	// derived_data table's column name
	public static final String DERIVED_DATA_ID = "derived_data_id";
	public static final String DERIVED_DATA_MSG_ID = "msg_id";
	public static final String DERIVED_DATA_SUPINE_TIDAL_VOL = "supine_tidal_vol";
	public static final String DERIVED_DATA_SUPINE_RESPIRATION_RATE = "supine_respiration_rate";
	public static final String DERIVED_DATA_SUPINE_RESPIRATION_RATE_TREND = "supine_respiration_rate_trend";
	public static final String DERIVED_DATA_SUPINE_RR_TV = "supine_rr_tv";
	public static final String DERIVED_DATA_FOWLER_TIDAL_VOL = "fowler_tidal_vol";
	public static final String DERIVED_DATA_FOWLER_RESPIRATION_RATE = "fowler_respiration_rate";
	public static final String DERIVED_DATA_FOWLER_RESPIRATION_RATE_TREND = "fowler_respiration_rate_trend";
	public static final String DERIVED_DATA_FOWLER_RR_TV = "fowler_rr_tv";
	public static final String DERIVED_DATA_DELTA_Z = "delta_z";
	public static final String DERIVED_DATA_S3_ENERGY = "s3_energy";
	public static final String DERIVED_DATA_HEART_RATE = "heart_rate";
	public static final String DERIVED_DATA_HEART_RATE_TREND = "heart_rate_trend";
	public static final String DERIVED_DATA_VITAL_TREND = "vital_trend";
	public static final String DERIVED_DATA_MEASUREMENT_TREND = "measurement_trend";
	public static final String DERIVED_DATA_READING_COMPLIANCE = "reading_compliance";
	public static final String DERIVED_DATA_S3_TREND = "s3_trend";
	public static final String DERIVED_DATA_TIDAL_VOL_TREND = "tidal_vol_trend";
	public static final String DERIVED_DATA_THORACIC_IMPEDANCE = "thoracic_impedance";
	public static final String DERIVED_DATA_THORACIC_IMPEDANCE_TREND = "thoracic_impedance_trend";
	public static final String DERIVED_DATA_CREATED_BY = "created_by";
	public static final String DERIVED_DATA_CREATED_TIMESTAMP = "created_timestamp";
	public static final String DERIVED_DATA_UPDATED_BY = "updated_by";
	public static final String DERIVED_DATA_UPDATED_TIMESTAMP = "updated_timestamp";

	// cloumns to render values on clinician's dashboard landing page
	public static final String PATIENT_ID_PD = "patient_id";
	public static final String INSTITUTION_ID_PD = "institution_id";
	public static final String LAST_READ = "lastread";
	public static final String HEART_RATE = "heart_rate";
	public static final String HEART_RATE_TREND = "heart_rate_trend";
	public static final String RESPIRATION_RATE = "respiration_rate";
	public static final String RESPIRATION_RATE_TREND = "respiration_rate_trend";
	public static final String S3_TREND = "s3_trend";
	public static final String TIDAL_VOL_TREND = "tidal_vol_trend";
	public static final String THORACIC_IMPEDANCE_TREND = "thoracic_impedance_trend";
	public static final String READING_COMPLIANCE = "reading_compliance";
	public static final String THRESHOLD_ALARM = "threshold_alarm";
	public static final String MSG_ID = "msg_id";
	public static final String CHF_PATIENT_ID_PD = "chf_patient_id";
	public static final String QT_TREND = "qt_trend";
	public static final String QT_WIDTH = "qt_width";
	public static final String QRS_TREND = "qrs_trend";
	public static final String QRS_WIDTH = "qrs_width";
	public static final String ABNORMAL_RHYTHM = "abnormal_rhythm";
	public static final String DAILY_COMPLIANCE = "daily_compliance";
	public static final String PERCETAGE = "percentage";
	public static final String SYSTEM_ID = "system_id";
	public static final String MEASUREMENT_ID = "measurement_id";

	// compliance_policy table's column name
	public static final String COMPLIANCE_PK_ID = "pk_id";
	public static final String COMPLIANCE_INSTITUTION_ID = "institution_id";
	public static final String COMPLIANCE_PERIOD = "compliance_period";
	public static final String COMPLIANCE_STATUS = "status";
	public static final String COMPLIANCE_CREATED_BY = "created_by";
	public static final String COMPLIANCE_CREATED_TIMESTAMP = "created_timestamp";
	public static final String COMPLIANCE_UPDATED_BY = "updated_by";
	public static final String COMPLIANCE_UPDATED_TIMESTAMP = "updated_timestamp";

	// device_inventory table's column name
	public static final String DEVICE_INVENTORY_KIT_ID = "kit_id";
	public static final String DEVICE_LABEL_ID = "device_label_id";
	public static final String WEARABLE_FIRMWARE_VERSION = "wearable_fw_version";

	// patient_device_mapping table's column name
	public static final String PATIENT_DEVICE_MAPPING_ID = "mapping_id";
	public static final String PATIENT_DEVICE_MAPPING_SYSTEM_ID = "system_id";
	public static final String PATIENT_DEVICE_MAPPING_KIT_ID = "kit_id";
	public static final String PATIENT_DEVICE_MAPPING_PATIENT_ID = "patient_id";
	public static final String PATIENT_DEVICE_MAPPING_STATUS = "mapping_status";
	public static final String PATIENT_DEVICE_MAPPING_CREATED_BY = "created_by";
	public static final String PATIENT_DEVICE_MAPPING_CREATED_TIMESTAMP = "created_timestamp";
	public static final String PATIENT_DEVICE_MAPPING_UPDATED_BY = "updated_by";
	public static final String PATIENT_DEVICE_MAPPING_UPDATED_TIMESTAMP = "updated_timestamp";

	// chf_patient_id_policy table's column name
	public static final String CHF_PATIENT_ID_POLICY_PK_ID = "pk_id";
	public static final String CHF_PATIENT_ID_POLICY_INSTITUTION_ID = "institution_id";
	public static final String CHF_PATIENT_ID_POLICY_IS_EMR_EHR_ENABLED = "is_emr_ehr_enabled";
	public static final String CHF_PATIENT_ID_POLICY_LABEL = "label";
	public static final String CHF_PATIENT_ID_POLICY_LABEL_RULE = "label_rule";
	public static final String CHF_PATIENT_ID_POLICY_LABEL_SEPARATOR = "label_separator";
	public static final String CHF_PATIENT_ID_POLICY_LABEL_SEQUENCE = "label_sequence";
	public static final String CHF_PATIENT_ID_POLICY_CREATED_BY = "created_by";
	public static final String CHF_PATIENT_ID_POLICY_CREATED_TIMESTAMP = "created_timestamp";

	// master_details table's column name
	public static final String MASTER_DETAILS_ID = "id";
	public static final String MASTER_DETAILS_MASTER_TYPE_CODE = "master_type_code";
	public static final String MASTER_DETAILS_LABEL_TEXT = "label_text";
	public static final String MASTER_DETAILS_PATIENT_ID = "delete_marker";
	public static final String MASTER_DETAILS_CREATED_BY = "created_by";
	public static final String MASTER_DETAILS_CREATED_TIMESTAMP = "created_timestamp";
	public static final String MASTER_DETAILS_UPDATED_BY = "updated_by";
	public static final String MASTER_DETAILS_UPDATED_TIMES_TAMP = "updated_timestamp";	

	//patient_master_other_mapping alert_data table's column name
	public static final String PATIENT_MASTER_OTHER_MAPPING_ID = "id";
	public static final String PATIENT_MASTER_OTHER_OTHER_MAPPING_MASTER_TYPE_CODE = "master_type_code";
	public static final String PATIENT_MASTER_OTHER_MAPPING_PATIENT_ID = "patient_id";
	public static final String PATIENT_MASTER_OTHER_MAPPING_LABEL_TEXT = "label_text";
	public static final String PATIENT_MASTER_OTHER_MAPPING_CREATED_BY = "created_by";
	public static final String PATIENT_MASTER_OTHER_MAPPING_CREATED_TIMESTAMP = "created_timestamp";
	public static final String PATIENT_MASTER_OTHER_MAPPING_UPDATED_BY = "updated_by";
	public static final String PATIENT_MASTER_OTHER_MAPPING_UPDATED_TIMES_TAMP = "updated_timestamp";

	//Column lable for get master and other details based on mapping
	public static final String PATIENT_MASTER_OTHER_MAPPING_COLUMN_LABEL_DATA_TYPE = "data_type";
	public static final String PATIENT_MASTER_OTHER_MAPPING_COLUMN_LABEL_ID = "master_other_id";
	public static final String PATIENT_MASTER_OTHER_MAPPING_COLUMN_LABEL_TEXT = "label_text";
	public static final String PATIENT_MASTER_OTHER_MAPPING_COLUMN_LABEL_MAPPING_ID = "mapping_id";
	public static final String PATIENT_MASTER_OTHER_MAPPING_COLUMN_LABEL_MASTER_TYPE_CODE = "master_type_code";

	// patient_actions table's column name
	public static final String PATIENT_ACTIONS_ACTION_ID = "action_id";
	public static final String PATIENT_ACTIONS_PATIENT_ID = "patient_id";
	public static final String PATIENT_ACTIONS_PRIORITY = "priority";
	public static final String PATIENT_ACTIONS_DUE_DATE = "due_date";
	public static final String PATIENT_ACTIONS_SUBJECT = "subject";
	public static final String PATIENT_ACTIONS_DETAILS = "details";
	public static final String PATIENT_ACTIONS_STATUS = "status";
	public static final String PATIENT_ACTIONS_CREATED_BY = "created_by";
	public static final String PATIENT_ACTIONS_CREATED_TIMESTAMP = "created_timestamp";
	public static final String PATIENT_ACTIONS_UPDATED_BY = "updated_by";
	public static final String PATIENT_ACTIONS_UPDATED_TIMESTEMP = "updated_timestamp";

	// patient_action_comments table's column name
	public static final String PATIENT_ACTION_COMMENTS_COMMENT_ID = "comment_id";
	public static final String PATIENT_ACTION_COMMENTS_ACTION_ID = "action_id";
	public static final String PATIENT_ACTION_COMMENTS_COMMENT = "comments";
	// temporary generated columns to return data while returning patient actions and their comments
	public static final String PATIENT_ACTION_COMMENTS_CREATED_BY = "com_created_by";
	public static final String PATIENT_ACTION_COMMENTS_CREATED_TIMESTAMP = "com_created_timestamp";

	// clinician_comments table's column name
	public static final String COMMENT_ID = "comment_id";
	public static final String COMMENT_PATIENT_ID = "patient_id";
	public static final String COMMENT = "comment";
	public static final String COMMENT_CREATED_BY = "created_by";
	public static final String COMMENT_CREATED_TIMESTAMP = "created_timestamp";
	public static final String COMMENT_UPDATED_BY = "updated_by";
	public static final String COMMENT_UPDATED_TIMESTAMP = "updated_timestamp";	

	// organization table's column name
	public static final String ORGANIZATION_ID = "organization_id";
	public static final String ORGANIZATION_NAME = "organization_name";
	public static final String ORGANIZATION_ADDRESS = "address";
	public static final String ORGANIZATION_CONTACT_PERSON = "contact_person";
	public static final String ORGANIZATION_CONTACT_NUMBER = "contact_number";
	public static final String ORGANIZATION_STATUS = "organization_status";
	public static final String ORGANIZATION_TYPE = "organization_type";
	public static final String ORGANIZATION_SUB_TYPE = "organization_sub_type";
	public static final String ORGANIZATION_DELETE_MARKER = "delete_marker";
	public static final String ORGANIZATION_PWD_EXPIRE_IN_DAYS = "pwd_expire_in_days";
	public static final String ORGANIZATION_PWD_EXPIRY_NOTIFICATION_START_IN_DAYS = "pwd_expiry_notification_start_in_days";
	public static final String ORGANIZATION_CREATED_BY = "created_by";
	public static final String ORGANIZATION_CREATED_TIMESTAMP = "created_timestamp";
	public static final String ORGANIZATION_UPDATED_BY = "updated_by";
	public static final String ORGANIZATION_UPDATED_TIMESTAMP = "updated_timestamp";
}