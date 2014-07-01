package com.$314e.bhrestapi;

import java.net.URLDecoder;

import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Helper methods
 * 
 * @author Kesav Kumar Kolla (kesav@314ecorp.com)
 *
 */
public class BHRestUtil {

	/**
	 * 
	 * @return
	 */
	public static BHRestApi.Auth getAuthApi() {
		return new ResteasyClientBuilder().build().target(BHRestApi.AUTH_URL).proxy(BHRestApi.Auth.class);
	}

	/**
	 * 
	 * @return
	 */
	public static BHRestApi.Token getTokenApi() {
		return new ResteasyClientBuilder().build().target(BHRestApi.REST_URL).proxy(BHRestApi.Token.class);
	}

	/**
	 * 
	 * @param token
	 * @return
	 */
	public static BHRestApi.Entity getEntityApi(final ObjectNode token) {
		return new ResteasyClientBuilder().build().target(token.get("restUrl").asText()).proxy(BHRestApi.Entity.class);
	}

	/**
	 * Performs REST API login and gets the authorization code
	 * 
	 * @param clientId
	 *            REST API Client Id
	 * @param user
	 *            Bullhorn user
	 * @param password
	 *            Bullhorn password
	 * @return authorization code
	 */
	public static ObjectNode getRestToken(final String clientId, final String clientSecret, final String user,
			final String password) throws Exception {
		final BHRestApi.Auth bhAuth = new ResteasyClientBuilder().build().target(BHRestApi.AUTH_URL)
				.proxy(BHRestApi.Auth.class);
		final Response resp = bhAuth.authorize("code", "Login", clientId, user, password);
		resp.close();

		final String location = (String) resp.getHeaders().getFirst("Location");
		if (!location.contains("code=")) {
			throw new RuntimeException("Error in login: " + location);
		}

		final String authCode = URLDecoder.decode(location.split("=")[1], "UTF-8");
		final ObjectNode bhAuthToken = bhAuth.token("authorization_code", authCode, clientId, clientSecret);
		final BHRestApi.Token tokenApi = getTokenApi();
		return tokenApi.login("*", bhAuthToken.get("access_token").asText());
	}
}
