package com.$314e.bhrest;

import org.junit.Assert;
import org.junit.Test;

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

	@Test
	public void testLogin() throws Exception {
		final ObjectNode token = BHRestUtil.getRestToken(CLIENT_ID, CLIENT_SECRET, BH_USER, BH_PASSWORD);
		System.out.println(token);
		Assert.assertNotNull("Token can not be null", token);
		Assert.assertNotNull("Rest Token can not be null", token.get("BhRestToken"));
		Assert.assertNotNull("", token.get("restUrl"));
	}
}
