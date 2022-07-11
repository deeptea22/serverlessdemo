package com.hcl.adi.chf.lambda.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.ConfirmPatient;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.PatientProvider;
import com.hcl.adi.chf.model.PatientProvider.Provider;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class ConfirmPatientTest {
	private static final Logger LOGGER = LogManager.getLogger(ConfirmPatientTest.class.getName());
	private static PatientProvider patientProvider;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		List<Provider> providerInfo = new ArrayList<PatientProvider.Provider>();
		patientProvider = new PatientProvider();
		patientProvider.setChfPatientId("GEOSON_ERTY678");
		patientProvider.setInstitutionId(1);
		patientProvider.setCreatedBy("clinician@gmail.com");
		Provider provider = new Provider();
		provider.setDoctorName("John");
		provider.setDoctorContactNumber("899789789");
		provider.setHospitalAffilation("Apollo Hospital");
		provider.setOtherContactNumber("87898989");
		provider.setProviderName("Test clinician");
		providerInfo.add(provider);
		patientProvider.setProviders(providerInfo);
	}

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testConfirmPatient() {
        ConfirmPatient handler = new ConfirmPatient();
        Context ctx = createContext();

        CustomResponse output = handler.handleRequest(patientProvider, ctx);
        LOGGER.info(output);

		// validate output here.
		Assert.assertEquals("OK", output.getDescription());
    }
}
