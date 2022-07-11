package com.hcl.adi.chf.util;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.hcl.adi.chf.model.DeviceData.Ecg;

/**
 * This utility class will be used to read contents from s3 bucket
 *
 * @author AyushRa
 */
public final class S3Util {
	private static final Logger LOGGER = LogManager.getLogger(S3Util.class.getName());

	private S3Util() {
		// private constructor
	}

	/**
	 * This method will read contents from specified S3 bucket and will return
	 * those contents as string which will have escape characters such as:
	 * /n,/r,/t etc.
	 *
	 * @param bucketName
	 * @param key
	 * @return
	 * @throws IOException
	 */
	public static String readContentsFromS3AndReturnAsString(final String bucketName, final String key) throws IOException {
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(System.getenv("AWS_REGION"))).build();
		
		LOGGER.info("::::::::::::::::::::::readJsonFromS3 method::::::::::::::::::::::Started");

		S3Object s3object = s3Client.getObject(new GetObjectRequest(bucketName, key));

		String restuledJson = IOUtils.toString(s3object.getObjectContent());

		LOGGER.info("::::::::::::::::::::::readJsonFromS3 method::::::::::::::::::::::Completed");

		return restuledJson;
	}

	/**
	 * This method will read contents from specified S3 bucket and will return
	 * those contents as json which will not have escape characters
	 *
	 * @param bucketName
	 * @param key
	 * @return
	 * @throws IOException
	 */
	public static Object readContentsFromS3AndReturnAsJson(final String bucketName, final String key) throws IOException {
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(System.getenv("AWS_REGION"))).build();

		LOGGER.info("::::::::::::::::::::::readJsonFromS3 method::::::::::::::::::::::Started");

		S3Object s3object = s3Client.getObject(new GetObjectRequest(bucketName, key));

		Object restuledJson = JsonUtil.jsonInputStreamToJavaObject(s3object.getObjectContent(), new TypeReference<List<Map<String, Object>>>() {});

		LOGGER.info("::::::::::::::::::::::readJsonFromS3 method::::::::::::::::::::::Completed");

		return restuledJson;
	}
	
	
	/**
	 * This method will read contents from specified S3 bucket and will write
	 * those contents in the specified file
	 *
	 * @param bucketName
	 * @param key
	 * @param outputFileName
	 * @throws IOException
	 */
	public static void readContentsFromS3AndWriteIntoLocalFile(final String bucketName, final String key, final String outputFileName) throws IOException {
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(System.getenv("AWS_REGION"))).build();

		LOGGER.info("::::::::::::::::::::::readJsonFromS3AndWriteIntoLocalFile method::::::::::::::::::::::Started");

		S3Object s3object = s3Client.getObject(new GetObjectRequest(bucketName, key));

		S3ObjectInputStream is = s3object.getObjectContent();
		FileOutputStream fos = new FileOutputStream(outputFileName);

		long numberOfBytesCopied = IOUtils.copy(is, fos);

		LOGGER.info("Number of bytes copied into file: " + numberOfBytesCopied);

		LOGGER.info("::::::::::::::::::::::readJsonFromS3AndWriteIntoLocalFile method::::::::::::::::::::::Completed");
	}
	
	
	/**
	 * This method is specifically used to read contents from ECG S3 bucket and will write
	 * those contents in ECG array list.
	 * The method is exclusive for ECG contect mapping
	 *
	 * @param bucketName
	 * @param key
	 * 
	 * @throws IOException
	 */
	public static Ecg readContentsFromS3CSVAndWriteIntoLocalFile(final String bucketName, final String key) throws IOException {
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(System.getenv("REGION_NAME_FOR_S3_BUCKET"))).build();
		Ecg result = new Ecg();
		LOGGER.info("::::::::::::::::::::::readEcgContentFromS3AndWriteIntoArrayList method::::::::::::::::::::::Started");
		final String DELIMITER = ",";
		List<Float> time = new ArrayList<>();
		List<Float> value = new ArrayList<>();

		try {
			S3Object s3object = s3Client.getObject(new GetObjectRequest(bucketName, key));
			BufferedReader reader = new BufferedReader(new InputStreamReader(s3object.getObjectContent()));
			String line;
			// skip the header of ECG CSV file to include only values in list
			LOGGER.info("ECG Header : "+ reader.readLine());
						
			while((line = reader.readLine()) != null) {
				String[] list = line.split(DELIMITER);
				time.add(Float.parseFloat(list[0]));
				value.add(Float.parseFloat(list[1]));
				
			}
			// setting up the start time, interval and endTime for ECG time values to optimise the 
			//data size as they are fixed values for every ECG data
			result.setStartTime(time.get(0));
			result.setEndTime(time.get(time.size() - 1));
			result.setInterval(time.get(1) - time.get(0));
			result.setEcgValue(value);

			
		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}
		LOGGER.info("::::::::::::::::::::::readEcgContentFromS3AndWriteIntoArrayList method::::::::::::::::::::::Completed");
		
		return result;
	}
	
	public static InputStream getFileContentsAsInputStreamFromS3(final String bucketName, final String absolutefileName)
			throws Exception {
		LOGGER.info("::::::::::::::::::::::getFileContentsAsInputStreamFromS3 method::::::::::::::::::::::Started");

		InputStream inputStream = null;

		try {
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
					.withCredentials(DefaultAWSCredentialsProviderChain.getInstance()).withRegion(Constants.REGION).build();

			S3Object s3object = s3Client.getObject(bucketName, absolutefileName);
			inputStream = s3object.getObjectContent().getDelegateStream();

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());

			throw e;
		}

		LOGGER.info(":::::::::::::::::::::getFileContentsAsInputStreamFromS3 method:::::::::::::::::::::Completed");

		return inputStream;
	}

	/**
	 * Just to test the functionality locally
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
//		String restuledJson = readContentsFromS3AndReturnAsString("ecg1", "msgId_121/json_only_for_ecg.txt");
//		System.out.println(restuledJson);
//
//		Object obj = readContentsFromS3AndReturnAsJson("ecg1", "msgId_121/json_only_for_ecg.txt");
//		System.out.println(obj);
//
//		readContentsFromS3AndWriteIntoLocalFile("ecg1", "msgId_121/json_only_for_ecg.txt", "ecg.json");
		System.setProperty("REGION", "us-east-1");
		getFileContentsAsInputStreamFromS3("adi-chf-patient-phi-upload-data", "PatientPhi.csv");
	}
}