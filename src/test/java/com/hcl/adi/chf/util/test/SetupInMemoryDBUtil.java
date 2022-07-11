package com.hcl.adi.chf.util.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.hcl.adi.chf.dao.DBConnection;

public class SetupInMemoryDBUtil {
	public static void setupInMemoryDB() {

		try {
			Connection connection = DBConnection.getConnection();
			PreparedStatement createPreparedStatement = null;
			
			String max="CREATE ALIAS DATE AS $$\r\n" + 
					"String DATE(String date) {\r\n" + 
					"		String returnData = null;\r\n" + 
					"		try {\r\n" + 
					"			returnData = \"2012-06-02\";\r\n" + 
					"		} catch (Exception e) {\r\n" + 
					"			e.printStackTrace();\r\n" + 
					"		}\r\n" + 
					"		return returnData;\r\n" + 
					"	}\r\n" + 
					"$$;";
			
			/*String DATE_SUB="CREATE ALIAS DATE_SUB AS $$\r\n" + 
					"String DATE_SUB(String date) {\r\n" + 
					"		String returnData = null;\r\n" + 
					"		try {\r\n" + 
					"			returnData = \"2012-06-02\";\r\n" + 
					"		} catch (Exception e) {\r\n" + 
					"			e.printStackTrace();\r\n" + 
					"		}\r\n" + 
					"		return returnData;\r\n" + 
					"	}\r\n" + 
					"$$;";*/

			String institution = "CREATE TABLE `institution` (`institution_id` INT(20) UNSIGNED NOT NULL AUTO_INCREMENT,	`institution_name` VARCHAR(40) NOT NULL,	`address` VARCHAR(50) NOT NULL,	`contact_person` VARCHAR(30) NULL DEFAULT NULL,	`contact_number` VARCHAR(18) NULL DEFAULT NULL,	`institution_status` CHAR(1) NOT NULL DEFAULT 'A',	`delete_marker` CHAR(1) NOT NULL DEFAULT 'N',	`pwd_expire_in_days` INT(20) NOT NULL DEFAULT '30',	`pwd_expiry_notification_start_in_days` INT(20) NOT NULL DEFAULT '7',`created_by` VARCHAR(50),`created_timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,`updated_by` VARCHAR(50) NULL DEFAULT NULL,`updated_timestamp` TIMESTAMP,PRIMARY KEY (`institution_id`))";
			String clinician = "CREATE TABLE `clinician` (`clinician_id` INT(20) UNSIGNED NOT NULL AUTO_INCREMENT,`institution_id` INT(20) UNSIGNED NOT NULL,	`email_id` VARCHAR(50) NOT NULL,	`first_name` VARCHAR(50) NOT NULL, `last_name` VARCHAR(50) NOT NULL,	`employee_id` VARCHAR(10) NULL DEFAULT NULL,`pool_id` VARCHAR(50) NOT NULL,	`status` VARCHAR(2) NOT NULL DEFAULT 'A',	`type` VARCHAR(2) NOT NULL DEFAULT 'CL',	`portal_access` VARCHAR(10) NOT NULL DEFAULT 'WEB',	`location` VARCHAR(40) NULL DEFAULT NULL,	`delete_marker` CHAR(1) NOT NULL DEFAULT 'N',	`is_tnc_accepted` CHAR(1) NOT NULL DEFAULT 'N',`pwd_updated_date` DATE NOT NULL DEFAULT '2050-12-01',`retry_login_attempt_counter` INT(1) NOT NULL DEFAULT '0',`last_login_timestamp` TIMESTAMP NULL DEFAULT NULL,`locale` VARCHAR(50) DEFAULT NULL,`timezone` VARCHAR(50) DEFAULT NULL,`created_by` VARCHAR(50),`created_timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,`updated_by` VARCHAR(50) NULL DEFAULT NULL,`updated_timestamp` TIMESTAMP, PRIMARY KEY (`clinician_id`),	UNIQUE INDEX `email_id` (`email_id`))";
			String patient = "CREATE TABLE `patient_phi` (`patient_id` INT(20) UNSIGNED NOT NULL AUTO_INCREMENT, `chf_patient_id` VARCHAR(30) NOT NULL, `institution_id` INT(10) UNSIGNED NOT NULL,`gender` VARCHAR(1) NOT NULL, `first_name` VARCHAR(20) NOT NULL,`last_name` VARCHAR(20) NOT NULL,`contact_no` VARCHAR(20) NOT NULL,`dob` VARCHAR(20) NOT NULL, `address` VARCHAR(50) NOT NULL,`zip` VARCHAR(50) NOT NULL,`ssn` VARCHAR(10) UNSIGNED NOT NULL, `mr_no` VARCHAR(20) NOT NULL, `patient_details_json` json NOT NULL,`is_threshold_set` tinyint(1) NOT NULL DEFAULT '0',`is_action_open` tinyint(1) NOT NULL DEFAULT '0',`created_by` VARCHAR(50) NOT NULL,`created_timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, `updated_by` VARCHAR(50) NULL DEFAULT NULL, `updated_timestamp` TIMESTAMP ,`delete_marker` CHAR(1) NOT NULL DEFAULT 'N',PRIMARY KEY (`patient_id`))";
			String admins = "CREATE TABLE `admins` (  `admin_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,  `institution_id` INT(10) unsigned NOT NULL DEFAULT '0',  `email_id` VARCHAR(50) NOT NULL,  `first_name` VARCHAR(50) NOT NULL,  `last_name` VARCHAR(50) NOT NULL,  `employee_id` VARCHAR(10) DEFAULT NULL,  `pool_id` VARCHAR(50) NOT NULL,  `status` VARCHAR(2) NOT NULL DEFAULT 'A',  `type` VARCHAR(2) NOT NULL,  `is_default` CHAR(1) NOT NULL DEFAULT 'N',  `portal_access` VARCHAR(10) NOT NULL DEFAULT 'WEB',  `location` VARCHAR(40) DEFAULT NULL,  `delete_marker` CHAR(1) NOT NULL DEFAULT 'N',  `is_tnc_accepted` CHAR(1) NOT NULL DEFAULT 'N',  `pwd_updated_date` DATE NOT NULL,  `retry_login_attempt_counter` INT(1) NOT NULL DEFAULT '0',  `last_login_timestamp` TIMESTAMP NULL DEFAULT NULL,  `locale` VARCHAR(50) DEFAULT NULL,  `timezone` VARCHAR(50) DEFAULT NULL,  `created_by` VARCHAR(50) NOT NULL,  `created_timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,  `updated_by` VARCHAR(50) DEFAULT NULL,  `updated_timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,  PRIMARY KEY (`admin_id`),  CONSTRAINT `admins_ibfk_1` FOREIGN KEY (`institution_id`) REFERENCES `institution` (`institution_id`))";
			String patientMapping = "CREATE TABLE `patient_clinician_mapping` (  `map_id` int(10) unsigned NOT NULL AUTO_INCREMENT,  `patient_id` int(10) unsigned NOT NULL,`clinician_id` int(10) unsigned NOT NULL,  `created_by` varchar(50) NOT NULL,  `created_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,  `updated_by` varchar(50) DEFAULT NULL,  `updated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,  PRIMARY KEY (`map_id`),  CONSTRAINT `FK_patient_clinician_mapping_clinician` FOREIGN KEY (`clinician_id`) REFERENCES `clinician` (`clinician_id`),  CONSTRAINT `FK_patient_clinician_mapping_patient_phi` FOREIGN KEY (`patient_id`) REFERENCES `patient_phi` (`patient_id`))";
			String passwordPolicy = "CREATE TABLE `institution_pwd_policy_mgmt` (`policy_id` INT(20) UNSIGNED NOT NULL AUTO_INCREMENT,`institution_id` INT(20) UNSIGNED NOT NULL,`pwd_rotation_in_days` INT(3) NOT NULL DEFAULT '30',`pwd_min_length` INT(2) UNSIGNED NOT NULL DEFAULT '8',`pwd_max_length` INT(2) UNSIGNED NOT NULL DEFAULT '20',`is_caps_allowed` CHAR(1) NOT NULL DEFAULT 'N',`is_lower_allowed` CHAR(1) NOT NULL DEFAULT 'N',`is_numeric_allowed` CHAR(1) NOT NULL DEFAULT 'N',`is_spl_char_allowed` CHAR(1) NOT NULL DEFAULT 'N',`prohibited_passwords` VARCHAR(200) NULL DEFAULT NULL,`policy_status` CHAR(1) NOT NULL DEFAULT 'A',`created_by` VARCHAR(50) NOT NULL,`created_timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,`updated_by` VARCHAR(50) NULL DEFAULT NULL,`updated_timestamp` TIMESTAMP ,PRIMARY KEY (`policy_id`), CONSTRAINT `institution_pwd_policy_mgmt_ibfk_1` FOREIGN KEY (`institution_id`) REFERENCES `institution` (`institution_id`))";
			String termsAndCondition = "CREATE TABLE `terms_n_conditions` (	`tnc_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,	`institution_id` INT(10) UNSIGNED NOT NULL,	`created_by` VARCHAR(50) NOT NULL,	`created_timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,	`updated_by` VARCHAR(50) NULL DEFAULT NULL,	`updated_timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,	`tnc_status` CHAR(1) NOT NULL DEFAULT 'A',	`delete_marker` CHAR(1) NOT NULL DEFAULT 'N',	`tnc_text` VARCHAR(25000) NOT NULL DEFAULT 'This is default terms and conditions',	PRIMARY KEY (`tnc_id`),	CONSTRAINT `terms_n_conditions_ibfk_1` FOREIGN KEY (`institution_id`) REFERENCES `institution` (`institution_id`))";
			String auditLog = "CREATE TABLE `audit_logs`( `log_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, `institution_id` INT(10) UNSIGNED NOT NULL, `user_type` VARCHAR(2) NOT NULL, `activity` VARCHAR(100) NOT NULL, `created_by` VARCHAR(50) NOT NULL, `created_timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, PRIMARY KEY (`log_id`), CONSTRAINT `audit_logs_ibfk_1` FOREIGN KEY (`institution_id`) REFERENCES `institution` (`institution_id`) )";

			String deviceInventory = "CREATE TABLE `device_inventory` (  `kit_id` int(10) unsigned NOT NULL AUTO_INCREMENT,  `ble_mac` varchar(50) NOT NULL,  `device_label_id` varchar(50) NOT NULL,  `wearable_serial_number` varchar(50) NOT NULL,  `wearable_fw_version` varchar(50) NOT NULL,  `basestation_serial_number` varchar(50) NOT NULL,  `basestation_fw_version` varchar(50) NOT NULL,  `created_by` varchar(50) NOT NULL,  `created_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,  `updated_by` varchar(50) DEFAULT NULL,  `updated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,  `wearabale_available_fw_version` varchar(50),  `basestation_available_fw_version` varchar(50),  `wearabale_fw_status` varchar(50),  `basestation_fw_status` varchar(50),  PRIMARY KEY (`kit_id`),  UNIQUE INDEX `ble_mac` (`ble_mac`),  UNIQUE INDEX `device_label_id` (`device_label_id`),  UNIQUE INDEX `wearable_serial_number` (`wearable_serial_number`),  UNIQUE INDEX `basestation_serial_number` (`basestation_serial_number`))";
			String dataArchival = "CREATE TABLE `data_archival_policy_mgmt` (	`data_archival_policy_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,	`institution_id` INT(10) UNSIGNED NOT NULL,	`archival_period_in_months` INT(2) UNSIGNED NOT NULL,	`auto_archival_frequency` VARCHAR(15) NOT NULL,	`auto_log_off_time_in_minutes` INT(3) UNSIGNED NOT NULL,	`duration_to_store_audit_logs_in_months` INT(2) UNSIGNED NOT NULL,	`data_archival_policy_status` CHAR(1) NOT NULL DEFAULT 'A',	`delete_marker` CHAR(1) NOT NULL DEFAULT 'N',	`created_by` VARCHAR(50) NOT NULL,	`created_timestamp` TIMESTAMP,	`updated_by` VARCHAR(50) NULL DEFAULT NULL,	`updated_timestamp` TIMESTAMP,	PRIMARY KEY (`data_archival_policy_id`),CONSTRAINT `data_archival_policy_mgmt_ibfk_1` FOREIGN KEY (`institution_id`) REFERENCES `institution` (`institution_id`))";
			String deviceInstitutionMapping = "CREATE TABLE `device_institution_mapping` (	`mapping_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,	`device_id` INT(10) UNSIGNED NOT NULL,	`base_station_id` BIGINT(20) UNSIGNED NOT NULL,	`institution_id` INT(10) UNSIGNED NOT NULL,	`mapping_status` CHAR(2) NOT NULL DEFAULT 'A',	`created_by` VARCHAR(50) NOT NULL,	`created_timestamp` TIMESTAMP,	`updated_by` VARCHAR(50) NULL DEFAULT NULL,	`updated_timestamp` TIMESTAMP,	PRIMARY KEY (`mapping_id`),	CONSTRAINT `device_institution_mapping_ibfk_3` FOREIGN KEY (`institution_id`) REFERENCES `institution` (`institution_id`))";

			// New Table
			String customVital = "CREATE TABLE `custom_vitals` ( `custom_vitals_id` INT(20) UNSIGNED NOT NULL AUTO_INCREMENT, `patient_id` INT(10) UNSIGNED NOT NULL, `systolic_bp` INT(3) NOT NULL, `diastolic_bp` INT(3) NOT NULL, `weight` VARCHAR(50) NOT NULL, `custom_vitals` VARCHAR(50) NULL DEFAULT NULL, `reading_date` DATETIME NOT NULL, `created_by` VARCHAR(50) NOT NULL, `created_timestamp` TIMESTAMP , `updated_by` VARCHAR(50) NOT NULL, `updated_timestamp` TIMESTAMP , PRIMARY KEY (`custom_vitals_id`), UNIQUE INDEX `patient_id` (`patient_id`))";
			String passwordPolicyMgmt = "CREATE TABLE `pwd_policy_mgmt` ( `pwd_policy_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, `institution_id` INT(10) UNSIGNED NOT NULL, `pwd_rotation_in_days` INT(3) NOT NULL DEFAULT '30', `pwd_min_length` INT(2) UNSIGNED NOT NULL DEFAULT '8', `pwd_max_length` INT(2) UNSIGNED NOT NULL DEFAULT '20', `is_caps_allowed` CHAR(1) NOT NULL DEFAULT 'N', `is_lower_allowed` CHAR(1) NOT NULL DEFAULT 'N', `is_numeric_allowed` CHAR(1) NOT NULL DEFAULT 'N', `is_spl_char_allowed` CHAR(1) NOT NULL DEFAULT 'N', `retry_login_attempts_allowed` INT(1) NOT NULL DEFAULT '3', `pwd_history` INT(1) NOT NULL DEFAULT '3', `prohibited_passwords` VARCHAR(250) NULL DEFAULT NULL, `pwd_policy_status` CHAR(1) NOT NULL DEFAULT 'A', `delete_marker` CHAR(1) NOT NULL DEFAULT 'N', `created_by` VARCHAR(50) NOT NULL, `created_timestamp` TIMESTAMP, `updated_by` VARCHAR(50) NULL DEFAULT NULL, `updated_timestamp` TIMESTAMP , PRIMARY KEY (`pwd_policy_id`), CONSTRAINT `pwd_policy_mgmt_ibfk_1` FOREIGN KEY (`institution_id`) REFERENCES `institution` (`institution_id`) )";

			String deviceInput = "CREATE TABLE `device_input` (  `pk_id` INT(10) unsigned NOT NULL AUTO_INCREMENT,  `file_name` VARCHAR(100) NOT NULL,  `file_type` VARCHAR(12) NOT NULL,  `file_status` VARCHAR(50) NOT NULL,  `measurement_id` VARCHAR(50) DEFAULT NULL,  `received_timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,  `updated_timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,  PRIMARY KEY (`pk_id`),  UNIQUE INDEX `file_name` (`file_name`))";
			String deviceData = "CREATE TABLE `device_data` (`pk_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,`system_id` VARCHAR(50) NOT NULL,`measurement_id` VARCHAR(50) NOT NULL,  `device_id` VARCHAR(50) NOT NULL,  `institution_id` INT(10) UNSIGNED NOT NULL,  `chf_patient_id` VARCHAR(50) NOT NULL,  `reading_date` TIMESTAMP NOT NULL,  `is_baseline_reading` TINYINT(1) NOT NULL DEFAULT '0',  `reading_position` VARCHAR(20) DEFAULT NULL,  `file_name` VARCHAR(100) NOT NULL,  `generated_files_base_url` VARCHAR(500) NOT NULL,  `status` char(1) NOT NULL DEFAULT 'A' ,  `created_by` VARCHAR(50) NOT NULL,  `created_timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,  `updated_by` VARCHAR(50) DEFAULT NULL,  `updated_timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,  PRIMARY KEY (`pk_id`),  UNIQUE INDEX `measurement_id` (`measurement_id`),  CONSTRAINT `device_data_ibfk_1` FOREIGN KEY (`institution_id`) REFERENCES `institution` (`institution_id`),  CONSTRAINT `device_data_ibfk_2` FOREIGN KEY (`file_name`) REFERENCES `device_input` (`file_name`))";
			String derivedData = "CREATE TABLE `derived_data` ( `derived_data_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, `msg_id` INT(10) UNSIGNED NOT NULL, `supine_tidal_vol` DOUBLE NOT NULL, `supine_respiration_rate` DOUBLE UNSIGNED NOT NULL, `supine_respiration_rate_trend` INT(1) NOT NULL, `supine_rr_tv` DOUBLE NOT NULL, `fowler_tidal_vol` DOUBLE NOT NULL, `fowler_respiration_rate` DOUBLE NOT NULL, `fowler_respiration_rate_trend` INT(1) NOT NULL, `fowler_rr_tv` DOUBLE NOT NULL, `delta_z` DOUBLE NOT NULL, `s3_energy` DOUBLE NOT NULL, `heart_rate` DOUBLE NOT NULL, `heart_rate_trend` INT(1) NOT NULL, `vital_trend` INT(1) NOT NULL, `measurement_trend` INT(1) NOT NULL, `reading_compliance` INT(1) NOT NULL, `s3_trend` INT(1) NOT NULL, `tidal_vol_trend` INT(1) NOT NULL, `thoracic_impedance` DOUBLE NOT NULL, `thoracic_impedance_trend` INT(1) NOT NULL, `created_by` VARCHAR(50) NOT NULL, `created_timestamp` TIMESTAMP, `updated_by` VARCHAR(50) NULL DEFAULT NULL, `updated_timestamp` TIMESTAMP, PRIMARY KEY (`derived_data_id`))";
			String alertData = "CREATE TABLE `alert_data` ( `alert_data_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, `msg_id` INT(10) UNSIGNED NOT NULL, `alert_id` INT(10) UNSIGNED NOT NULL, `is_supine_tidal_vol_alert` TINYINT(1) NOT NULL DEFAULT '0', `is_delta_z_alert` TINYINT(1) NOT NULL DEFAULT '0', `is_partial_reading_alert` TINYINT(1) NOT NULL DEFAULT '0', `created_by` VARCHAR(50) NOT NULL, `created_timestamp` TIMESTAMP, `updated_by` VARCHAR(50) NULL DEFAULT NULL, `updated_timestamp` TIMESTAMP, PRIMARY KEY (`alert_data_id`), UNIQUE INDEX `alert_id` (`alert_id`))";

			String compliancePolicy = "CREATE TABLE `compliance_settings` (`pk_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, `institution_id` INT(10) unsigned NOT NULL, `compliance_period` INT(11) NOT NULL, `status` tinyint(1) NOT NULL DEFAULT '1', `created_by` VARCHAR(50) NOT NULL, `created_timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, `updated_by` VARCHAR(50) DEFAULT NULL, `updated_timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP , PRIMARY KEY (`pk_id`) )";
			String patientThreshold = "CREATE TABLE `patient_threshold` (`threshold_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, `patient_id` INT(10) UNSIGNED NOT NULL, `system_id` VARCHAR(50), `threshold_values` json DEFAULT NULL, `created_by` VARCHAR(50) NOT NULL, `created_timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, `updated_by` VARCHAR(50) DEFAULT NULL, `updated_timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, PRIMARY KEY (`threshold_id`), CONSTRAINT `patient_threshold_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patient_phi` (`patient_id`))";
			String thresholdPolicy = "CREATE TABLE `threshold_policy` (  `threshold_policy_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,  `institution_id` INT(10) UNSIGNED NOT NULL DEFAULT '0',  `threshold_policy` JSON NOT NULL,  `status` VARCHAR(1) NOT NULL DEFAULT 'A',  `created_by` VARCHAR(50) NOT NULL,  `created_timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,  `updated_by` VARCHAR(50) NOT NULL,  `updated_timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,  PRIMARY KEY (`threshold_policy_id`),  CONSTRAINT `threshold_policy_ibfk_1` FOREIGN KEY (`institution_id`) REFERENCES `institution` (`institution_id`))";
			String patientProvider = "CREATE TABLE `patient_provider_mapping` (  `patient_provider_mapping_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,  `chf_patient_id` VARCHAR(50) NOT NULL,  `provider_id` INT(10) NOT NULL DEFAULT '0',  `delete_marker` CHAR(1) NOT NULL DEFAULT 'N',  `is_default` TINYINT(1) NOT NULL DEFAULT '0',  `created_by` VARCHAR(50) NOT NULL,  `created_timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,  `updated_by` VARCHAR(50) NOT NULL,  `updated_timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,  PRIMARY KEY (`patient_provider_mapping_id`))";
					
			String patientVital = "CREATE TABLE `patient_vitals` (  `patient_vitals_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,  `patient_id` INT(10) UNSIGNED NOT NULL,  `systolic_bp` INT(3) DEFAULT NULL,  `diastolic_bp` INT(3) DEFAULT NULL,  `weight` VARCHAR(5) DEFAULT NULL,  `temperature` VARCHAR(5) DEFAULT NULL,  `reading_date` TIMESTAMP NULL DEFAULT NULL,  `custom_vitals` JSON DEFAULT NULL,  `created_by` VARCHAR(50) NOT NULL,  `created_timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,  `updated_by` VARCHAR(50) DEFAULT NULL,  `updated_timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,  PRIMARY KEY (`patient_vitals_id`),  CONSTRAINT `patient_vitals_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patient_phi` (`patient_id`))";
			
			String thresholdRequest = "CREATE TABLE `threshold_breach_data` (  `threshold_breach_id` BIGINT(20) unsigned NOT NULL AUTO_INCREMENT,  `system_id` VARCHAR(50) NOT NULL,  `measurement_id` VARCHAR(50) NOT NULL,  `threshold_breach_data` json NOT NULL,  `severity` CHAR(1) NOT NULL DEFAULT 'L',  `is_active` TINYINT(1) NOT NULL DEFAULT '1',  `created_by` VARCHAR(50) NOT NULL,  `created_timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,  `updated_by` VARCHAR(50) DEFAULT NULL,  `updated_timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,  PRIMARY KEY (`threshold_breach_id`))";
			
			String contactUs = "CREATE TABLE `contact_us` (  `requestId` int(10) unsigned NOT NULL AUTO_INCREMENT,  `requester_name` varchar(100) DEFAULT NULL,  `from_email` varchar(50) NOT NULL,  `to_email` varchar(50) NOT NULL,  `cc_email` varchar(50) DEFAULT NULL,  `subject` varchar(100) NOT NULL,  `message` varchar(1500) NOT NULL,  `is_copy_requested` tinyint(1) NOT NULL DEFAULT '0',  `ses_msg_id` varchar(200) DEFAULT NULL,  `created_by` varchar(50) NOT NULL,  `created_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,  `updated_by` varchar(50) DEFAULT NULL,  `updated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,  PRIMARY KEY (`requestId`))";
			
			String helpCenterMaster = "CREATE TABLE `help_center_master` (  `master_id` int(10) NOT NULL AUTO_INCREMENT,  `subject` varchar(200) DEFAULT NULL,  `created_by` varchar(50) DEFAULT NULL,  `created_timestamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,  PRIMARY KEY (`master_id`))";
			
			String helpCenter = "CREATE TABLE `help_center` (  `help_id` int(10) unsigned NOT NULL AUTO_INCREMENT,  `help_topic` varchar(5000) NOT NULL,  `help_desc` varchar(20000) NOT NULL,  `help_url` varchar(5000) NOT NULL,  `help_type` varchar(50) NOT NULL,  `created_by` varchar(50) NOT NULL,  `created_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,  `updated_by` varchar(50) NOT NULL,  `updated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,  PRIMARY KEY (`help_id`))";
			
			String healthCareProvider = "CREATE TABLE `healthcare_provider` (  `provider_id` int(10) unsigned NOT NULL AUTO_INCREMENT,  `institution_id` int(10) unsigned NOT NULL,  `provider_name` varchar(255) DEFAULT NULL,  `hospital_affilation` varchar(255) DEFAULT NULL,  `doctor_name` varchar(255) DEFAULT NULL,  `doctor_contact_no` varchar(255) DEFAULT NULL,  `doctor_email` varchar(255) DEFAULT NULL,  `other_contact_number` varchar(255) DEFAULT NULL,  `delete_marker` char(1) NOT NULL DEFAULT 'N',  `created_by` varchar(50) NOT NULL,  `created_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,  `updated_by` varchar(50) DEFAULT NULL,  `updated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,  PRIMARY KEY (`provider_id`))";
			
			String chfPatientIdPolicy ="CREATE TABLE `chf_patient_id_policy` (  `pk_id` int(10) unsigned NOT NULL AUTO_INCREMENT,  `institution_id` int(10) unsigned NOT NULL,  `is_emr_ehr_enabled` char(1) NOT NULL DEFAULT 'N',  `label` varchar(30) NOT NULL,  `label_rule` varchar(30) NOT NULL,  `label_separator` varchar(30) NOT NULL,  `label_sequence` smallint(1) unsigned NOT NULL,  `created_by` varchar(50) NOT NULL,  `created_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,  PRIMARY KEY (`pk_id`))";
			
			String patientDeviceMapping ="CREATE TABLE `patient_device_mapping` (  `mapping_id` int(10) unsigned NOT NULL AUTO_INCREMENT,  `system_id` varchar(50) NOT NULL COMMENT 'Custom generated id to store the mapping of device kit and patient',  `kit_id` int(10) unsigned NOT NULL,  `chf_patient_id` varchar(50) NOT NULL,  `mapping_status` varchar(2) NOT NULL DEFAULT 'A',  `created_by` varchar(50) NOT NULL,  `created_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,  `updated_by` varchar(50) DEFAULT NULL,  `updated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,  PRIMARY KEY (`mapping_id`),  UNIQUE INDEX `kit_id` (`kit_id`),  UNIQUE INDEX `system_id` (`system_id`),  CONSTRAINT `patient_device_mapping_ibfk_1` FOREIGN KEY (`chf_patient_id`) REFERENCES `patient_phi` (`chf_patient_id`),  CONSTRAINT `patient_device_mapping_ibfk_2` FOREIGN KEY (`kit_id`) REFERENCES `device_inventory` (`kit_id`))";
			
			String patientActions  = "CREATE TABLE `patient_actions` (  `action_id` int(10) unsigned NOT NULL AUTO_INCREMENT,  `patient_id` int(10) unsigned NOT NULL,  `priority` varchar(10) NOT NULL DEFAULT 'STANDARD',  `due_date` date DEFAULT NULL,  `subject` varchar(200) NOT NULL,  `details` varchar(1000) NOT NULL,  `status` varchar(10) NOT NULL DEFAULT 'OPEN',  `created_by` varchar(50) NOT NULL,  `created_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,  `updated_by` varchar(50) DEFAULT NULL,  `updated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,  PRIMARY KEY (`action_id`),  CONSTRAINT `patient_actions_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patient_phi` (`patient_id`))";
			
			String patientActionComments = "CREATE TABLE `patient_action_comments` (  `comment_id` int(10) unsigned NOT NULL AUTO_INCREMENT,  `action_id` int(10) unsigned NOT NULL,  `comments` varchar(1000) NOT NULL,  `created_by` varchar(50) NOT NULL,  `created_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,  PRIMARY KEY (`comment_id`),  CONSTRAINT `patient_action_comments_ibfk_1` FOREIGN KEY (`action_id`) REFERENCES `patient_actions` (`action_id`))";
			
			String clinicianComments ="CREATE TABLE `clinician_comments` (  `comment_id` int(10) unsigned NOT NULL AUTO_INCREMENT,  `patient_id` int(10) unsigned NOT NULL,  `comment` varchar(2500) NOT NULL,  `created_by` varchar(50) NOT NULL,  `created_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,  `updated_by` varchar(50) DEFAULT NULL,  `updated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,  PRIMARY KEY (`comment_id`),  CONSTRAINT `clinician_comments_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patient_phi` (`patient_id`))";
			
			String patientInfoMaster = "CREATE TABLE `patient_info_master` (  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,  `master_type_code` varchar(10) NOT NULL,  `key_code` varchar(50) NOT NULL,  `key_value` varchar(50) NOT NULL,  `delete_marker` char(1) NOT NULL DEFAULT 'N',  `created_by` varchar(50) NOT NULL,  `created_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,  `updated_by` varchar(50) DEFAULT NULL,  `updated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,  PRIMARY KEY (`id`))";
			
			String patientMasterMapping = "CREATE TABLE `patient_master_mapping` (  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,  `master_id` int(10) unsigned NOT NULL,  `patient_id` int(10) unsigned NOT NULL,  `delete_marker` varchar(1) NOT NULL DEFAULT 'N',  `created_by` varchar(50) NOT NULL,  `created_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,  `updated_by` varchar(50) DEFAULT NULL,  `updated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,  PRIMARY KEY (`id`),  UNIQUE INDEX `patient_unique_key` (`master_id`,`patient_id`),  CONSTRAINT `patient_mapping_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patient_phi` (`patient_id`),  CONSTRAINT `patient_mapping_ibfk_2` FOREIGN KEY (`master_id`) REFERENCES `patient_info_master` (`id`))";
			
			String patientMasterOtherMapping = "CREATE TABLE `patient_master_other_mapping` (  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,  `master_type_code` varchar(10) NOT NULL,  `patient_id` int(10) unsigned NOT NULL,  `label_text` varchar(128) DEFAULT NULL,  `delete_marker` varchar(1) NOT NULL DEFAULT 'N',  `created_by` varchar(50) NOT NULL,  `created_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,  `updated_by` varchar(50) DEFAULT NULL,  `updated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,  PRIMARY KEY (`id`),  CONSTRAINT `patient_other_master_mapping_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patient_phi` (`patient_id`))";
			
			connection.setAutoCommit(false);
			
			createPreparedStatement = connection.prepareStatement(max);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();
			
//			createPreparedStatement = connection.prepareStatement(DATE_SUB);
//			createPreparedStatement.executeUpdate();
//			createPreparedStatement.close();
			
			createPreparedStatement = connection.prepareStatement(institution);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();

			createPreparedStatement = connection.prepareStatement(clinician);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();

			createPreparedStatement = connection.prepareStatement(admins);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();

			createPreparedStatement = connection.prepareStatement(patient);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();

			createPreparedStatement = connection.prepareStatement(patientMapping);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();

			createPreparedStatement = connection.prepareStatement(passwordPolicy);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();

			createPreparedStatement = connection.prepareStatement(termsAndCondition);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();

			createPreparedStatement = connection.prepareStatement(auditLog);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();

			createPreparedStatement = connection.prepareStatement(dataArchival);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();

			createPreparedStatement = connection.prepareStatement(deviceInstitutionMapping);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();

			createPreparedStatement = connection.prepareStatement(deviceInventory);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();

			createPreparedStatement = connection.prepareStatement(customVital);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();

			createPreparedStatement = connection.prepareStatement(passwordPolicyMgmt);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();

			createPreparedStatement = connection.prepareStatement(deviceInput);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();
			
			createPreparedStatement = connection.prepareStatement(deviceData);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();

			createPreparedStatement = connection.prepareStatement(derivedData);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();

			createPreparedStatement = connection.prepareStatement(alertData);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();
			
			createPreparedStatement = connection.prepareStatement(compliancePolicy);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();
						
			createPreparedStatement = connection.prepareStatement(patientThreshold);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();
			
			createPreparedStatement = connection.prepareStatement(thresholdPolicy);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();
			
			createPreparedStatement = connection.prepareStatement(patientProvider);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();
			
			createPreparedStatement = connection.prepareStatement(patientVital);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();
			
			createPreparedStatement = connection.prepareStatement(thresholdRequest);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();
			
			createPreparedStatement = connection.prepareStatement(contactUs);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();
			
			createPreparedStatement = connection.prepareStatement(helpCenterMaster);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();
			
			createPreparedStatement = connection.prepareStatement(helpCenter);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();
			
			createPreparedStatement = connection.prepareStatement(healthCareProvider);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();
			
			createPreparedStatement = connection.prepareStatement(chfPatientIdPolicy);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();
			
			createPreparedStatement = connection.prepareStatement(patientDeviceMapping);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();
			
			createPreparedStatement = connection.prepareStatement(patientActions);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();
			
			createPreparedStatement = connection.prepareStatement(patientActionComments);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();
			
			createPreparedStatement = connection.prepareStatement(clinicianComments);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();
			
			createPreparedStatement = connection.prepareStatement(patientInfoMaster);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();
			
			createPreparedStatement = connection.prepareStatement(patientMasterMapping);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();
			
			createPreparedStatement = connection.prepareStatement(patientMasterOtherMapping);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();

			Statement statement = connection.createStatement();

			statement.executeUpdate(
					"INSERT INTO `institution` (`institution_id`, `institution_name`, `address`, `contact_person`, `contact_number`, `institution_status`, `delete_marker`, `pwd_expire_in_days`, `pwd_expiry_notification_start_in_days`, `created_by`, `created_timestamp`, `updated_by`, `updated_timestamp`) VALUES	(0, 'ADI', 'ADI Bangalore', 'Karthick NG', '9650723451', 'A', 'N', 30, 7, 'admin@hcl.com', '2019-06-25 06:57:27', 'maganta.ojha@hcl.com', '2019-08-08 09:55:22'),	(1, 'Apollo1', 'Noida1', 'Abel', '9321456789', 'A', 'N', 30, 7, 'dinesh-kuma@hcl.com', '2019-06-25 07:00:02', 'automationrobot1@gmail.com', '2019-07-02 08:39:12'),	(2, 'Ayurvedic Hospital', 'Gurgaon', 'Ajay', '+919650788878', 'A', 'Y', 30, 7, 'dinesh-kuma@hcl.com', '2019-06-26 06:19:59', 'dinesh-kuma@hcl.com', '2019-07-05 03:54:52'),	(3, 'massacguttess hospital', 'Adyar,Chennai', 'Dr Raj adbi', '8765433213', 'A', 'Y', 30, 7, 'automationrobot1@gmail.com', '2019-06-26 06:21:27', 'automationrobot1@gmail.com', '2019-07-09 09:48:50'),	(4, 'Massachusetts General Hospital', 'chennai', 'eeeeeeeeee', '1212121212', 'A', 'N', 30, 7, 'automationrobot2@gmail.com', '2019-06-26 09:21:19', 'automationrobot55@gmail.com', '2019-08-07 10:37:32'),	(5, 'hrmmmjhjhjrrhff hospital', 'Adyar,Chennai', 'Dr Raj adbi', '8765433213', 'A', 'Y', 30, 7, 'automationrobot1@gmail.com', '2019-06-26 10:53:20', 'automationrobot1@gmail.com', '2019-06-26 10:58:56'),	(6, 'Fortish', 'chennai', 'adhi', '1223456451', 'A', 'N', 90, 7, 'automationrobot2@gmail.com', '2019-06-26 11:18:12', 'automationrobot1@gmail.com', '2019-08-08 11:53:29'),	(7, 'automationhospital', 'chennai', 'fff', '', 'A', 'Y', 30, 7, 'automationrobot2@gmail.com', '2019-06-26 11:24:37', 'automationrobot2@gmail.com', '2019-07-17 11:31:08'),	(8, 'hrmmmjhjhjrrgghff hospital', 'Bangalore', 'Dr gupta', '12345678987', 'A', 'Y', 30, 7, 'automationrobot1@gmail.com', '2019-06-26 11:31:31', 'automationrobot1@gmail.com', '2019-06-27 09:34:39'),	(10, 'hrmmmjhjhjrhhrgghfhhf hospital', 'Adyar,Chennai', 'Dr Raj adbi', '8765433213', 'A', 'Y', 30, 7, 'automationrobot1@gmail.com', '2019-06-26 11:39:43', 'automationrobot1@gmail.com', '2019-06-26 11:45:14'),	(13, 'Sample Hospital', '1, NJSLSSLgfhfgh', 'XYZ', '+12345678116', 'A', 'N', 30, 7, 'karthick.ng@analog.com', '2019-06-28 11:34:22', 'manikandanmah@hcl.com', '2019-08-08 13:01:01')");
			statement.executeUpdate(
					"INSERT INTO `institution` (`institution_id`, `institution_name`, `address`, `contact_person`, `contact_number`, `institution_status`, `delete_marker`, `pwd_expire_in_days`, `pwd_expiry_notification_start_in_days`) VALUES (9, 'Test Data', 'Test Data 4', 'Test ', '1231231230', 'A', 'Y', 30, 7)");

			statement.executeUpdate(
					"INSERT INTO `institution` (`institution_id`, `institution_name`, `address`, `contact_person`, `contact_number`, `institution_status`, `delete_marker`, `pwd_expire_in_days`, `pwd_expiry_notification_start_in_days`) VALUES (11, 'TestAdded', 'sdaasdasdasdasd', 'asdasd', '1231231231', 'A', 'Y', 30, 7)");
			statement.executeUpdate(
					"INSERT INTO `institution` (`institution_id`, `institution_name`, `address`, `contact_person`, `contact_number`, `institution_status`, `delete_marker`, `pwd_expire_in_days`, `pwd_expiry_notification_start_in_days`) VALUES (12, 'Data is added', 'data is added', 'Data is added', '1231231231', 'A', 'Y', 30, 7)");
			statement.executeUpdate(
					"INSERT INTO `institution` (`institution_id`, `institution_name`, `address`, `contact_person`, `contact_number`, `institution_status`, `delete_marker`, `pwd_expire_in_days`, `pwd_expiry_notification_start_in_days`) VALUES (19, 'maxdfsdfsdw', 'noidawr', 'testasdf', '235235235255', 'A', 'Y', 30, 7)");
			statement.executeUpdate(
					"INSERT INTO `institution` (`institution_id`, `institution_name`, `address`, `contact_person`, `contact_number`, `institution_status`, `delete_marker`, `pwd_expire_in_days`, `pwd_expiry_notification_start_in_days`) VALUES (18, 'Apolo', 'Delhi', 'Jacob', '9867453298', 'I', 'N', 30, 7)");
			statement.executeUpdate(
					"INSERT INTO `institution` (`institution_id`, `institution_name`, `address`, `contact_person`, `contact_number`, `institution_status`, `delete_marker`, `pwd_expire_in_days`, `pwd_expiry_notification_start_in_days`) VALUES (21, 'Global', 'chennai', '', '', 'A', 'Y', 30, 7)");
			statement.executeUpdate(
					"INSERT INTO `institution` (`institution_id`, `institution_name`, `address`, `contact_person`, `contact_number`, `institution_status`, `delete_marker`, `pwd_expire_in_days`, `pwd_expiry_notification_start_in_days`) VALUES (22, 'Global', 'Chennai', '', '', 'A', 'Y', 30, 7)");
			statement.executeUpdate(
					"INSERT INTO `institution` (`institution_id`, `institution_name`, `address`, `contact_person`, `contact_number`, `institution_status`, `delete_marker`, `pwd_expire_in_days`, `pwd_expiry_notification_start_in_days`) VALUES (23, 'Global', 'Hyderabad', '', '', 'A', 'Y', 30, 7)");
			statement.executeUpdate(
					"INSERT INTO `institution` (`institution_id`, `institution_name`, `address`, `contact_person`, `contact_number`, `institution_status`, `delete_marker`, `pwd_expire_in_days`, `pwd_expiry_notification_start_in_days`) VALUES (25, 'Global', 'chennai', '', '', 'A', 'Y', 30, 7)");
			statement.executeUpdate(
					"INSERT INTO `institution` (`institution_id`, `institution_name`, `address`, `contact_person`, `contact_number`, `institution_status`, `delete_marker`, `pwd_expire_in_days`, `pwd_expiry_notification_start_in_days`) VALUES (31, 'Nadar ', 'Madhurai', 'nadar', '9867548976', 'A', 'N', 30, 7)");
			statement.executeUpdate(
					"INSERT INTO `institution` (`institution_id`, `institution_name`, `address`, `contact_person`, `contact_number`, `institution_status`, `delete_marker`, `pwd_expire_in_days`, `pwd_expiry_notification_start_in_days`) VALUES (36, 'Samar Hospital', 'Dwarka sec 19', 'Samar', '9867542387', 'A', 'N', 30, 7)");

			statement.executeUpdate(
					"INSERT INTO `clinician`(`clinician_id`,`institution_id`,`email_id`,`first_name`,`last_name`,`employee_id`,`pool_id`,`status`,`type`,`portal_access`,`location`,`delete_marker`,`is_tnc_accepted`,`pwd_updated_date`,`retry_login_attempt_counter`,`last_login_timestamp`,`locale`,`timezone`,`created_by`,`created_timestamp`,`updated_by`,`updated_timestamp`)VALUES(10,1,'clinician1@gmail.com','XYZ', 'XYZ','589216', 'ayush@gmail.com','A', 'CL','MOBILE','india','N', 'N','2019-04-12',1,'2020-04-01 06:57:27','aksn','ahsik','admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(11,1,'clinician2@gmail.com','XYZ', 'XYZ','589216', 'ayush@gmail.com','A', 'CL','MOBILE','india','N', 'N','2019-04-12',1,'2020-04-01 06:57:27','aksn','ahsik','admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(12,1,'clinician3@gmail.com','XYZ', 'XYZ','589216', 'ayush@gmail.com','A', 'CL','MOBILE','india','N', 'N','2019-04-12',1,'2020-04-01 06:57:27','aksn','ahsik','admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(13,1,'clinician4@gmail.com','XYZ', 'XYZ','589216', 'ayush@gmail.com','A', 'CL','MOBILE','india','N', 'N','2019-04-12',1,'2020-04-01 06:57:27','aksn','ahsik','admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(14,1,'clinician5@gmail.com','XYZ', 'XYZ','589216', 'ayush@gmail.com','A', 'CL','MOBILE','india','N', 'N','2019-04-12',1,'2020-04-01 06:57:27','aksn','ahsik','admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(15,1,'clinician6@gmail.com','XYZ', 'XYZ','589216', 'ayush@gmail.com','A', 'CL','MOBILE','india','N', 'N','2019-04-12',1,'2020-04-01 06:57:27','aksn','ahsik','admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(16,1,'clinician7@gmail.com','XYZ', 'XYZ','589216', 'ayush@gmail.com','LI', 'CL','MOBILE','india','N', 'N','2019-04-12',1,'2020-04-01 06:57:27','aksn','ahsik','admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-18 03:19:27'),(17,1,'clinician8@gmail.com','XYZ', 'XYZ','589216', 'ayush@gmail.com','D', 'CL','MOBILE','india','N', 'N','2019-04-12',1,'2020-04-01 06:57:27','aksn','ahsik','admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27')");
		
			statement.executeUpdate(
					"INSERT INTO `admins`(`admin_id`,`institution_id`,`email_id`,`first_name`,`last_name`,`employee_id`,`pool_id`,`status`,`type`,`is_default`,`portal_access`,`location`,`delete_marker`,`is_tnc_accepted`,`pwd_updated_date`,`retry_login_attempt_counter`,`last_login_timestamp`,`locale`,`timezone`,`created_by`,`created_timestamp`,`updated_by`,`updated_timestamp`)VALUES(10,1,'admin1@gmail.com','XYZ', 'XYZ','589216', 'ayush@gmail.com','A', 'CL','Y','MOBILE','india','N', 'N','2019-04-12',1,'2020-04-01 06:57:27','aksn','ahsik','admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(11,0,'admin2@gmail.com','XYZ', 'XYZ','589216', 'ayush@gmail.com','A', 'CL','N','MOBILE','india','N', 'N','2019-04-12',1,'2020-04-01 06:57:27','aksn','ahsik','admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(12,1,'admin3@gmail.com','XYZ', 'XYZ','589216', 'ayush@gmail.com','A', 'CL','Y','MOBILE','india','N', 'N','2019-04-12',1,'2020-04-01 06:57:27','aksn','ahsik','admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(13,0,'admin4@gmail.com','XYZ', 'XYZ','589216', 'ayush@gmail.com','A', 'CL','Y','MOBILE','india','N', 'N','2019-04-12',1,'2020-04-01 06:57:27','aksn','ahsik','admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(14,2,'admin5@gmail.com','XYZ', 'XYZ','589216', 'ayush@gmail.com','A', 'CL','N','MOBILE','india','N', 'N','2019-04-12',1,'2020-04-01 06:57:27','aksn','ahsik','admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(15,3,'admin6@gmail.com','XYZ', 'XYZ','589216', 'ayush@gmail.com','A', 'CL','N','MOBILE','india','N', 'N','2019-04-12',2,'2020-04-01 06:57:27','aksn','ahsik','admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(16,4,'admin7@gmail.com','XYZ', 'XYZ','589216', 'ayush@gmail.com','A', 'CL','N','MOBILE','india','N', 'N','2019-04-12',2,'2020-04-01 06:57:27','aksn','ahsik','admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(17,5,'admin8@gmail.com','XYZ', 'XYZ','589216', 'ayush@gmail.com','A', 'SA','N','MOBILE','india','N', 'N','2019-04-12',2,'2020-04-01 06:57:27','aksn','ahsik','admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(18,6,'admin9@gmail.com','XYZ', 'XYZ','589216', 'ayush@gmail.com','A', 'SA','Y','MOBILE','india','N', 'N','2020-01-24',1,'2020-04-01 06:57:27','aksn','ahsik','admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(19,0,'admin@gmail.com','XYZ', 'XYZ','589216', 'ayush@gmail.com','A', 'SA','Y','MOBILE','india','N', 'N','2019-04-12',1,'2020-04-01 06:57:27','aksn','ahsik','admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(21,0,'admin10@gmail.com','XYZ', 'XYZ','589216', 'ayush@gmail.com','LI', 'SA','Y','MOBILE','india','N', 'N','2019-04-12',1,'2020-04-01 06:57:27','aksn','ahsik','admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27')");
			statement.executeUpdate(
					"INSERT INTO `admins`(`admin_id`,`institution_id`,`email_id`,`first_name`,`last_name`,`employee_id`,`pool_id`,`status`,`type`,`is_default`,`portal_access`,`location`,`delete_marker`,`is_tnc_accepted`,`pwd_updated_date`,`retry_login_attempt_counter`,`last_login_timestamp`,`locale`,`timezone`,`created_by`,`created_timestamp`,`updated_by`,`updated_timestamp`)VALUES(2386,4,'shivtest@hcl.com','shi', 'shi','1234567890', 'us-east-1_tswTIQ0gH','A', 'IA','Y','WEB','india','N', 'N','2020-01-08',0,'2020-03-13 11:51:53','aksn','ahsik','dinesh-kuma@hcl.com','2020-01-08 10:36:11','admin@hcl.com', '2020-04-01 06:59:27')");
			statement.executeUpdate(
					"INSERT INTO `admins`(`admin_id`,`institution_id`,`email_id`,`first_name`,`last_name`,`employee_id`,`pool_id`,`status`,`type`,`is_default`,`portal_access`,`location`,`delete_marker`,`is_tnc_accepted`,`pwd_updated_date`,`retry_login_attempt_counter`,`last_login_timestamp`,`locale`,`timezone`,`created_by`,`created_timestamp`,`updated_by`,`updated_timestamp`)VALUES(1386,0,'shivtest@hcl.com','shi', 'shi','1234567890', 'us-east-1_tswTIQ0gH','LI', 'IA','Y','WEB','india','N', 'N','2020-01-08',0,'2020-03-13 11:51:53','aksn','ahsik','dinesh-kuma@hcl.com','2020-01-08 10:36:11','admin@hcl.com', '2020-04-01 06:59:27')");
			statement.executeUpdate(
					"INSERT INTO `admins`(`admin_id`,`institution_id`,`email_id`,`first_name`,`last_name`,`employee_id`,`pool_id`,`status`,`type`,`is_default`,`portal_access`,`location`,`delete_marker`,`is_tnc_accepted`,`pwd_updated_date`,`retry_login_attempt_counter`,`last_login_timestamp`,`locale`,`timezone`,`created_by`,`created_timestamp`,`updated_by`,`updated_timestamp`)VALUES(20,0,'var@hcl.com','var', 'var','1234567890', 'us-east-1_tswTIQ0gH','LI', 'IA','Y','WEB','india','N', 'N','2020-01-08',0,'2020-03-13 11:51:53','aksn','ahsik','dinesh-kuma@hcl.com','2020-01-08 10:36:11','admin@hcl.com', '2020-04-01 06:59:27')");

			statement.executeUpdate(
					"INSERT INTO `patient_phi`(`patient_id`,`chf_patient_id`,`institution_id`,`mr_no`,`gender`,`first_name`,`last_name`,`contact_no`,`dob`,`address`,`zip`,`ssn`,`patient_details_json`,`is_threshold_set`,`is_action_open`,`delete_marker`,`created_by`,`created_timestamp`,`updated_by`,`updated_timestamp`)VALUES (1,'c0',1,'m1','m','john','Knight','8302000822','22-09-94','noida','200199','bhs','{\"who_id\": \"who123\", \"zipcode\": \"247667\", \"geo_code\": \"122233\", \"birth_place\": \"Dallas\", \"practice_specific\": \"122233\", \"loc_specific_number\": \"223344\", \"unique_alphanumeric_no\": \"123456abc\", \"enterprise_specific_number\": \"1585018389000\"}',0,0,'N','vartika@gmail.com','2019-04-01 06:57:27','admin@hcl.com', '2019-04-01 06:59:27')");

			statement.executeUpdate(
					"INSERT INTO `patient_phi`(`patient_id`,`chf_patient_id`,`institution_id`,`mr_no`,`gender`,`first_name`,`last_name`,`contact_no`,`dob`,`address`,`zip`,`ssn`,`patient_details_json`,`is_threshold_set`,`is_action_open`,`delete_marker`,`created_by`,`created_timestamp`,`updated_by`,`updated_timestamp`)VALUES (2,'c1',2,'m2','m','jack','Knight','8302000822','22-09-94','noida','200199','bhs','{\"who_id\": \"who123\", \"zipcode\": \"247667\", \"geo_code\": \"122233\", \"birth_place\": \"Dallas\", \"practice_specific\": \"122233\", \"loc_specific_number\": \"223344\", \"unique_alphanumeric_no\": \"123456abc\", \"enterprise_specific_number\": \"1585018389000\"}',0,0,'N','vartika@gmail.com','2019-04-01 06:57:27','admin@hcl.com', '2019-04-01 06:59:27')");

			statement.executeUpdate(
					"INSERT INTO `patient_phi`(`patient_id`,`chf_patient_id`,`institution_id`,`mr_no`,`gender`,`first_name`,`last_name`,`contact_no`,`dob`,`address`,`zip`,`ssn`,`patient_details_json`,`is_threshold_set`,`is_action_open`,`delete_marker`,`created_by`,`created_timestamp`,`updated_by`,`updated_timestamp`)VALUES (3,'c2',3,'m3','m','will','Knight','8302000822','22-09-94','noida','200199','bhs','{\"who_id\": \"who123\", \"zipcode\": \"247667\", \"geo_code\": \"122233\", \"birth_place\": \"Dallas\", \"practice_specific\": \"122233\", \"loc_specific_number\": \"223344\", \"unique_alphanumeric_no\": \"123456abc\", \"enterprise_specific_number\": \"1585018389000\"}',0,0,'N','vartika@gmail.com','2019-04-01 06:57:27','admin@hcl.com', '2019-04-01 06:59:27')");
			statement.executeUpdate(
					"INSERT INTO `patient_phi`(`patient_id`,`chf_patient_id`,`institution_id`,`mr_no`,`gender`,`first_name`,`last_name`,`contact_no`,`dob`,`address`,`zip`,`ssn`,`patient_details_json`,`is_threshold_set`,`is_action_open`,`delete_marker`,`created_by`,`created_timestamp`,`updated_by`,`updated_timestamp`)VALUES (4,'c3',4,'m4','m','will','Knight','8302000822','22-09-94','noida','200199','bhs','{\"who_id\": \"who123\", \"zipcode\": \"247667\", \"geo_code\": \"122233\", \"birth_place\": \"Dallas\", \"practice_specific\": \"122233\", \"loc_specific_number\": \"223344\", \"unique_alphanumeric_no\": \"123456abc\", \"enterprise_specific_number\": \"1585018389000\"}',0,0,'N','vartika@gmail.com','2019-04-01 06:57:27','admin@hcl.com', '2019-04-01 06:59:27')");
			statement.executeUpdate(
					"INSERT INTO `patient_phi`(`patient_id`,`chf_patient_id`,`institution_id`,`mr_no`,`gender`,`first_name`,`last_name`,`contact_no`,`dob`,`address`,`zip`,`ssn`,`patient_details_json`,`is_threshold_set`,`is_action_open`,`delete_marker`,`created_by`,`created_timestamp`,`updated_by`,`updated_timestamp`)VALUES (5,'c4',5,'m5','m','will','Knight','8302000822','22-09-94','noida','200199','bhs','{\"who_id\": \"who123\", \"zipcode\": \"247667\", \"geo_code\": \"122233\", \"birth_place\": \"Dallas\", \"practice_specific\": \"122233\", \"loc_specific_number\": \"223344\", \"unique_alphanumeric_no\": \"123456abc\", \"enterprise_specific_number\": \"1585018389000\"}',0,0,'N','vartika@gmail.com','2019-04-01 06:57:27','admin@hcl.com', '2019-04-01 06:59:27')");
			statement.executeUpdate(
					"INSERT INTO `patient_phi`(`patient_id`,`chf_patient_id`,`institution_id`,`mr_no`,`gender`,`first_name`,`last_name`,`contact_no`,`dob`,`address`,`zip`,`ssn`,`patient_details_json`,`is_threshold_set`,`is_action_open`,`delete_marker`,`created_by`,`created_timestamp`,`updated_by`,`updated_timestamp`)VALUES (6,'c5',6,'m6','m','will','Knight','8302000822','22-09-94','noida','200199','bhs','{\"who_id\": \"who123\", \"zipcode\": \"247667\", \"geo_code\": \"122233\", \"birth_place\": \"Dallas\", \"practice_specific\": \"122233\", \"loc_specific_number\": \"223344\", \"unique_alphanumeric_no\": \"123456abc\", \"enterprise_specific_number\": \"1585018389000\"}',0,0,'N','vartika@gmail.com','2019-04-01 06:57:27','admin@hcl.com', '2019-04-01 06:59:27')");

			statement.executeUpdate(
					"INSERT INTO `patient_clinician_mapping`(`map_id`,`patient_id`,`clinician_id`,`created_by`,`created_timestamp`,`updated_by`,`updated_timestamp`)VALUES(1, 1, 11, 'clinician4@gmail.com', '2019-04-17 00:00:00', 'clinician4@gmail.com', '2019-04-25 10:08:08'),(2, 2, 12, 'clinician4@gmail.com', '2019-04-17 00:00:00', 'clinician4@gmail.com', '2019-04-25 10:08:08'),(3, 3, 13, 'clinician4@gmail.com', '2019-04-17 00:00:00', 'clinician4@gmail.com', '2019-04-25 10:08:08'),(4, 4, 14, 'clinician4@gmail.com', '2019-04-17 00:00:00', 'clinician4@gmail.com', '2019-04-25 10:08:08'),(5, 5, 15, 'clinician4@gmail.com', '2019-04-17 00:00:00', 'clinician4@gmail.com', '2019-04-25 10:08:08')");
			
			statement.executeUpdate(
					"INSERT INTO `terms_n_conditions` (`tnc_id`, `institution_id`, `created_by`, `created_timestamp`, `updated_by`, `updated_timestamp`, `tnc_status`, `delete_marker`, `tnc_text`) VALUES	(1, 0, 'admin@hcl.com', '2019-06-26 06:12:42', 'automationrobot1@gmail.com', '2019-08-08 10:56:01', 'A', 'N', 'Sample terms and conditions\\r\\npassword should be changed.Accept terms before login 8/8/2019'),	(2, 1, 'ayush@gmail.com', '2019-06-26 06:19:59', 'automationrobot3@gmail.com', '2019-06-27 09:54:03', 'A', 'N', 'terms and conditions changed')");

			statement.executeUpdate(
					"INSERT INTO `audit_logs` (`log_id`, `institution_id`, `user_type`, `activity`, `created_by`, `created_timestamp`) VALUES (1, 1, 'IA', 'created a user Test', 'addition_admin@hcl.com', '2019-04-22 09:45:22')");
			statement.executeUpdate(
					"INSERT INTO `audit_logs` (`log_id`, `institution_id`, `user_type`, `activity`, `created_by`, `created_timestamp`) VALUES (2, 1, 'CL', 'Institution admin has update a user', 'test181@july.com', '2019-04-23 07:41:35')");
			statement.executeUpdate(
					"INSERT INTO `audit_logs` (`log_id`, `institution_id`, `user_type`, `activity`, `created_by`, `created_timestamp`) VALUES (3, 0, 'SA', 'Institution admin has deactivated a user', 'dinesh-kuma@hcl.com', '2019-04-23 07:41:37')");
			statement.executeUpdate(
					"INSERT INTO `audit_logs` (`log_id`, `institution_id`, `user_type`, `activity`, `created_by`, `created_timestamp`) VALUES (4, 1, 'IA', 'Institution admin has created a web app user', 'aqawerty@hotmail.com', '2019-04-23 07:41:39')");
			statement.executeUpdate(
					"INSERT INTO `audit_logs` (`log_id`, `institution_id`, `user_type`, `activity`, `created_by`, `created_timestamp`) VALUES (5, 1, 'CL', 'Login successful', 'ayushra@hl.com', '2019-04-29 10:43:39')");
			statement.executeUpdate(
					"INSERT INTO `audit_logs` (`log_id`, `institution_id`, `user_type`, `activity`, `created_by`, `created_timestamp`) VALUES (6, 1, 'CL', 'Login unsuccessful', 'ayushra@hl.com', '2019-04-29 10:43:39')");
			statement.executeUpdate(
					"INSERT INTO `audit_logs` (`log_id`, `institution_id`, `user_type`, `activity`, `created_by`, `created_timestamp`) VALUES (7, 1, 'CL', 'Login unsuccessful', 'ayushra@hl.com', '2019-04-29 10:43:39')");
			statement.executeUpdate(
					"INSERT INTO `audit_logs` (`log_id`, `institution_id`, `user_type`, `activity`, `created_by`, `created_timestamp`) VALUES (8, 1, 'CL', 'Login unsuccessful', 'ayushra@hl.com', '2019-04-29 10:43:39')");
			statement.executeUpdate(
					"INSERT INTO `audit_logs` (`log_id`, `institution_id`, `user_type`, `activity`, `created_by`, `created_timestamp`) VALUES (9, 1, 'CL', 'Login successful', 'ayushra@hl.com', '2019-04-29 10:43:39')");

			statement.executeUpdate(
					"INSERT INTO `device_inventory`(`kit_id`,`ble_mac`,`device_label_id`,`wearable_serial_number`,`wearable_fw_version`,`basestation_serial_number`,`basestation_fw_version`,`created_by`,`created_timestamp`,`updated_by`,`updated_timestamp`)VALUES(1,'888:8989:878','ADI-1001','234242342355254', '1.0', '23424234234sdfsdf4234236', '1.0','divya@hcl.com', '2020-02-20 17:11:23','divya@hcl.com', '2020-02-20 17:11:23'),(2,'887:8989:878','ADI-1002','234242342355244', '1.0', '23424234234sdfsdf4234235', '1.0','divya@hcl.com', '2020-02-20 17:11:23','divya@hcl.com', '2020-02-20 17:11:23'),(3,'886:8989:878','ADI-1003','234242342355234', '1.0', '23424234234sdfsdf4234234', '1.0','divya@hcl.com', '2020-02-20 17:11:23','divya@hcl.com', '2020-02-20 17:11:23'),(4,'885:8989:878','ADI-1004','234242342355224', '1.0', '23424234234sdfsdf4234233', '1.0','divya@hcl.com', '2020-02-20 17:11:23','divya@hcl.com', '2020-02-20 17:11:23'),(5,'884:8989:878','ADI-1005','234242342355214', '1.0', '23424234234sdfsdf4234232', '1.0','divya@hcl.com', '2020-02-20 17:11:23','divya@hcl.com', '2020-02-20 17:11:23')");
			
			statement.executeUpdate(
					"INSERT INTO `pwd_policy_mgmt` (`pwd_policy_id`, `institution_id`, `pwd_rotation_in_days`, `pwd_min_length`, `pwd_max_length`, `is_caps_allowed`, `is_lower_allowed`, `is_numeric_allowed`, `is_spl_char_allowed`, `retry_login_attempts_allowed`, `pwd_history`, `prohibited_passwords`, `pwd_policy_status`, `delete_marker`, `created_by`, `created_timestamp`, `updated_by`, `updated_timestamp`) VALUES (1, 1, 60, 11, 12, 'Y', 'Y', 'N', 'N', 3, 3, 'admin, hcl, Desk1234@', 'A', 'N', 'admin@hcl.com', '2019-06-26 06:11:58', 'manikandanmah@hcl.com', '2019-07-05 07:54:07'),(2, 2, 30, 8, 20, 'N', 'N', 'N', 'N', 3, 3, 'Test123,WWW', 'A', 'N', 'ayush@gmail.com', '2019-06-26 06:19:59', 'ayush@gmail.com', '2019-07-01 06:19:01'), (3, 3, 30, 8, 20, 'N', 'N', 'N', 'N', 3, 3, 'Test123,WWW', 'A', 'N', 'automationrobot1@gmail.com', '2019-06-26 06:21:27', 'automationrobot1@gmail.com', '2019-06-26 06:21:27'), (4, 4, 30, 8, 17, 'N', 'N', 'N', 'N', 3, 3, 'Test123, WWW', 'A', 'N', 'automationrobot2@gmail.com', '2019-06-26 09:21:19', 'automationrobot4@gmail.com', '2019-06-27 05:14:20'), (5, 5, 30, 8, 20, 'N', 'N', 'N', 'N', 3, 3, 'Test123, WWW', 'A', 'N', 'automationrobot1@gmail.com', '2019-06-26 10:53:20', 'automationrobot1@gmail.com', '2019-06-26 10:53:20'), (6, 6, 30, 8, 20, 'N', 'N', 'N', 'N', 3, 3, 'Test123, WWW', 'A', 'N', 'automationrobot2@gmail.com', '2019-06-26 11:18:12', 'automationrobot2@gmail.com', '2019-06-26 11:18:12'), (7, 7, 30, 8, 20, 'N', 'N', 'N', 'N', 3, 3, 'Test123, WWW', 'A', 'N', 'automationrobot2@gmail.com', '2019-06-26 11:24:37', 'automationrobot2@gmail.com', '2019-06-26 11:24:37'), (8, 8, 30, 8, 20, 'N', 'N', 'N', 'N', 3, 3, 'Test123, WWW', 'A', 'N', 'automationrobot1@gmail.com', '2019-06-26 11:31:31', 'automationrobot1@gmail.com', '2019-06-26 11:31:31'),(9, 10, 30, 8, 20, 'N', 'N', 'N', 'N', 3, 3, 'Test123, WWW', 'A', 'N', 'automationrobot1@gmail.com', '2019-06-26 11:39:43', 'automationrobot1@gmail.com', '2019-06-26 11:39:43'),(10, 0, 30, 8, 20, 'N', 'N', 'N', 'N', 3, 3, 'Test123, WWW', 'A', 'N', 'automationrobot1@gmail.com', '2019-06-26 11:39:43', 'automationrobot1@gmail.com', '2019-06-26 11:39:43')");

			statement.executeUpdate(
					"INSERT INTO`device_input`(`pk_id`,`file_name`,`file_type`,`file_status`,`measurement_id`,`received_timestamp`,`updated_timestamp`)VALUES(1,'file1','doc','ok','m1','2020-04-01 06:57:27','2020-04-01 06:57:27'),(2,'file2','doc','ok','m2','2020-04-01 06:57:27','2020-04-01 06:57:27')");
						
			statement.executeUpdate(
					"INSERT INTO `device_data`(`pk_id`,`system_id`,`measurement_id`,`device_id`,`institution_id`,`chf_patient_id`,`reading_date`,`is_baseline_reading`,`reading_position`,`file_name`,`generated_files_base_url`,`status`,`created_by`,`created_timestamp`,`updated_by`,`updated_timestamp`)VALUES(1, 'vh','m1','77', 1,'c1','2019-04-01 06:56:27',0,'r1','file1','http://file1','A','vartika@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(2, 'vh','m2','78', 2,'c1','2019-04-01 06:59:27',0,'r1','file2','http://file2','A','vartika@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(3, 'vh','m3','79', 2,'c1','2019-04-01 06:59:27',1,'r1','file2','http://file2','A','vartika@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(4, 'vh','m4','76', 2,'c1','2019-04-01 06:59:27',0,'r1','file2','http://file2','A','vartika@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(5, 'vh','m5','75', 2,'c1','2019-04-01 06:59:27',0,'r1','file2','http://file2','A','vartika@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(6, 'vh','m6','74', 2,'c2','2019-04-01 06:59:27',0,'r1','file2','http://file2','A','vartika@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27')");
			statement.executeUpdate(
					"INSERT INTO `derived_data` (`derived_data_id`, `msg_id`, `supine_tidal_vol`, `supine_respiration_rate`, `supine_respiration_rate_trend`, `supine_rr_tv`, `fowler_tidal_vol`, `fowler_respiration_rate`, `fowler_respiration_rate_trend`, `fowler_rr_tv`, `delta_z`, `s3_energy`, `heart_rate`, `heart_rate_trend`, `vital_trend`, `measurement_trend`, `reading_compliance`, `s3_trend`, `tidal_vol_trend`, `thoracic_impedance`, `thoracic_impedance_trend`, `created_by`, `created_timestamp`, `updated_by`, `updated_timestamp`) VALUES (1, 1, 100, 120, 1, 230, 120, 120, 0, 120, 230, 120, 90, -1, 0, 1, 0, -1, 0, 90, 1, 'sandeep@hcl.com', '2019-06-26 12:00:58', NULL, '2019-07-02 14:56:51'), (2, 2, 70, 112, 0, 324, 80, 90, -1, 30, 302, 20, 100, 1, -1, 0, 1, 1, 1, 68, 0, 'sandeep@hcl.com', '2019-06-26 12:00:58', NULL, '2019-07-08 09:40:30'), (3, 3, 115, 20, -1, 23, 39, 20, 1, 302, 22, 222, 111, 0, 1, -1, -1, 0, -1, 134, -1, 'sandeep@hcl.com', '2019-06-26 12:00:58', NULL, '2019-07-08 09:40:37'), (4, 4, 230, 33, 1, 80, 195, 222, -1, 234, 99, 88, 177, 1, -1, 1, 1, -1, 1, 238, 1, 'sandeep@hcl.com', '2019-06-26 12:00:58', NULL, '2019-07-08 09:42:27'), (5, 5, 324, 127, -1, 127, 125, 205, 1, 700, 195, 188, 45, -1, 1, -1, -1, 1, -1, 673, -1, 'sandeep@hcl.com', '2019-06-26 12:00:58', NULL, '2019-07-08 09:42:58'), (6, 6, 388, 220, 0, 234, 230, 127, 0, 124, 34, 124, 273, 0, 0, 0, 0, 0, 0, 80, 0, 'sandeep@hcl.com', '2019-06-26 12:00:58', NULL, '2019-07-08 09:43:04'), (7, 7, 187, 134, 1, 452, 78, 230, 1, 1, 1, 1, 334, 1, 1, 1, 1, 1, 1, 532, 1, 'sandeep@hcl.com', '2019-06-26 12:00:58', NULL, '2019-07-08 09:43:11'), (8, 8, 87, 67, -1, 100, 99, 74, -1, -1, -1, -1, 77, -1, -1, -1, -1, -1, -1, 457, -1, 'sandeep@hcl.com', '2019-06-26 12:00:58', NULL, '2019-07-08 09:43:22')");
			statement.executeUpdate(
					"INSERT INTO `data_archival_policy_mgmt` (`data_archival_policy_id`, `institution_id`, `archival_period_in_months`, `auto_archival_frequency`, `auto_log_off_time_in_minutes`, `duration_to_store_audit_logs_in_months`, `data_archival_policy_status`, `delete_marker`, `created_by`, `created_timestamp`, `updated_by`, `updated_timestamp`) VALUES (1, 1, 12, 'ONCE_IN_A_YEAR', 45, 6, 'A', 'N', 'admin@hcl.com', '2019-06-26 11:44:12', 'password@gmail.com', '2019-07-09 10:52:51'), (2, 1, 12, 'ONCE_IN_A_MONTH', 15, 6, 'A', 'N', 'ayush@gmail.com', '2019-06-26 06:19:59', 'automationrobot3@gmail.com', '2019-06-27 09:54:19'), (3, 3, 12, 'ONCE_IN_A_MONTH', 15, 6, 'A', 'N', 'automationrobot1@gmail.com', '2019-06-26 06:21:27', 'automationrobot1@gmail.com', '2019-06-26 06:21:27'), (4, 4, 12, 'ONCE_IN_A_MONTH', 15, 6, 'A', 'N', 'automationrobot2@gmail.com', '2019-06-26 09:21:19', 'automationrobot4@gmail.com', '2019-07-09 12:08:19'), (5, 5, 12, 'ONCE_IN_A_MONTH', 15, 6, 'A', 'N', 'automationrobot1@gmail.com', '2019-06-26 10:53:20', 'automationrobot1@gmail.com', '2019-06-26 10:53:20'), (6, 6, 12, 'ONCE_IN_A_MONTH', 15, 6, 'A', 'N', 'automationrobot2@gmail.com', '2019-06-26 11:18:12', 'automationrobot2@gmail.com', '2019-06-26 11:18:12'), (7, 7, 12, 'ONCE_IN_A_MONTH', 15, 6, 'A', 'N', 'automationrobot2@gmail.com', '2019-06-26 11:24:37', 'automationrobot2@gmail.com', '2019-06-26 11:24:37'),(8, 8, 12, 'ONCE_IN_A_MONTH', 15, 6, 'A', 'N', 'automationrobot1@gmail.com', '2019-06-26 11:31:31', 'automationrobot1@gmail.com', '2019-06-26 11:31:31'),(9, 10, 12, 'ONCE_IN_A_MONTH', 15, 6, 'A', 'N', 'automationrobot1@gmail.com', '2019-06-26 11:39:43', 'automationrobot1@gmail.com', '2019-06-26 11:39:43'), (10, 0, 12, 'ONCE_IN_A_MONTH', 15, 6, 'A', 'N', 'karthick.ng@analog.com', '2019-06-28 11:34:22', 'karthick.ng@analog.com', '2019-06-28 11:34:22')");
			
			statement.executeUpdate(
					"INSERT INTO `compliance_settings`(`pk_id`,`institution_id`,`compliance_period`,`status`,`created_by`,`created_timestamp`,`updated_by`,`updated_timestamp`) VALUES (0,0,1,1,'vartika@gmail.com','2019-04-01 06:57:27','admin@hcl.com','2019-04-01 06:59:27')");
			statement.executeUpdate(
					"INSERT INTO `compliance_settings`(`pk_id`,`institution_id`,`compliance_period`,`status`,`created_by`,`created_timestamp`,`updated_by`,`updated_timestamp`) VALUES (1,1,1,1,'vartika@gmail.com','2019-04-01 06:57:27','admin@hcl.com','2019-04-01 06:59:27')");
			statement.executeUpdate(
					"INSERT INTO `compliance_settings`(`pk_id`,`institution_id`,`compliance_period`,`status`,`created_by`,`created_timestamp`,`updated_by`,`updated_timestamp`) VALUES (2,2,1,1,'vartika@gmail.com','2019-04-01 06:57:27','admin@hcl.com','2019-04-01 06:59:27')");
			statement.executeUpdate(
					"INSERT INTO `compliance_settings`(`pk_id`,`institution_id`,`compliance_period`,`status`,`created_by`,`created_timestamp`,`updated_by`,`updated_timestamp`) VALUES (3,3,1,2,'vartika@gmail.com','2019-04-01 06:57:27','admin@hcl.com','2019-04-01 06:59:27')");
			
			statement.executeUpdate(
					"INSERT INTO `patient_threshold`(`threshold_id`,`patient_id`,`system_id`,`created_by`,`created_timestamp`,`updated_by`,`updated_timestamp`)VALUES(1,1, 'vh', 'vartika@gmail.com', '2019-04-01 06:57:27', 'admin@hcl.com', '2019-04-01 06:59:27'),(2,2, 'vh', 'vartika@gmail.com', '2019-04-01 06:57:27', 'admin@hcl.com', '2019-04-01 06:59:27'),(3,3, 'vh', 'vartika@gmail.com', '2019-04-01 06:57:27', 'admin@hcl.com', '2019-04-01 06:59:27'),(4,4, 'vh', 'vartika@gmail.com', '2019-04-01 06:57:27', 'admin@hcl.com', '2019-04-01 06:59:27'),(5,5, 'vh', 'vartika@gmail.com', '2019-04-01 06:57:27', 'admin@hcl.com', '2019-04-01 06:59:27'),(6,6, 'vh', 'vartika@gmail.com', '2019-04-01 06:57:27', 'admin@hcl.com', '2019-04-01 06:59:27')");
			statement.executeUpdate(
					"INSERT INTO `threshold_policy`(`threshold_policy_id`,`institution_id`,`threshold_policy`,`status`,`created_by`,`created_timestamp`,`updated_by`,`updated_timestamp`)VALUES(1,1,'[{\"controls\": [{\"unit\": \"\", \"unitDesc\": \"absolute comparison\", \"unitName\": \"Min\", \"unitValue\": \"\"}], \"paramDesc\": \"absolute comparison\",\"paramName\": \"QT (ms)\", \"validations\": {\"max\": \"700\", \"min\": \"200\", \"unit\": \"ms\", \"errMsgKey\": \"ERR_QT\"}}]','A','admin@gmail.com','2020-04-02 06:57:27','admin@hcl.com', '2020-04-02 06:59:27')");
			statement.executeUpdate(
					"INSERT INTO `threshold_policy`(`threshold_policy_id`,`institution_id`,`threshold_policy`,`status`,`created_by`,`created_timestamp`,`updated_by`,`updated_timestamp`)VALUES(2,2,'[{\"controls\": [{\"unit\": \"\", \"unitDesc\": \"absolute comparison\", \"unitName\": \"Min\", \"unitValue\": \"\"}], \"paramDesc\": \"absolute comparison\",\"paramName\": \"QT (ms)\", \"validations\": {\"max\": \"700\", \"min\": \"200\", \"unit\": \"ms\", \"errMsgKey\": \"ERR_QT\"}}]','A','admin@gmail.com','2020-04-02 06:57:27','admin@hcl.com', '2020-04-02 06:59:27')");
			statement.executeUpdate(
					"INSERT INTO `threshold_policy`(`threshold_policy_id`,`institution_id`,`threshold_policy`,`status`,`created_by`,`created_timestamp`,`updated_by`,`updated_timestamp`)VALUES(3,3,'[{\"controls\": [{\"unit\": \"\", \"unitDesc\": \"absolute comparison\", \"unitName\": \"Min\", \"unitValue\": \"\"}], \"paramDesc\": \"absolute comparison\",\"paramName\": \"QT (ms)\", \"validations\": {\"max\": \"700\", \"min\": \"200\", \"unit\": \"ms\", \"errMsgKey\": \"ERR_QT\"}}]','A','admin@gmail.com','2020-04-02 06:57:27','admin@hcl.com', '2020-04-02 06:59:27')");
			statement.executeUpdate(
					"INSERT INTO `threshold_policy`(`threshold_policy_id`,`institution_id`,`threshold_policy`,`status`,`created_by`,`created_timestamp`,`updated_by`,`updated_timestamp`)VALUES(4,4,'[{\"controls\": [{\"unit\": \"\", \"unitDesc\": \"absolute comparison\", \"unitName\": \"Min\", \"unitValue\": \"\"}], \"paramDesc\": \"absolute comparison\",\"paramName\": \"QT (ms)\", \"validations\": {\"max\": \"700\", \"min\": \"200\", \"unit\": \"ms\", \"errMsgKey\": \"ERR_QT\"}}]','A','admin@gmail.com','2020-04-02 06:57:27','admin@hcl.com', '2020-04-02 06:59:27')");
			statement.executeUpdate(
					"INSERT INTO `threshold_policy`(`threshold_policy_id`,`institution_id`,`threshold_policy`,`status`,`created_by`,`created_timestamp`,`updated_by`,`updated_timestamp`)VALUES(5,0,'[{\"controls\": [{\"unit\": \"\", \"unitDesc\": \"absolute comparison\", \"unitName\": \"Min\", \"unitValue\": \"\"}], \"paramDesc\": \"absolute comparison\",\"paramName\": \"QT (ms)\", \"validations\": {\"max\": \"700\", \"min\": \"200\", \"unit\": \"ms\", \"errMsgKey\": \"ERR_QT\"}}]','A','admin@gmail.com','2020-04-02 06:57:27','admin@hcl.com', '2020-04-02 06:59:27')");
			
			statement.executeUpdate(
					"INSERT INTO `patient_provider_mapping`(`patient_provider_mapping_id`,`chf_patient_id`,`provider_id`,`delete_marker`,`is_default`,`created_by`,`created_timestamp`,`updated_by`,`updated_timestamp`)VALUES(1,'c1',1,'Y',0,'vartika@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(2,'c2',2,'Y',0,'vartika@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(3,'c3',3,'Y',0,'vartika@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(4,'c4',4,'N',0,'vartika@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(5,'c5',5,'N',0,'vartika@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27')");
			
			statement.executeUpdate(
					"INSERT INTO `patient_vitals`(`patient_vitals_id`,`patient_id`,`systolic_bp`,`diastolic_bp`,`weight`,`temperature`,`reading_date`,`custom_vitals`,`created_by`,`created_timestamp`,`updated_by`,`updated_timestamp`)VALUES(1,1,100,120,'68.5','29.8','2020-04-01 06:57:27','[{\"label\": \"l1\", \"value\": \"5\"}]','admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(2,2,100,120,'68.5','29.8','2020-04-01 06:57:27','[{\"label\": \"l1\", \"value\": \"5\"}]','admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(3,3,100,120,'68.5','29.8','2020-04-01 06:57:27','[{\"label\": \"l1\", \"value\": \"5\"}]','admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27')");
			
			statement.executeUpdate(
					"INSERT INTO `threshold_breach_data`(`threshold_breach_id`,`system_id`,`measurement_id`,`threshold_breach_data`,`severity`,`is_active`,`created_by`,`created_timestamp`,`updated_by`,`updated_timestamp`)VALUES(1,'s1','m1','[{\"patientid\": 1, \"email\": \"abc\"}]','L',1,'admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(2,'s1','m2','[{\"patientid\": 2, \"email\": \"abc\"}]','L',1,'admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(3,'s1','m3','[{\"patientid\": 3, \"email\": \"abc\"}]','L',1,'admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(4,'s1','m4','[{\"patientid\": 1, \"email\": \"abc\"}]','L',1,'admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27')");
			
			statement.executeUpdate(
					"INSERT INTO `contact_us`(`requestId`,`requester_name`,`from_email`,`to_email`,`cc_email`,`subject`,`message`,`is_copy_requested`,`ses_msg_id`,`created_by`,`created_timestamp`,`updated_by`,`updated_timestamp`)VALUES(1,'var','admin@gmail.com','abc@hcl.com','VV@gmail.com','test','test',0,'sajajak','admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(2,'var','admin@gmail.com','abc@hcl.com','VV@gmail.com','test','test',0,'sajajak','admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(3,'var','admin@gmail.com','abc@hcl.com','VV@gmail.com','test','test',0,'sajajak','admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(4,'var','admin@gmail.com','abc@hcl.com','VV@gmail.com','test','test',0,'sajajak','admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(5,'var','admin@gmail.com','abc@hcl.com','VV@gmail.com','test','test',0,'sajajak','admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27')");
			
			statement.executeUpdate(
					"INSERT INTO `help_center_master`(`master_id`,`subject`,`created_by`,`created_timestamp`)VALUES(1,'sub1','admin@gmail.com','2020-04-01 06:57:27'),(2,'sub2','admin@gmail.com','2020-04-01 06:57:27'),(3,'sub3','admin@gmail.com','2020-04-01 06:57:27'),(4,'sub4','admin@gmail.com','2020-04-01 06:57:27'),(5,'sub5','admin@gmail.com','2020-04-01 06:57:27')");
			
			statement.executeUpdate(
					"INSERT INTO `help_center`(`help_id`,`help_topic`,`help_desc`,`help_url`,`help_type`,`created_by`,`created_timestamp`,`updated_by`,`updated_timestamp`)VALUES(1,'ht1','ht','helptopic.com','sb','admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(2,'ht2','ht','helptopic.com','sb','admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(3,'ht3','ht','helptopic.com','sb','admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(4,'ht4','ht','helptopic.com','sb','admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(5,'ht5','ht','helptopic.com','sb','admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27')");
			
			statement.executeUpdate(
					"INSERT INTO `healthcare_provider`(`provider_id`,`institution_id`,`provider_name`,`hospital_affilation`,`doctor_name`,`doctor_contact_no`,`doctor_email`,`other_contact_number`,`delete_marker`,`created_by`,`created_timestamp`,`updated_by`,`updated_timestamp`)VALUES(1, 0, 'shivendra1', 'applo1', 'shivendra123', '9711135753', 'abc@gmail.com', '9811135752', 'N', 'admin@admin.com', '2020-03-19 12:14:31', 'abc@gmail.com', '2020-03-23 02:10:56'),(2, 0, 'shivendra2', 'applo1', 'shivendra123', '9711135753', 'abc@gmail.com', '9811135752', 'N', 'admin1@admin.com','2020-03-22 05:10:09', 'abc@gmail.com', '2020-03-23 10:32:22'),(3, 1, 'shivendrat', 'apploloo', 'shivendraty', '9711135753', 'adi@ad.com', '9711135756', 'N', 'admin1@admin.com', '2020-03-19 12:14:31', 'rameshmenan.k@hcl.com', '2020-03-28 13:35:37'),(4, 1, 'shivendrat', 'apploloo', 'shivendraty', '9711135753', 'adi@ad.com', '9711135756', 'N', 'admin1@admin.com', '2020-03-19 12:14:31', 'rameshmenan.k@hcl.com', '2020-03-28 13:35:37'),(5, 54, 'shivendrat', 'apploloo', 'shivendraty', '9711135753', 'adi@ad.com', '9711135756', 'N', 'admin1@admin.com', '2020-03-19 12:14:31', 'rameshmenan.k@hcl.com', '2020-03-28 13:35:37')");
			
			statement.executeUpdate(
					"INSERT INTO `chf_patient_id_policy`(`pk_id`,`institution_id`,`is_emr_ehr_enabled`,`label`,`label_rule`,`label_separator`,`label_sequence`,`created_by`,`created_timestamp`)VALUES(1,1,'N','Date of Birth','MMYYYY','.[Period]',1,'manikandanmah@hcl.com','2019-11-05 09:47:01'),(2,2,'N','Date of Birth','MMYYYY','.[Period]',1,'manikandanmah@hcl.com','2019-11-05 09:47:01'),(3,3,'N','Date of Birth','MMYYYY','.[Period]',1,'manikandanmah@hcl.com','2019-11-05 09:47:01'),(4,4,'N','Date of Birth','MMYYYY','.[Period]',1,'manikandanmah@hcl.com','2019-11-05 09:47:01'),(5,5,'N','Date of Birth','MMYYYY','.[Period]',1,'manikandanmah@hcl.com','2019-11-05 09:47:01')");
			
			statement.executeUpdate(
					"INSERT INTO `patient_device_mapping`(`mapping_id`,`system_id`,`kit_id`,`chf_patient_id`,`mapping_status`,`created_by`,`created_timestamp`,`updated_by`,`updated_timestamp`)VALUES(1,'djLyqSSZ10sIrMEwFPkT9a1586326135',1,'c1','A','vartika@gmail.com','2020-04-08 06:08:55','vartika@gmail.com','2020-04-08 06:08:55'),(2,'djLyqSSZ10sIrMEwFPkT9a1586326134',2,'c2','A','vartika@gmail.com','2020-04-08 06:08:55','vartika@gmail.com','2020-04-08 06:08:55'),(3,'djLyqSSZ10sIrMEwFPkT9a1586326133',3,'c3','A','vartika@gmail.com','2020-04-08 06:08:55','vartika@gmail.com','2020-04-08 06:08:55')");
			
			statement.executeUpdate(
					"INSERT INTO `patient_actions`(`action_id`,`patient_id`,`priority`,`due_date`,`subject`,`details`,`status`,`created_by`,`created_timestamp`,`updated_by`,`updated_timestamp`)VALUES(10,1,'STANDARD','2019-08-02','Hello Testing First','First', 'CLOSED', 'dk1326@hcl1.com','2019-08-01 10:32:57', 'dk1326@hcl1.com', '2019-08-02 20:15:51'),(11,1,'STANDARD','2019-08-02','Hello Testing First','First', 'CLOSED', 'dk1326@hcl1.com','2019-08-01 10:32:57', 'dk1326@hcl1.com', '2019-08-02 20:15:51'),(12,1,'STANDARD','2019-08-02','Hello Testing First','First', 'CLOSED', 'dk1326@hcl1.com','2019-08-01 10:32:57', 'dk1326@hcl1.com', '2019-08-02 20:15:51')");
			
			statement.executeUpdate(
					"INSERT INTO `patient_action_comments`(`comment_id`,`action_id`,`comments`,`created_by`,`created_timestamp`)VALUES(1,10,'Patient will ok','admin1@gmail.com','2019-08-01 10:32:57'),(2,10,'Patient will ok','admin1@gmail.com','2019-08-01 10:32:57'),(3,11,'Patient will ok','admin1@gmail.com','2019-08-01 10:32:57')");
			
			statement.executeUpdate(
					"INSERT INTO `clinician_comments`(`comment_id`,`patient_id`,`comment`,`created_by`,`created_timestamp`,`updated_by`,`updated_timestamp`)VALUES(1,1,'comment1','admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(2,1,'comment2','admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(3,1,'comment3','admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(4,2,'comment4','admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27'),(5,2,'comment5','admin@gmail.com','2020-04-01 06:57:27','admin@hcl.com', '2020-04-01 06:59:27')");
			
			statement.executeUpdate(
					"INSERT INTO `patient_info_master`(`id`,`master_type_code`,`key_code`,`key_value`,`delete_marker`,`created_by`,`created_timestamp`,`updated_by`,`updated_timestamp`)VALUES(1,'COM','k1','COPD','N', 'admin@adi.com', '2020-02-25 15:29:52', 'admin@adi.com', '2020-02-27 10:09:28'),(2,'PRO','k2','COPD','N', 'admin@adi.com', '2020-02-25 15:29:52', 'admin@adi.com', '2020-02-27 10:09:28'),(3,'IMP','k3','COPD','N', 'admin@adi.com', '2020-02-25 15:29:52', 'admin@adi.com', '2020-02-27 10:09:28'),(4,'ADM','k1','COPD','N', 'admin@adi.com', '2020-02-25 15:29:52', 'admin@adi.com', '2020-02-27 10:09:28'),(5,'COM','k1','COPD','N', 'admin@adi.com', '2020-02-25 15:29:52', 'admin@adi.com', '2020-02-27 10:09:28')");
			
			statement.executeUpdate(
					"INSERT INTO `patient_master_mapping`(`id`,`master_id`,`patient_id`,`delete_marker`,`created_by`,`created_timestamp`,`updated_by`,`updated_timestamp`)VALUES(1,1,1,'N','admin@adi.com', '2020-02-25 15:29:52', 'admin@adi.com', '2020-02-27 10:09:28'),(2,2,2,'Y','admin@adi.com', '2020-02-25 15:29:52', 'admin@adi.com', '2020-02-27 10:09:28'),(3,3,3,'Y','admin@adi.com', '2020-02-25 15:29:52', 'admin@adi.com', '2020-02-27 10:09:28'),(4,4,4,'N','admin@adi.com', '2020-02-25 15:29:52', 'admin@adi.com', '2020-02-27 10:09:28')");
			
			statement.executeUpdate(
					"INSERT INTO `patient_master_other_mapping`(`id`,`master_type_code`,`patient_id`,`label_text`,`delete_marker`,`created_by`,`created_timestamp`,`updated_by`,`updated_timestamp`)VALUES(1,'COM',1,'COPD','N', 'admin@adi.com', '2020-02-25 15:29:52', 'admin@adi.com', '2020-02-27 10:09:28'),(2,'PRO',2,'COPD','N', 'admin@adi.com', '2020-02-25 15:29:52', 'admin@adi.com', '2020-02-27 10:09:28'),(3,'IMP',3,'COPD','N', 'admin@adi.com', '2020-02-25 15:29:52', 'admin@adi.com', '2020-02-27 10:09:28'),(4,'ADM',4,'COPD','N', 'admin@adi.com', '2020-02-25 15:29:52', 'admin@adi.com', '2020-02-27 10:09:28'),(5,'COM',5,'COPD','N', 'admin@adi.com', '2020-02-25 15:29:52', 'admin@adi.com', '2020-02-27 10:09:28')");
			
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

	}

	public static void resetConnection(boolean connectionReset) {
		try {
			Constructor<DBConnection> constructor = DBConnection.class.getDeclaredConstructor();
			constructor.setAccessible(true);
			DBConnection dbConnection = constructor.newInstance();

			Field connectionInstance = DBConnection.class.getDeclaredField("connectionInstance");
			connectionInstance.setAccessible(true);
			DBConnection conn = (DBConnection) connectionInstance.get(dbConnection);

			Field connectionField = DBConnection.class.getDeclaredField("connection");
			connectionField.setAccessible(true);

			if (connectionReset) {
				connectionField.set(conn, null);
			} else {
				connectionInstance.set(conn, null);
			}

		} catch (Exception e) {

		}
	}
	
	public static int max(int value) {
        return 10;
    }

	public static void main(String[] args) {
		try {
			setupInMemoryDB();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}