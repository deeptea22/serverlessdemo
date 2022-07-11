package com.hcl.adi.chf.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hcl.adi.chf.model.ChfPatientIdPolicy;
import com.hcl.adi.chf.model.ChfPatientIdPolicy.Policy;
import com.hcl.adi.chf.model.PatientPhi;

/**
 * This utility class to generate the chfPatientId 
 * from patient id policy of institution.
 *
 * @author DivyaAg
 */
public final class ChfPatientIdUtil {
	private static final Logger LOGGER = LogManager.getLogger(ChfPatientIdUtil.class.getName());
	private static final Map<String, String> separatorMap = new HashMap<>();
	
	
	private ChfPatientIdUtil() {
		// private constructor
		separatorMap.put("- [Hyphen]", "-");
		separatorMap.put("_ [Underscore]", "_");
		separatorMap.put(": [Colon]", ":");
		separatorMap.put(". [Period]", ".");
		separatorMap.put("[Space]", " ");
		separatorMap.put("None", "");
	}

	/**
	 * This method will return the chfPatientId of a patient based on ppatientDetailsJson 
	 * and its institution patient id policy
	 *
	 * @param patientPhi
	 * @return
	 */
	public static  String getChfPatientId(final PatientPhi patientPhi, final ChfPatientIdPolicy chfPatientIdPolicy) {
		
		String chfPatientId =  "";
		LOGGER.info(" patientDetailsJosn: " + patientPhi);
		
		
		if(chfPatientIdPolicy != null && chfPatientIdPolicy.getPolicyList() != null && 
				chfPatientIdPolicy.getPolicyList().isEmpty() == false){
			
			for ( Policy policy : chfPatientIdPolicy.getPolicyList()) {
				switch(policy.getLabel()) {
					case "First Name":
						{
							switch (policy.getLabelRule()){
								case "Only first initial" :
								{
									chfPatientId += patientPhi.getFirstName().charAt(0) + getSeparator(policy.getLabelSeparator());
									break;
								}
								case "First two letters" :
								{
									chfPatientId += patientPhi.getFirstName().substring(0,2) + getSeparator(policy.getLabelSeparator());
									break;
								}
								case "Only last letter" :
								{
									chfPatientId += patientPhi.getFirstName().charAt(patientPhi.getFirstName().length() - 1) + getSeparator(policy.getLabelSeparator());
									break;
								}
								case "Last two letter" :
								{
									chfPatientId += patientPhi.getFirstName().substring(patientPhi.getFirstName().length() - 2) + getSeparator(policy.getLabelSeparator());
									break;
								}
								case "Full length" :
								{
									chfPatientId += patientPhi.getFirstName() + getSeparator(policy.getLabelSeparator());
									break;
								}
							}
						}
					break;
					case "Last Name":
					{
						switch (policy.getLabelRule()){
							case "Only first initial" :
							{
								chfPatientId += patientPhi.getLastName().charAt(0) + getSeparator(policy.getLabelSeparator());
								break;
							}
							case "First two letters" :
							{
								chfPatientId += patientPhi.getLastName().substring(0,2) + getSeparator(policy.getLabelSeparator());
								break;
							}
							case "Only last letter" :
							{
								chfPatientId += patientPhi.getLastName().charAt(patientPhi.getFirstName().length() - 1) + getSeparator(policy.getLabelSeparator());
								break;
							}
							case "Last two letter" :
							{
								chfPatientId += patientPhi.getLastName().substring(patientPhi.getFirstName().length() - 2) + getSeparator(policy.getLabelSeparator());
								break;
							}
							case "Full length" :
							{
								chfPatientId += patientPhi.getLastName() + getSeparator(policy.getLabelSeparator());
								break;
							}
						}
					}
				break;
					case "Date of Birth":
						chfPatientId += patientPhi.getDoB() + getSeparator(policy.getLabelSeparator());
					break;
					case "Social Security Number":
					{
						switch (policy.getLabelRule()){
							case "First two numbers" :
							{
								chfPatientId += patientPhi.getSsn().substring(0,2) + getSeparator(policy.getLabelSeparator());
								break;
							}
							case "First three numbers" :
							{
								chfPatientId += patientPhi.getSsn().substring(0,3) + getSeparator(policy.getLabelSeparator());
								break;
							}
							case "First four numbers" :
							{
								chfPatientId += patientPhi.getSsn().substring(0,4) + getSeparator(policy.getLabelSeparator());
								break;
							}
							case "Last two numbers" :
							{
								chfPatientId += patientPhi.getSsn().substring(patientPhi.getFirstName().length() - 2) + getSeparator(policy.getLabelSeparator());
								break;
							}
							case "Last three numbers" :
							{
								chfPatientId += patientPhi.getSsn().substring(patientPhi.getFirstName().length() - 3) + getSeparator(policy.getLabelSeparator());
								break;
							}
							case "All numbers" :
							{
								chfPatientId += patientPhi.getSsn() + getSeparator(policy.getLabelSeparator());
								break;
							}
						}
					}
				break;
					case "Other":
					{
						switch (policy.getLabelRule()){
							case "Zipcode(postal code)" :
							{
								chfPatientId += patientPhi.getZip() + getSeparator(policy.getLabelSeparator());
								break;
							}
						// add further cases based on PatientPhi input fields
						}
					}
				break;
				}
				
			}
		}
		

		return chfPatientId;
	}
	/**
	 * This method will return the separator based on labelSeparator provided in the policy
	 *
	 * @param separator
	 * @return
	 */	
	private static  String getSeparator(final String separator) {
		String delimiter = separatorMap.get(separator);
		return delimiter;
	}

	
}
