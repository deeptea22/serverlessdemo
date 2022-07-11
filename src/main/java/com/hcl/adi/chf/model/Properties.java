package com.hcl.adi.chf.model;

/**
 * This is a helper utility class for DashboardVitals model class
 *
 * @author Sandeep Singh
 */
public final class Properties {
	private String key;
	private String value;
	private String type;
	private String date;

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Properties [key=" + key + ", value=" + value + ", type=" + type + ", date=" + date + "]";
	}
}