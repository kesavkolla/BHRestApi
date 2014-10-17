package com.$314e.bhrestapi;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.Encoded;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public interface Entity {
		public enum ENTITY_TYPE {
			// @formatter:off
			CANDIDATE("Candidate"), 
			CLIENT_CONTACT("ClientContact"), 
			NOTE_ENTITY("NoteEntity"), 
			NOTE("Note"), 
			APPOINTMENT("Appointment"), 
			PLACEMENT("Placement"), 
			JOB_ORDER("JobOrder"), 
			JOB_SUBMISSION("JobSubmission"), 
			PERSON("Person"),
			TEARSHEAT("Tearsheet");
			// @formatter:off

			private final String value;

			private ENTITY_TYPE(String value) {
				this.value = value;
			}

			public String value() {
				return value;
			}

			@Override
			public String toString() {
				return value;
			}
		}

		/**
		 * Searches for entities in Lucene style
		 * 
		 * @param entityType
		 * @param token
		 * @param query
		 *            Lucene query string
		 * @param fields
		 *            Comma-separated list of field names
		 * @param sort
		 *            Field to sort result on. Default sort order is ascending.
		 *            Precede with minus sign to perform descending sort.
		 * @param count
		 *            Limit on the number of entities to return. If the set of
		 *            matched results is larger than the count value, the
		 *            returned results is capped at the count value. The default
		 *            count is 20. The maximum count is 500; if you specify a
		 *            count greater than 500, a message at the end of the
		 *            response indicates you have specified too many items. The
		 *            response also includes the actual number of items returned
		 *            and the start value of the request. This is useful when
		 *            you want to make calls to page additional sets of data.
		 * 
		 * @param start
		 *            From the set of matched results, returns item numbers
		 *            start through (start + count)
		 * @return
		 */
		@GET
		@Path("search/{entityType}")
		public ObjectNode search(final @PathParam("entityType") ENTITY_TYPE entityType,
				final @QueryParam("BhRestToken") String token, final @QueryParam("query") String query,
				final @QueryParam("fields") String fields, final @QueryParam("sort") String sort,
				final @QueryParam("count") int count, final @QueryParam("start") long start);

		/**
		 * queries for entities in SQL style
		 * 
		 * @param entityType
		 * @param token
		 * @param query
		 *            SQL query string
		 * @param fields
		 *            Comma-separated list of field names
		 * @param orderBy
		 *            Comma-separated list of field names on which to base the
		 *            order of returned entities. Precede field name with a
		 *            minus sign (-) or plus sign (+) to sort results in
		 *            descending or ascending order based on that field; default
		 *            is ascending order.
		 * @param count
		 *            Limit on the number of entities to return. If the set of
		 *            matched results is larger than the count value, the
		 *            returned results is capped at the count value. The default
		 *            count is 20. The maximum count is 500; if you specify a
		 *            count greater than 500, a message at the end of the
		 *            response indicates you have specified too many items. The
		 *            response also includes the actual number of items returned
		 *            and the start value of the request. This is useful when
		 *            you want to make calls to page additional sets of data.
		 * 
		 * @param start
		 *            From the set of matched results, returns item numbers
		 *            start through (start + count)
		 * @return
		 */
		@GET
		@Path("query/{entityType}")
		public ObjectNode query(final @PathParam("entityType") ENTITY_TYPE entityType,
				final @QueryParam("BhRestToken") String token, final @QueryParam("where") String where,
				final @QueryParam("fields") String fields, final @QueryParam("orderBy") String orderBy,
				final @QueryParam("count") int count, final @QueryParam("start") long start);

		/**
		 * Retrieves a given entity
		 * 
		 * @param entityType
		 *            Entity Type
		 * @param token
		 *            REST token
		 * @param entityId
		 *            entity id to be retrieved. Multiple Ids can be provided
		 *            with a "," separated values
		 * @param fields
		 *            Fields to retrieve. Use * to retrieve all the fields
		 * @return
		 */
		@GET
		@Path("entity/{entityType}/{entityId}")
		public ObjectNode get(final @PathParam("entityType") ENTITY_TYPE entityType,
				final @QueryParam("BhRestToken") String token, final @PathParam("entityId") Object entityId,
				final @QueryParam("fields") String... fields);

		/**
		 * Update given entity
		 * 
		 * @param entityType
		 *            Entity tpye
		 * @param token
		 *            REST token
		 * @param entityId
		 *            entity id to for update
		 * @param entity
		 *            data to be updated for the entity
		 * @return
		 */
		@POST
		@Path("entity/{entityType}/{entityId}")
		public ObjectNode update(final @PathParam("entityType") ENTITY_TYPE entityType,
				final @QueryParam("BhRestToken") String token, final @PathParam("entityId") Object entityId,
				final ObjectNode entity);
		
		/**
		 * Add a new entity
		 * 
		 * @param entityType Entity tpye
		 * @param token REST token
		 * * @param entity entity data
		 * @return
		 */
		@PUT
		@Path("entity/{entityType}")
		public ObjectNode put(final @PathParam("entityType") ENTITY_TYPE entityType,
				final @QueryParam("BhRestToken") String token, final ObjectNode entity);

		/**
		 * Perform massupdate of entities
		 * 
		 * @param entityType
		 *            entity type
		 * @param token
		 *            REST token
		 * @param data
		 *            massupdate data
		 * @return
		 */
		@POST
		@Path("massUpdate/{entityType}")
		public ObjectNode massUpdate(final @PathParam("entityType") ENTITY_TYPE entityType,
				final @QueryParam("BhRestToken") String token, final ObjectNode data);
	}
}
