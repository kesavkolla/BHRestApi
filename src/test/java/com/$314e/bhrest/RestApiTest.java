package com.$314e.bhrest;

import java.util.Properties;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.$314e.bhrestapi.BHRestApi;
import com.$314e.bhrestapi.BHRestUtil;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Unit test for simple App.
 */
public class RestApiTest {
	private static final String CLIENT_ID = "71eb9c1a-27e1-4bb6-8c60-8f835cc51651";
	private static final String CLIENT_SECRET = "lU5yFm9ypiLPctFGzidBaXYV7c53Drie";

	private static Properties properties = new Properties();
	private static ObjectNode token;
	private static String restToken;
	private static BHRestApi.Entity entityApi;

	@BeforeClass
	public static void setupToken() throws Exception {
		properties.load(RestApiTest.class.getResourceAsStream("/local.properties"));
		token = BHRestUtil.getRestToken(properties.getProperty("CLIENT_ID", CLIENT_ID),
				properties.getProperty("CLIENT_SECRET", CLIENT_SECRET), properties.getProperty("BH_USER"),
				properties.getProperty("BH_PASSWORD"));
		restToken = token.get("BhRestToken").asText();
		entityApi = BHRestUtil.getEntityApi(token);

	}

	@Test
	public void testLogin() throws Exception {
		Assert.assertNotNull("Token can not be null", token);
		Assert.assertNotNull("Rest Token can not be null", token.get("BhRestToken"));
		Assert.assertNotNull("restUrl can not be null", token.get("restUrl"));
	}

	@Test
	public void candidateSearch() throws Exception {
		System.out
				.println(entityApi
						.search(BHRestApi.Entity.ENTITY_TYPE.CANDIDATE,
								restToken,
								"isDeleted:0 AND NOT status:Archive",
								"id,name,occupation,phone,address,customText19,companyName,status,dateAdded,owner(id,firstName,lastName),dateAvailableEnd,email,customDate3,customText4,source",
								"-dateAdded", 10, 0));
	}

	@Test
	public void getCandidate() throws Exception {
		final ObjectNode candidate = entityApi.get(BHRestApi.Entity.ENTITY_TYPE.CANDIDATE, restToken, "70858", "*");
		Assert.assertNotNull("Candidate can't be empty", candidate);
		Assert.assertNotNull("Candidate can't be null", candidate.get("data"));
		System.out.println(candidate.path("data").path("address"));
	}
}
