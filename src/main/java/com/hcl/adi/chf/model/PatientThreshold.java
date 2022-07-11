package com.hcl.adi.chf.model;

import java.util.Date;
import java.util.List;

/**
 * Model class for Patient Threshold
 *
 * @author Sandeep Singh
 */
public final class PatientThreshold extends CustomResponse {
	private Integer thresholdId;
	private Integer patientId;
	private List<Threshold> threshold;
	private String createdBy;
	private Date createdTimestamp;
	private String updatedBy;
	private Date updatedTimestamp;
	
	/**
	 * @return the thresholdId
	 */
	public Integer getThresholdId() {
		return thresholdId;
	}
	/**
	 * @param thresholdId the thresholdId to set
	 */
	public void setThresholdId(Integer thresholdId) {
		this.thresholdId = thresholdId;
	}
	/**
	 * @return the patientId
	 */
	public Integer getPatientId() {
		return patientId;
	}
	/**
	 * @param patientId the patientId to set
	 */
	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}
	
	
	/**
	 * @return the threshold
	 */
	public List<Threshold> getThreshold() {
		return threshold;
	}
	/**
	 * @param threshold the threshold to set
	 */
	public void setThreshold(List<Threshold> threshold) {
		this.threshold = threshold;
	}
	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the createdTimestamp
	 */
	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}
	/**
	 * @param createdTimestamp the createdTimestamp to set
	 */
	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}
	/**
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}
	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	/**
	 * @return the updatedTimestamp
	 */
	public Date getUpdatedTimestamp() {
		return updatedTimestamp;
	}
	/**
	 * @param updatedTimestamp the updatedTimestamp to set
	 */
	public void setUpdatedTimestamp(Date updatedTimestamp) {
		this.updatedTimestamp = updatedTimestamp;
	}
	
	@Override
	public String toString() {
		return "PatientThreshold [thresholdId=" + thresholdId + ", patientId=" + patientId + ",  createdBy=" + createdBy + ", createdTimestamp=" + createdTimestamp
				+ ", updatedBy=" + updatedBy + ", updatedTimestamp=" + updatedTimestamp + "]";
	}
	/**
	 * Class to store Controls
	 */
	public static final class Threshold {
		List<Controls> controls;
		Validations validations;
		String paramName;
		String paramDesc;
		

		/**
		 * @return the controls
		 */
		public List<Controls> getControls() {
			return controls;
		}

		/**
		 * @param controls the controls to set
		 */
		public void setControls(List<Controls> controls) {
			this.controls = controls;
		}

		
		
		/**
		 * @return the validations
		 */
		public Validations getValidations() {
			return validations;
		}

		/**
		 * @param validations the validations to set
		 */
		public void setValidations(Validations validations) {
			this.validations = validations;
		}

		/**
		 * @return the paramDesc
		 */
		public String getParamDesc() {
			return paramDesc;
		}

		/**
		 * @param paramDesc the paramDesc to set
		 */
		public void setParamDesc(String paramDesc) {
			this.paramDesc = paramDesc;
		}

		/**
		 * @return the paramName
		 */
		public String getParamName() {
			return paramName;
		}

		/**
		 * @param paramName the paramName to set
		 */
		public void setParamName(String paramName) {
			this.paramName = paramName;
		}

		@Override
		public String toString() {
			return "Threshold [controls=" + controls + "]";
		}
		
		
	}
	
	/**
	 * Class to store Controls
	 */
	public static final class Controls {

		private String unit;
		private String unitDesc;
		private String unitName;
		private String unitValue;
		/**
		 * @return the unit
		 */
		public String getUnit() {
			return unit;
		}
		/**
		 * @param unit the unit to set
		 */
		public void setUnit(String unit) {
			this.unit = unit;
		}
		/**
		 * @return the unitDesc
		 */
		public String getUnitDesc() {
			return unitDesc;
		}
		/**
		 * @param unitDesc the unitDesc to set
		 */
		public void setUnitDesc(String unitDesc) {
			this.unitDesc = unitDesc;
		}
		/**
		 * @return the unitName
		 */
		public String getUnitName() {
			return unitName;
		}
		/**
		 * @param unitName the unitName to set
		 */
		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}
		/**
		 * @return the unitValue
		 */
		public String getUnitValue() {
			return unitValue;
		}
		/**
		 * @param unitValue the unitValue to set
		 */
		public void setUnitValue(String unitValue) {
			this.unitValue = unitValue;
		}
		
		@Override
		public String toString() {
			return "Controls [unit=" + unit + ", unitDesc=" + unitDesc + ", unitName=" + unitName + ", unitValue="
					+ unitValue + "]";
		}
		
		
		
	}
	
public static final class Validations {
		
		private String min;
		private String max;
		private String unit;
		private String errMsgKey;
		/**
		 * @return the min
		 */
		public String getMin() {
			return min;
		}
		/**
		 * @param min the min to set
		 */
		public void setMin(String min) {
			this.min = min;
		}
		/**
		 * @return the max
		 */
		public String getMax() {
			return max;
		}
		/**
		 * @param max the max to set
		 */
		public void setMax(String max) {
			this.max = max;
		}
		/**
		 * @return the unit
		 */
		public String getUnit() {
			return unit;
		}
		/**
		 * @param unit the unit to set
		 */
		public void setUnit(String unit) {
			this.unit = unit;
		}
		/**
		 * @return the errMsgKey
		 */
		public String getErrMsgKey() {
			return errMsgKey;
		}
		/**
		 * @param errMsgKey the errMsgKey to set
		 */
		public void setErrMsgKey(String errMsgKey) {
			this.errMsgKey = errMsgKey;
		}
		
		
	}
	
	
	
}