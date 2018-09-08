package com.embl.technicaltest.integrationtestcase.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.HashMap;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.embl.technicaltest.Application;
import com.embl.technicaltest.datatransferobject.PersonDTO;
import com.embl.technicaltest.datatransferobject.PersonDTOResponseWrapperCollection;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Integration Test
 * 
 * @author Ashutosh Shimpi
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonIntegrationTest {

	@LocalServerPort
	private int port;

	private TestRestTemplate restTemplate = new TestRestTemplate("trusted-app", "secret");
	private ObjectMapper mapper = new ObjectMapper();

	/**
	 * Positive test case to test Create Person API
	 */
	@Test
	public void testcase1_createPersonTest() {
		HttpHeaders headers = newHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);

		String requestJson = "{  \"person\": [{\"age\": \"22\", \"favourite_colour\": \"Black\", \"first_name\": \"John\", \"id\": 0, \"last_name\": \"Cena\"},{ \"age\": \"22\", \"favourite_colour\": \"Black\", \"first_name\": \"Rock\", \"id\": 0, \"last_name\": \"star\"},{ \"age\": \"22\",\"favourite_colour\": \"Black\",\"first_name\": \"Goldburg\",\"id\": 0,\"last_name\": \"star\" }]}";

		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);

		ResponseEntity<PersonDTOResponseWrapperCollection> response = restTemplate.exchange(
				createURLWithPort("/v1/persons"), HttpMethod.POST, entity, PersonDTOResponseWrapperCollection.class);

		PersonDTOResponseWrapperCollection personDTOResponseWrapperCollection = response.getBody();

		assertions(personDTOResponseWrapperCollection);
	}

	/**
	 * Positive test case to test getPerson API
	 */
	@Test
	public void testcase2_getPersonTest() {
		HttpHeaders headers = newHeaders();

		HttpEntity<Void> entity = new HttpEntity<Void>(headers);

		ResponseEntity<PersonDTOResponseWrapperCollection> response = restTemplate.exchange(
				createURLWithPort("/v1/persons?personId=1&personId=2&personId=3"), HttpMethod.GET, entity,
				PersonDTOResponseWrapperCollection.class);

		PersonDTOResponseWrapperCollection personDTOResponseWrapperCollection = response.getBody();

		assertions(personDTOResponseWrapperCollection);
	}

	/**
	 * Positive test case to test updatePerson API
	 */
	@Test
	public void testcase3_updatePersonTest() {

		HttpHeaders headers = newHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);

		String requestJson = "{  \"person\": [{\"age\": \"22\", \"favourite_colour\": \"Black\", \"first_name\": \"John\", \"id\": 0, \"last_name\": \"Cena\"},{ \"age\": \"22\", \"favourite_colour\": \"Black\", \"first_name\": \"Rock\", \"id\": 0, \"last_name\": \"star\"},{ \"age\": \"22\",\"favourite_colour\": \"Black\",\"first_name\": \"Goldburg\",\"id\": 0,\"last_name\": \"star\" }]}";

		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);

		restTemplate.exchange(createURLWithPort("/v1/persons"), HttpMethod.POST, entity,
				PersonDTOResponseWrapperCollection.class);

		headers.setContentType(MediaType.APPLICATION_JSON);

		requestJson = "{  \"person\": [{\"age\": \"22\", \"favourite_colour\": \"Green\", \"first_name\": \"John\", \"id\": 4, \"last_name\": \"Cena\"},{ \"age\": \"28\", \"favourite_colour\": \"Black\", \"first_name\": \"Rock\", \"id\": 5, \"last_name\": \"star\"},{ \"age\": \"22\",\"favourite_colour\": \"Black\",\"first_name\": \"Goldburg\",\"id\": 6,\"last_name\": \"wwe\" }]}";

		entity = new HttpEntity<String>(requestJson, headers);

		restTemplate.exchange(createURLWithPort("/v1/persons"), HttpMethod.PUT, entity,
				PersonDTOResponseWrapperCollection.class);

		ResponseEntity<PersonDTOResponseWrapperCollection> response = restTemplate.exchange(
				createURLWithPort("/v1/persons?personId=4&personId=5&personId=6"), HttpMethod.GET, entity,
				PersonDTOResponseWrapperCollection.class);

		assertionsUpdate(response.getBody());
	}

	/**
	 * Positive test case to test deletePerson API
	 */
	@Test
	public void testcase4_deletePersonTest() {
		HttpHeaders headers = newHeaders();

		HttpEntity<Void> entity = new HttpEntity<Void>(headers);

		ResponseEntity<PersonDTOResponseWrapperCollection> response = restTemplate.exchange(
				createURLWithPort("/v1/persons?personId=1&personId=2&personId=3"), HttpMethod.DELETE, entity,
				PersonDTOResponseWrapperCollection.class);

		PersonDTOResponseWrapperCollection personDTOResponseWrapperCollection = response.getBody();

		assertions(personDTOResponseWrapperCollection);
	}

	/* Test cases for operations on single entity */

	/**
	 * Positive test case to test single entity CRUD operations like
	 * getPerson,updatePerson & deletePerson API
	 */
	@Test
	public void testcase5_createGetUpdateDeletePersonSingleTest() {

		HttpHeaders headers = newHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);

		String requestJson = "{  \"person\": [{\"age\": \"22\", \"favourite_colour\": \"Black\", \"first_name\": \"John\", \"id\": 0, \"last_name\": \"Cena\"}]}";

		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);

		ResponseEntity<PersonDTOResponseWrapperCollection> response = restTemplate.exchange(
				createURLWithPort("/v1/persons"), HttpMethod.POST, entity, PersonDTOResponseWrapperCollection.class);

		PersonDTOResponseWrapperCollection personDTOResponseWrapperCollection = response.getBody();

		PersonDTO personDTO = personDTOResponseWrapperCollection.getPersonsData().get(0).getPerson();

		headers.setContentType(MediaType.APPLICATION_JSON);

		requestJson = "{\"id\": 5, \"age\": \"22\",\"first_name\": \"Dev\",\"last_name\": \"Cena\",\"favourite_colour\": \"RED\"}";

		entity = new HttpEntity<String>(requestJson, headers);

		restTemplate.exchange(createURLWithPort("/v1/persons/" + personDTO.getId()), HttpMethod.PUT, entity,
				PersonDTO.class);

		HttpEntity<Void> entityVoid = new HttpEntity<Void>(headers);

		ResponseEntity<PersonDTO> responseDTO = restTemplate.exchange(
				createURLWithPort("/v1/persons/" + personDTO.getId()), HttpMethod.GET, entityVoid, PersonDTO.class);

		personDTO = responseDTO.getBody();

		assertEquals(personDTO.getFirstName(), "Dev");

		restTemplate.exchange(createURLWithPort("/v1/persons/" + personDTO.getId()), HttpMethod.DELETE, entity,
				PersonDTOResponseWrapperCollection.class);
	}

	/**
	 * Negative test case to test EntityNotFoundException for getPerson
	 */
	@Test
	public void testcase6_getPerson_EntityNotFoundExceptionTest() {
		HttpHeaders headers = newHeaders();

		HttpEntity<Void> entity = new HttpEntity<Void>(headers);

		ResponseEntity<PersonDTOResponseWrapperCollection> response = restTemplate.exchange(
				createURLWithPort("/v1/persons?personId=10"), HttpMethod.GET, entity,
				PersonDTOResponseWrapperCollection.class);

		PersonDTOResponseWrapperCollection personDTOResponseWrapperCollection = response.getBody();
		badRequestAssertions(personDTOResponseWrapperCollection);
	}

	/**
	 * Negative test case to test EntityNotFoundException for updatePerson
	 */
	@Test
	public void testcase7_update_EntityNotFoundExceptionTest() {

		HttpHeaders headers = newHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);

		String requestJson = "{  \"person\": [{\"age\": \"22\", \"favourite_colour\": \"Green\", \"first_name\": \"John\", \"id\": 60, \"last_name\": \"Cena\"}]}";

		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);

		ResponseEntity<PersonDTOResponseWrapperCollection> response = restTemplate.exchange(
				createURLWithPort("/v1/persons"), HttpMethod.PUT, entity, PersonDTOResponseWrapperCollection.class);

		PersonDTOResponseWrapperCollection personDTOResponseWrapperCollection = response.getBody();
		badRequestAssertions(personDTOResponseWrapperCollection);
	}

	/**
	 * Negative test case to test EntityNotFoundException for deletePerson
	 */
	@Test
	public void testcase8_deletePerson_EntityNotFoundExceptionTest() {
		HttpHeaders headers = newHeaders();

		HttpEntity<Void> entity = new HttpEntity<Void>(headers);

		ResponseEntity<PersonDTOResponseWrapperCollection> response = restTemplate.exchange(
				createURLWithPort("/v1/persons?personId=60"), HttpMethod.DELETE, entity,
				PersonDTOResponseWrapperCollection.class);

		PersonDTOResponseWrapperCollection personDTOResponseWrapperCollection = response.getBody();
		badRequestAssertions(personDTOResponseWrapperCollection);
	}

	/**
	 * Negative test case to test EntityNotFoundException for getPerson with single entity
	 */
	@Test
	public void testcase9_getPersonSingle_EntityNotFoundExceptionTest() {
		HttpHeaders headers = newHeaders();

		HttpEntity<Void> entity = new HttpEntity<Void>(headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/v1/persons/100"), HttpMethod.GET,
				entity, String.class);

		assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
	}

	/**
	 * Negative test case to test EntityNotFoundException for updatePerson with single entity
	 */
	@Test
	public void testcase10_updatePersonSingle_EntityNotFoundExceptionTest() {

		HttpHeaders headers = newHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);

		String requestJson = "{\"id\": 5, \"age\": \"22\",\"first_name\": \"Dev\",\"last_name\": \"Cena\",\"favourite_colour\": \"RED\"}";

		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/v1/persons/67"), HttpMethod.PUT,
				entity, String.class);

		assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
	}

	/**
	 * Negative test case to test EntityNotFoundException for deletePerson with single entity
	 */
	@Test
	public void testcase11_deletePersonSingle_EntityNotFoundExceptionTest() {
		HttpHeaders headers = newHeaders();

		HttpEntity<Void> entity = new HttpEntity<Void>(headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/v1/persons/56"), HttpMethod.DELETE,
				entity, String.class, 1);

		assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
	}

	private void assertions(PersonDTOResponseWrapperCollection personDTOResponseWrapperCollection) {

		assertNotNull(personDTOResponseWrapperCollection);
		assertFalse(personDTOResponseWrapperCollection.getPersonsData().isEmpty());
		assertTrue(personDTOResponseWrapperCollection.getPersonsData().size() == 3);

		PersonDTO personDTO1 = personDTOResponseWrapperCollection.getPersonsData().get(0).getPerson();
		PersonDTO personDTO2 = personDTOResponseWrapperCollection.getPersonsData().get(1).getPerson();
		PersonDTO personDTO3 = personDTOResponseWrapperCollection.getPersonsData().get(2).getPerson();

		assertTrue(personDTO1.getId().equals(Long.valueOf(1)));
		assertTrue(personDTO1.getFirstName().equals("John"));
		assertTrue(personDTO1.getLastName().equals("Cena"));
		assertTrue(personDTO1.getFavouriteColour().equals("Black"));
		assertTrue(personDTO1.getAge().equals("22"));

		assertTrue(personDTO2.getId().equals(Long.valueOf(2)));
		assertTrue(personDTO2.getFirstName().equals("Rock"));
		assertTrue(personDTO2.getLastName().equals("star"));
		assertTrue(personDTO2.getFavouriteColour().equals("Black"));
		assertTrue(personDTO2.getAge().equals("22"));

		assertTrue(personDTO3.getId().equals(Long.valueOf(3)));
		assertTrue(personDTO3.getFirstName().equals("Goldburg"));
		assertTrue(personDTO3.getLastName().equals("star"));
		assertTrue(personDTO3.getFavouriteColour().equals("Black"));
		assertTrue(personDTO3.getAge().equals("22"));

		// Test coverage for getter setter

		personDTOResponseWrapperCollection.getPersonsData().get(0).setHttpHeaders(null);
		personDTOResponseWrapperCollection.getPersonsData().get(0).setErrorMessage(null);
	}

	private void assertionsUpdate(PersonDTOResponseWrapperCollection personDTOResponseWrapperCollection) {

		assertNotNull(personDTOResponseWrapperCollection);
		assertFalse(personDTOResponseWrapperCollection.getPersonsData().isEmpty());
		assertTrue(personDTOResponseWrapperCollection.getPersonsData().size() == 3);

		PersonDTO personDTO1 = personDTOResponseWrapperCollection.getPersonsData().get(0).getPerson();
		PersonDTO personDTO2 = personDTOResponseWrapperCollection.getPersonsData().get(1).getPerson();
		PersonDTO personDTO3 = personDTOResponseWrapperCollection.getPersonsData().get(2).getPerson();

		assertTrue(personDTO1.getId().equals(Long.valueOf(4)));
		assertTrue(personDTO1.getFirstName().equals("John"));
		assertTrue(personDTO1.getLastName().equals("Cena"));
		assertTrue(personDTO1.getFavouriteColour().equals("Green"));
		assertTrue(personDTO1.getAge().equals("22"));

		assertTrue(personDTO2.getId().equals(Long.valueOf(5)));
		assertTrue(personDTO2.getFirstName().equals("Rock"));
		assertTrue(personDTO2.getLastName().equals("star"));
		assertTrue(personDTO2.getFavouriteColour().equals("Black"));
		assertTrue(personDTO2.getAge().equals("28"));

		assertTrue(personDTO3.getId().equals(Long.valueOf(6)));
		assertTrue(personDTO3.getFirstName().equals("Goldburg"));
		assertTrue(personDTO3.getLastName().equals("wwe"));
		assertTrue(personDTO3.getFavouriteColour().equals("Black"));
		assertTrue(personDTO3.getAge().equals("22"));

		// Test coverage for getter setter

		personDTOResponseWrapperCollection.getPersonsData().get(0).setHttpHeaders(null);
		personDTOResponseWrapperCollection.getPersonsData().get(0).setErrorMessage(null);
	}

	private void badRequestAssertions(PersonDTOResponseWrapperCollection personDTOResponseWrapperCollection) {
		assertNotNull(personDTOResponseWrapperCollection);
		assertFalse(personDTOResponseWrapperCollection.getPersonsData().isEmpty());
		assertTrue(personDTOResponseWrapperCollection.getPersonsData().size() == 1);
		assertTrue(personDTOResponseWrapperCollection.getPersonsData().get(0).getHttpStatus() == HttpStatus.BAD_REQUEST
				.value());
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

	private HttpHeaders newHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + getAccessToken());
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		return headers;
	}

	private String getAccessToken() {
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(
					"http://localhost:" + port + "/oauth/token?grant_type=password&username=user&password=password",
					null, String.class);

			assertEquals(HttpStatus.OK, response.getStatusCode());

			HashMap<?, ?> jwtMap = mapper.readValue(response.getBody(), HashMap.class);

			String accessToken = (String) jwtMap.get("access_token");

			return accessToken;
		} catch (Exception e) {
			// DO nothing
		}
		return "";
	}
}
