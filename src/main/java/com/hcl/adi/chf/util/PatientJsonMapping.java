package com.hcl.adi.chf.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum for json fields in Patient json field.
 * 
 * @author ravishankar_g
 */
public enum PatientJsonMapping {
    //first name
    first_name("First Name"),
    //last name
    last_name("Last Name"),
    //date of Dirth
    dob("Date of Birth"),
    // zipcode
    zip("Zipcode"),
    // place of birth
    birth_place("Place of birth"),
    // 6-digit geo code (long + lat)
    geo_code("6-digit geo code (long + lat)"),
    // World Health Org T:Id
    who_id("World Health Org T:Id"),
    // Mother’s maiden name
    mother_maiden_name("Mother’s maiden name"),
    // Unique Alpha Numeric Number
    unique_alphanumeric_no("Unique Alpha Numeric Number"),
    // Enterprise Specific Number
    enterprise_specific_number("Enterprise Specific Number"),
    // Practice Specific
    practice_specific("Practice Specific"),
    // Location Specific Number
    loc_specific_number("Location Specific Number");

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private PatientJsonMapping(String value) {
        this.value = value;
    }

    // ****** Reverse Lookup Implementation************//

    // Lookup table
    private static final Map<String, PatientJsonMapping> lookup = new HashMap<>();

    // Populate the lookup table on loading time
    static {
        for (PatientJsonMapping patientMapping : PatientJsonMapping.values()) {
            lookup.put(patientMapping.getValue(), patientMapping);
        }
    }

    // This method can be used for reverse lookup purpose
    public static PatientJsonMapping getDbColumnName(String value) {
		return lookup.get(value);
	}
}


