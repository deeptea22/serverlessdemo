package com.hcl.adi.chf.lambda.test;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.PersistPHIDataFromCSV;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.PatientPhi;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.test.SetupInMemoryDBUtil;

public class PersistPHIDataFromCSVTest {
	private static final Logger LOGGER = LogManager.getLogger(PersistPHIDataTest.class.getName());
	private static PatientPhi patientPhi;
	private static Map<String, String> input;

	@BeforeClass
	public static void createInput() throws Exception {
		// set up your sample input object here.

		// It is use to initialize database and create table and insert data
		SetupInMemoryDBUtil.setupInMemoryDB();
		// set up your sample input object here.
		input = new HashMap<String, String>();
		input.put(Constants.FILE_OBJECT_KEY, "PatientPhi.csv");
		input.put(Constants.CREATED_BY, "Ravis");
		input.put(Constants.QUERY_PARAM_INSTITUTION_ID, "2");

		Map<String, String> envMap = new HashMap<String, String>();
		envMap.put("S3_BUCKET_NAME", "adi-chf-patient-phi-upload-data");
		envMap.put("REGION", "us-east-1");
		setEnv(envMap);

	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testPersistPHIDataFromCsv() {
		PersistPHIDataFromCSV handler = new PersistPHIDataFromCSV();
		Context ctx = createContext();

		CustomResponse output = handler.handleRequest(input, ctx);
		LOGGER.info(output);

		// validate output here.
		Assert.assertEquals("OK", output.getDescription());
	}

	protected static void setEnv(Map<String, String> newenv) throws Exception {
		try {
			Class<?> processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment");
			Field theEnvironmentField = processEnvironmentClass.getDeclaredField("theEnvironment");
			theEnvironmentField.setAccessible(true);
			Map<String, String> env = (Map<String, String>) theEnvironmentField.get(null);
			env.putAll(newenv);
			Field theCaseInsensitiveEnvironmentField = processEnvironmentClass
					.getDeclaredField("theCaseInsensitiveEnvironment");
			theCaseInsensitiveEnvironmentField.setAccessible(true);
			Map<String, String> cienv = (Map<String, String>) theCaseInsensitiveEnvironmentField.get(null);
			cienv.putAll(newenv);
		} catch (NoSuchFieldException e) {
			Class[] classes = Collections.class.getDeclaredClasses();
			Map<String, String> env = System.getenv();
			for (Class cl : classes) {
				if ("java.util.Collections$UnmodifiableMap".equals(cl.getName())) {
					Field field = cl.getDeclaredField("m");
					field.setAccessible(true);
					Object obj = field.get(env);
					Map<String, String> map = (Map<String, String>) obj;
					map.clear();
					map.putAll(newenv);
				}
			}
		}
	}
}
