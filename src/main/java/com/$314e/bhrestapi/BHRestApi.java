package com.$314e.bhrestapi;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.Encoded;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * 
 * @author Kesav Kumar Kolla (kesav@314ecorp.com)
 *
 */
public interface BHRestApi {

	public static final String AUTH_URL = "https://auth.bullhornstaffing.com";
	public static final String REST_URL = "https://rest.bullhornstaffing.com";

	/**
	 * 
	 * @author Kesav Kumar Kolla (kesav@314ecorp.com)
	 *
	 */
	@Path("oauth")
	public interface Auth {
		/**
		 * 
		 * @param responseType
		 * @param action
		 * @param clientId
		 * @param user
		 * @param password
		 * @return
		 */
		@GET
		@Path("authorize")
		public Response authorize(final @QueryParam("response_type") @DefaultValue("code") String responseType,
				final @QueryParam("action") @DefaultValue("Login") String action,
				final @QueryParam("client_id") String clientId, final @QueryParam("username") String user,
				final @QueryParam("password") String password);

		/**
		 * 
		 * @param grantType
		 * @param code
		 * @param clientId
		 * @param clientSecret
		 * @return
		 */
		@POST
		@Path("token")
		@Produces("application/json")
		public ObjectNode token(final @QueryParam("grant_type") String grantType,
				final @Encoded @QueryParam("code") String code, final @QueryParam("client_id") String clientId,
				final @QueryParam("client_secret") String clientSecret);

	}

	/**
	 * 
	 * @author Kesav Kumar Kolla (kesav@314ecorp.com)
	 *
	 */
	@Path("rest-services")
	public interface Token {
		/**
		 * Returns a REST API session token.
		 * 
		 * @param version
		 *            Version of the API to use (* is a wildcard for latest
		 *            version).
		 * @param accessToken
		 *            Access token obtained from OAuth authorization.
		 * @return REST API session token
		 */
		@GET
		@Path("login")
		public ObjectNode login(final @QueryParam("version") String version,
				final @QueryParam("access_token") String accessToken);

		/**
		 * 
		 * @param token
		 * @return
		 */
		@GET
		@Path("logout")
		public ObjectNode logout(final @QueryParam("BhRestToken") String token);

	}

	/**
	 * 
	 * @author Kesav Kumar Kolla (kesav@314ecorp.com)
	 *
	 */
	public interface Candidate {
		/**
		 * Searches for candidates
		 * 
		 * @param token
		 *            REST API token
		 * @param query
		 *            candidate query
		 * @param fields
		 *            fields to return in query
		 * @param count
		 *            no.of items to retrieve
		 * @param start
		 *            start of the results
		 * @return
		 */
		@GET
		@Path("search/Candidate")
		public ObjectNode search(final @QueryParam("BhRestToken") String token,
				final @QueryParam("query") String query, final @QueryParam("fields") String fields,
				final @QueryParam("count") int count, final @QueryParam("start") long start);
	}
}
