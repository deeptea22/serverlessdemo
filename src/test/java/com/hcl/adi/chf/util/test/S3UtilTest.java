package com.hcl.adi.chf.util.test;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.sqs.model.Message;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.S3Util;
import com.hcl.adi.chf.util.SQSUtil;

public class S3UtilTest {
	private static final Logger LOGGER = LogManager.getLogger(S3UtilTest.class.getName());
	private static String bucketName;
	private static String key;
	private static String fileName;

	@BeforeClass
	public static void createInput() throws IOException {
		bucketName = "testQue";
		key = "key1";
		fileName ="abc";
	}

	@Test
	public void readContentsFromS3AndReturnAsString() {
		try {
			S3Util.readContentsFromS3AndReturnAsString(bucketName, key);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void readContentsFromS3AndReturnAsJson() {
		try {
			S3Util.readContentsFromS3AndReturnAsJson(bucketName, key);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void readContentsFromS3AndWriteIntoLocalFile() {
		try {
			S3Util.readContentsFromS3AndWriteIntoLocalFile(bucketName, key, fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void readContentsFromS3CSVAndWriteIntoLocalFile() {
		try {
			S3Util.readContentsFromS3CSVAndWriteIntoLocalFile(bucketName, key);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}