package com.emailage.javawrapper.example;

import com.emailage.javawrapper.EmailageClient;
import com.emailage.javawrapper.model.ConfigurationParameters;
import com.emailage.javawrapper.model.Enums;
import com.emailage.javawrapper.model.ExtraInputParameter;
import com.emailage.javawrapper.model.response.EmailageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;

public class TestClient {

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {

		/* Results can be in JSON or XML format */
		Enums.Format resultFormat = Enums.Format.Json;
		Enums.SignatureMethod signatureMethod = Enums.SignatureMethod.HMAC_SHA256;

		/*
		 * OPTIONAL FIELD. Use this field if you want to associate the API call
		 * to a specific Emailage user.
		 */
		String user_email = null;
		Enums.Environment environment = Enums.Environment.Production;

		String accountSecret = "replace-me";
		String authToken = "replace-me";

		ConfigurationParameters parameters = new ConfigurationParameters();
		parameters.setUserEmail(user_email);
		parameters.setAcccountToken(authToken);
		parameters.setAccountSecret(accountSecret);
		parameters.setEnvironment(environment);
		parameters.setHashAlgorithm(signatureMethod);
		parameters.setResultFormat(resultFormat);

		// Configure jackson-afterburner
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new AfterburnerModule());

		// Email validation
		try {
			System.out.println("Querying Email");
			EmailageResponse validResult = EmailageClient.QueryEmail("test@test.com", parameters);
			String result = mapper.writeValueAsString(validResult);
			System.out.println(result);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Email + IP validation
		try {
			System.out.println("Querying Email + IP");
			EmailageResponse validResult = EmailageClient.QueryEmailAndIP("test@test.com", "147.12.12.13", parameters);
			String result = mapper.writeValueAsString(validResult);
			System.out.println(result);

		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * Querying Email + IP and passing additional arguments, in this case,
		 * billpostal code
		 */
		try {
			System.out.println("Querying Email + IP + Extra Arguments");

			ExtraInputParameter extraArgs = new ExtraInputParameter();
			extraArgs.setbilladdress("123 Any St.");
			extraArgs.setbillpostal("85225");
			extraArgs.setbillcity("Chandler");
			extraArgs.setbillregion("AZ");
			extraArgs.setbillcountry("us");
			extraArgs.setphone("4805551212");
			extraArgs.settransamount(1234.56);
			extraArgs.settranscurrency("USD");
			extraArgs.setexistingcustomer(false);
			extraArgs.setfirstname("Bob");
			extraArgs.setlastname("Smith");

			EmailageResponse validResult = EmailageClient.QueryEmailAndIPPlusExtraArgs("test@test.com", "147.12.12.13", extraArgs, parameters);
			String result = mapper.writeValueAsString(validResult);
			System.out.println(result);

		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * This method is used to report cases where a "confirmed fraud" or
		 * "confirmed good" email is found at the customer site.
		 */
		// Email + IP validation
		try {
			System.out.println("Marking an Email as Fraud");
			EmailageResponse validResult = EmailageClient.MarkEmailAsFraud("test@test.com", Enums.FraudType.Fraud, Enums.FraudCode.CARD_NOT_PRESENT, parameters);
			String result = mapper.writeValueAsString(validResult);
			System.out.println(result);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
