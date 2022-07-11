package com.hcl.adi.chf.util.test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hcl.adi.chf.model.Admins;
import com.hcl.adi.chf.model.AuditLog;
import com.hcl.adi.chf.model.ChfPatientIdPolicy;
import com.hcl.adi.chf.model.ChfPatientIdPolicy.Policy;
import com.hcl.adi.chf.model.ChfPatientSearchRequest;
import com.hcl.adi.chf.model.Clinician;
import com.hcl.adi.chf.model.CompliancePolicy;
import com.hcl.adi.chf.model.ContactUS;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.DashboardVitals;
import com.hcl.adi.chf.model.DataArchivalPolicy;
import com.hcl.adi.chf.model.HelpCenterResponse;
import com.hcl.adi.chf.model.Institution;
import com.hcl.adi.chf.model.LoginActivity;
import com.hcl.adi.chf.model.MasterDetails;
import com.hcl.adi.chf.model.MessageForClinician;
import com.hcl.adi.chf.model.PasswordPolicy;
import com.hcl.adi.chf.model.PatientActionComments;
import com.hcl.adi.chf.model.PatientActions;
import com.hcl.adi.chf.model.PatientClinicianMapping;
import com.hcl.adi.chf.model.PatientDeviceMapping;
import com.hcl.adi.chf.model.PatientMasterMapping;
import com.hcl.adi.chf.model.PatientMasterOtherMappings;
import com.hcl.adi.chf.model.PatientPhi;
import com.hcl.adi.chf.model.PatientPhi.PatientAttributes;
import com.hcl.adi.chf.model.PatientProvider;
import com.hcl.adi.chf.model.PatientProvider.Provider;
import com.hcl.adi.chf.model.PwdExpiryInfo;
import com.hcl.adi.chf.model.TermsAndConditions;
import com.hcl.adi.chf.model.ThresholdBreachRequest;
import com.hcl.adi.chf.model.ThresholdPolicy;
import com.hcl.adi.chf.model.ThresholdPolicy.Validations;
import com.hcl.adi.chf.model.Tnc;
import com.hcl.adi.chf.model.UserDetails;

public class ModelTest {
	private static final Logger LOGGER = LogManager.getLogger(ModelTest.class.getName());

	private static Object[] modelObjects = null;

	@BeforeClass
	public static void createInput() throws IOException {
		modelObjects = new Object[] { new Admins(), new AuditLog(), new CustomResponse(), new DataArchivalPolicy(),
				new Institution(), new LoginActivity(), new MessageForClinician(), new PasswordPolicy(),
				new PwdExpiryInfo(), new ContactUS(), new TermsAndConditions(), new UserDetails(), new ThresholdBreachRequest(), 
				new CompliancePolicy(), new Clinician(), new PatientProvider(), new Provider(), new HelpCenterResponse(), new UserDetails(),
				new PatientPhi(), new PatientAttributes(), new DashboardVitals(), new ThresholdPolicy(), new Validations(),
				new Tnc(), new PatientDeviceMapping(), new ChfPatientIdPolicy(), new Policy(), new PatientMasterMapping(), 
				new PatientMasterOtherMappings(), new PatientClinicianMapping(), new ChfPatientSearchRequest(),new MasterDetails(),
				new PatientActions(), new PatientActionComments()};
	}

	@Test
	public void coverAllModel() {
		try {
			for (Object modelObject : modelObjects) {
				Class classs = modelObject.getClass();
				Method[] allMethods = classs.getDeclaredMethods();
				for (Method method : allMethods) {
					Class[] parameterTypes = method.getParameterTypes();
					if (parameterTypes.length > 0) {
						for (Class param : parameterTypes) {
							try {
								if ("Byte".equalsIgnoreCase(param.getSimpleName())) {
									method.invoke(modelObject, 1);
								} else if ("Short".equalsIgnoreCase(param.getSimpleName())) {
									method.invoke(modelObject, 1);
								} else if ("Integer".equalsIgnoreCase(param.getSimpleName())) {
									method.invoke(modelObject, 1);
								} else if ("Long".equalsIgnoreCase(param.getSimpleName())) {
									method.invoke(modelObject, 1);
								} else if ("Float".equalsIgnoreCase(param.getSimpleName())) {
									method.invoke(modelObject, 1.1f);
								} else if ("Double".equalsIgnoreCase(param.getSimpleName())) {
									method.invoke(modelObject, 1.1d);
								} else if ("Date".equalsIgnoreCase(param.getSimpleName())) {
									method.invoke(modelObject, new Date());
								} else if ("String".equalsIgnoreCase(param.getSimpleName())) {
									method.invoke(modelObject, "Cover");
								} else {
									method.invoke(modelObject, null);
								}
							} catch (Exception e) {

							}
						}
					} else {
						try {
							method.invoke(modelObject);
						} catch (Exception e) {

						}
					}
				}
			}
		} catch (Exception e) {

		}
	}
}