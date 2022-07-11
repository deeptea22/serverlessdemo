package com.hcl.adi.chf.authorizer.util;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;

import javax.ws.rs.core.Response.Status;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.BadJWTException;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;

/**
 * This class will validate the cognito user pool generated JWT token
 *
 * @author AyushRa
 */
public final class AwsCognitoJwtValidatorUtil {
	private AwsCognitoJwtValidatorUtil() {
		// private constructor
	}

	/**
	 * This method will validate the Aws Jwt Token using Nimbus Jose Jwt Library
	 *
	 * @param token
	 * @return JWTClaimsSet
	 * @throws ParseException
	 * @throws JOSEException
	 * @throws BadJOSEException
	 * @throws CustomException
	 * @throws IOException
	 */
	public static JWTClaimsSet validateAWSJwtToken(final String token) throws ParseException, JOSEException, BadJOSEException, CustomException, IOException {
		String jsonWebKeyFileURL = AwsCognitoJwtParserUtil.getJsonWebKeyURL(token);

		ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<SecurityContext>();
		JWKSource<SecurityContext> jwkSource = null;
		jwkSource = new RemoteJWKSet<SecurityContext>(new URL(jsonWebKeyFileURL));
		JWSAlgorithm jwsAlgorithm = JWSAlgorithm.RS256;
		JWSKeySelector<SecurityContext> keySelector = new JWSVerificationKeySelector<SecurityContext>(jwsAlgorithm, jwkSource);
		jwtProcessor.setJWSKeySelector(keySelector);

		try {
			JWTClaimsSet claimsSet = jwtProcessor.process(token, null);
			return claimsSet;

		} catch (BadJWTException e) {
			throw new CustomException(Status.UNAUTHORIZED, AuthenticationError.TOKEN_EXPIRED, e.getLocalizedMessage());
		}
	}
}