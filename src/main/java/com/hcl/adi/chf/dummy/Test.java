package com.hcl.adi.chf.dummy;

import java.util.List;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.AdminDeleteUserRequest;
import com.amazonaws.services.cognitoidp.model.ListUsersRequest;
import com.amazonaws.services.cognitoidp.model.ListUsersResult;
import com.amazonaws.services.cognitoidp.model.UserType;

public class Test {
	public static void main(String[] args) {
		AWSCognitoIdentityProvider cognitoClient = AWSCognitoIdentityProviderClientBuilder.standard()
				.withRegion(Regions.US_EAST_1).build();

		ListUsersRequest req = new ListUsersRequest();
		req = req.withUserPoolId("us-east-1_tswTIQ0gH");

		ListUsersResult response = cognitoClient.listUsers(req);
		List<UserType> list = response.getUsers();

		System.out.println(list.size());

		for (UserType userType : list) {
			cognitoClient.adminDeleteUser(new AdminDeleteUserRequest().withUserPoolId("us-east-1_tswTIQ0gH")
					.withUsername(userType.getUsername()));
		}

		response = cognitoClient.listUsers(req);
		list = response.getUsers();

		System.out.println(list.size());

		for (UserType userType : list) {
			cognitoClient.adminDeleteUser(new AdminDeleteUserRequest().withUserPoolId("us-east-1_tswTIQ0gH")
					.withUsername(userType.getUsername()));
		}

		response = cognitoClient.listUsers(req);
		list = response.getUsers();

		System.out.println(list.size());

		for (UserType userType : list) {
			cognitoClient.adminDeleteUser(new AdminDeleteUserRequest().withUserPoolId("us-east-1_tswTIQ0gH")
					.withUsername(userType.getUsername()));
		}

		response = cognitoClient.listUsers(req);
		list = response.getUsers();

		System.out.println(list.size());

		for (UserType userType : list) {
			cognitoClient.adminDeleteUser(new AdminDeleteUserRequest().withUserPoolId("us-east-1_tswTIQ0gH")
					.withUsername(userType.getUsername()));
		}

		response = cognitoClient.listUsers(req);
		list = response.getUsers();

		System.out.println(list.size());

		for (UserType userType : list) {
			cognitoClient.adminDeleteUser(new AdminDeleteUserRequest().withUserPoolId("us-east-1_tswTIQ0gH")
					.withUsername(userType.getUsername()));
		}

		response = cognitoClient.listUsers(req);
		list = response.getUsers();

		System.out.println(list.size());

		for (UserType userType : list) {
			cognitoClient.adminDeleteUser(new AdminDeleteUserRequest().withUserPoolId("us-east-1_tswTIQ0gH")
					.withUsername(userType.getUsername()));
		}

		response = cognitoClient.listUsers(req);
		list = response.getUsers();

		System.out.println(list.size());

		for (UserType userType : list) {
			cognitoClient.adminDeleteUser(new AdminDeleteUserRequest().withUserPoolId("us-east-1_tswTIQ0gH")
					.withUsername(userType.getUsername()));
		}

		response = cognitoClient.listUsers(req);
		list = response.getUsers();

		System.out.println(list.size());

		for (UserType userType : list) {
			cognitoClient.adminDeleteUser(new AdminDeleteUserRequest().withUserPoolId("us-east-1_tswTIQ0gH")
					.withUsername(userType.getUsername()));
		}

		response = cognitoClient.listUsers(req);
		list = response.getUsers();

		System.out.println(list.size());

		for (UserType userType : list) {
			cognitoClient.adminDeleteUser(new AdminDeleteUserRequest().withUserPoolId("us-east-1_tswTIQ0gH")
					.withUsername(userType.getUsername()));
		}

		response = cognitoClient.listUsers(req);
		list = response.getUsers();

		System.out.println(list.size());

		for (UserType userType : list) {
			cognitoClient.adminDeleteUser(new AdminDeleteUserRequest().withUserPoolId("us-east-1_tswTIQ0gH")
					.withUsername(userType.getUsername()));
		}
	}
}