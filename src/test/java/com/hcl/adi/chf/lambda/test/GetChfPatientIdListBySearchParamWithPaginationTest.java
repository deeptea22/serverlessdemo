package com.hcl.adi.chf.lambda.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.GetChfPatientIdListBySearchParamWithPagination;
import com.hcl.adi.chf.model.ChfPatientListResponseForMobileApp;
import com.hcl.adi.chf.model.ChfPatientSearchRequest;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.PatientActions;

public class GetChfPatientIdListBySearchParamWithPaginationTest {
	private static final Logger LOGGER = LogManager.getLogger(GetChfPatientIdListBySearchParamWithPaginationTest.class.getName());
	private static ChfPatientSearchRequest patientSearchReq = null;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		patientSearchReq = new ChfPatientSearchRequest();
		patientSearchReq.setInstitutionId(1);
		patientSearchReq.setPolicyField("abc");
		patientSearchReq.setPolicyValue("abc");
		patientSearchReq.setPageCount(1);
		patientSearchReq.setPageStartIndex(0);
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testGetChfPatientIdListBySearchParamWithPagination() {
		GetChfPatientIdListBySearchParamWithPagination handler = new GetChfPatientIdListBySearchParamWithPagination();
		Context ctx = createContext();

		ChfPatientListResponseForMobileApp chfPatientListResponseForMobileApp = handler.handleRequest(patientSearchReq, ctx);
		LOGGER.info(chfPatientListResponseForMobileApp);

		// validate output here.
		Assert.assertNotNull(chfPatientListResponseForMobileApp);
	}
}