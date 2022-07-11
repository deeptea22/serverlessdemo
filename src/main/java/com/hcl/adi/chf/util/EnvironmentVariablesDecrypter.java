package com.hcl.adi.chf.util;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.kms.model.DecryptRequest;
import com.amazonaws.util.Base64;

/**
 * This utility class will be used to decrypt the lambda environment variables
 *
 * @author AyushRa
 */
public final class EnvironmentVariablesDecrypter {
	private EnvironmentVariablesDecrypter() {
		// private constructor
	}

	public static String decryptKey(String environmentVariableName) {
		byte[] encryptedKey = Base64.decode(System.getenv(environmentVariableName));

		AWSKMS client = AWSKMSClientBuilder.standard().withRegion(Regions.fromName(System.getenv("AWS_REGION")))
				.build();

		DecryptRequest request = new DecryptRequest().withCiphertextBlob(ByteBuffer.wrap(encryptedKey));

		ByteBuffer plainTextKey = client.decrypt(request).getPlaintext();

		return new String(plainTextKey.array(), Charset.forName("UTF-8"));
	}
}