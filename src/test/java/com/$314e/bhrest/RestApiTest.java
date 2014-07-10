package com.$314e.bhrest;

import org.junit.Assert;
import org.junit.Before;
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
	private static final String BH_USER = "abhi";
	private static final String BH_PASSWORD = "Kaiser123";

	private ObjectNode token;
	private String restToken;
	private BHRestApi.Entity entityApi;

	@Before
	public void setupToken() throws Exception {
		token = BHRestUtil.getRestToken(CLIENT_ID, CLIENT_SECRET, BH_USER, BH_PASSWORD);
		this.restToken = token.get("BhRestToken").asText();
		this.entityApi = BHRestUtil.getEntityApi(this.token);
	}

	@Test
	public void testLogin() throws Exception {
		Assert.assertNotNull("Token can not be null", this.token);
		Assert.assertNotNull("Rest Token can not be null", this.token.get("BhRestToken"));
		Assert.assertNotNull("restUrl can not be null", this.token.get("restUrl"));
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
		final ObjectNode candidate = entityApi
				.get(BHRestApi.Entity.ENTITY_TYPE.CANDIDATE, this.restToken, "70858", "*");
		System.out.println(candidate.toString());
	}
}
