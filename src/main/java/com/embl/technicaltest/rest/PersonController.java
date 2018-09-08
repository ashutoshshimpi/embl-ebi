package com.embl.technicaltest.rest;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.embl.technicaltest.datatransferobject.PersonDTO;
import com.embl.technicaltest.datatransferobject.PersonDTORequestWrapper;
import com.embl.technicaltest.datatransferobject.PersonDTOResponseWrapper;
import com.embl.technicaltest.datatransferobject.PersonDTOResponseWrapperCollection;
import com.embl.technicaltest.domainobject.PersonDO;
import com.embl.technicaltest.exception.EntityNotFoundException;
import com.embl.technicaltest.rest.mapper.PersonMapper;
import com.embl.technicaltest.service.PersonService;

/**
 * 
 * REST controller to expose person API
 * 
 * @author Ashutosh Shimpi
 *
 */
@PreAuthorize("hasRole('ROLE_PERSON')")
@RestController
@RequestMapping("/v1/persons")
public class PersonController {

	private static org.slf4j.Logger LOG = LoggerFactory.getLogger(PersonController.class);

	private final PersonService personService;

	@Autowired
	public PersonController(final PersonService personService) {

		this.personService = personService;
	}

	/**
	 * Create Persons API
	 * 
	 * @param PersonDTORequestWrapper object 
	 * @return ResponseEntity<PersonDTOResponseWrapperCollection>
	 */
	@PostMapping
	public ResponseEntity<PersonDTOResponseWrapperCollection> createPersons(
			@Valid @RequestBody PersonDTORequestWrapper personDTORequestWrapper) {

		PersonDTOResponseWrapperCollection personDTOResponseWrapperCollection = new PersonDTOResponseWrapperCollection();
		personDTOResponseWrapperCollection.setPersonsData(new ArrayList<>(personDTORequestWrapper.getPerson().size()));

		personDTORequestWrapper.getPerson().stream().forEach(personDTO -> {

			try {

				PersonDO personDO = PersonMapper.makePersonDO(personDTO);
				personDO = personService.createPerson(personDO);
				buildPersonResponseSuccess(personDO, personDTOResponseWrapperCollection.getPersonsData(),
						HttpStatus.CREATED.value());

			} catch (Exception e) {

				LOG.error(e.getMessage(), e);

				buildPersonResponseError(personDTOResponseWrapperCollection.getPersonsData(),
						HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error occurred while creating person");
			}
		});

		return ResponseEntity.status(HttpStatus.MULTI_STATUS).body(personDTOResponseWrapperCollection);
	}

	/**
	 * Get persons API
	 * 
	 * @param one or more personId
	 * @return ResponseEntity<PersonDTOResponseWrapperCollection>
	 */
	@GetMapping
	public ResponseEntity<PersonDTOResponseWrapperCollection> getPersons(@Valid @RequestParam long[] personId) {

		PersonDTOResponseWrapperCollection personDTOResponseWrapperCollection = new PersonDTOResponseWrapperCollection();
		personDTOResponseWrapperCollection.setPersonsData(new ArrayList<>(personId.length));

		for (int i = 0; i < personId.length; i++) {

			try {

				PersonDO personDO = personService.getPerson(personId[i]);
				buildPersonResponseSuccess(personDO, personDTOResponseWrapperCollection.getPersonsData(),
						HttpStatus.OK.value());

			} catch (EntityNotFoundException e) {

				LOG.error(e.getMessage(), e);

				buildPersonResponseError(personDTOResponseWrapperCollection.getPersonsData(),
						HttpStatus.BAD_REQUEST.value(), "Person not found with id " + personId[i]);

			} catch (Exception e) {

				LOG.error(e.getMessage(), e);

				buildPersonResponseError(personDTOResponseWrapperCollection.getPersonsData(),
						HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error occurred while creating person");
			}
		}

		return ResponseEntity.status(HttpStatus.MULTI_STATUS).body(personDTOResponseWrapperCollection);
	}

	/**
	 * Update Persons API
	 * 
	 * @param PersonDTORequestWrapper object
	 * @return ResponseEntity<PersonDTOResponseWrapperCollection>
	 */
	@PutMapping
	public ResponseEntity<PersonDTOResponseWrapperCollection> updatePersons(
			@Valid @RequestBody PersonDTORequestWrapper personDTORequestWrapper) {

		PersonDTOResponseWrapperCollection personDTOResponseWrapperCollection = new PersonDTOResponseWrapperCollection();
		personDTOResponseWrapperCollection.setPersonsData(new ArrayList<>(personDTORequestWrapper.getPerson().size()));

		personDTORequestWrapper.getPerson().stream().forEach(personDTO -> {

			try {

				PersonDO personDO = PersonMapper.makePersonDO(personDTO);
				personDO.setId(personDTO.getId());
				personDO = personService.updatePerson(personDO);

				buildPersonResponseSuccess(personDO, personDTOResponseWrapperCollection.getPersonsData(),
						HttpStatus.OK.value());

			} catch (EntityNotFoundException e) {

				LOG.error(e.getMessage(), e);

				buildPersonResponseError(personDTOResponseWrapperCollection.getPersonsData(),
						HttpStatus.BAD_REQUEST.value(), "Person not found with id " + personDTO.getId());

			} catch (Exception e) {

				LOG.error(e.getMessage(), e);

				buildPersonResponseError(personDTOResponseWrapperCollection.getPersonsData(),
						HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error occurred while updating person");
			}
		});

		return ResponseEntity.status(HttpStatus.MULTI_STATUS).body(personDTOResponseWrapperCollection);
	}

	/**
	 * Delete Persons API
	 * 
	 * @param one or more personIds
	 * @return ResponseEntity<PersonDTOResponseWrapperCollection>
	 */
	@DeleteMapping
	public ResponseEntity<PersonDTOResponseWrapperCollection> deletePersons(@RequestParam long[] personId) {

		PersonDTOResponseWrapperCollection personDTOResponseWrapperCollection = new PersonDTOResponseWrapperCollection();
		personDTOResponseWrapperCollection.setPersonsData(new ArrayList<>(personId.length));

		for (int i = 0; i < personId.length; i++) {

			try {

				PersonDO personDO = personService.getPerson(personId[i]);
				personService.deletePerson(personDO.getId());

				buildPersonResponseSuccess(personDO, personDTOResponseWrapperCollection.getPersonsData(),
						HttpStatus.OK.value());

			} catch (EntityNotFoundException e) {

				LOG.error(e.getMessage(), e);

				buildPersonResponseError(personDTOResponseWrapperCollection.getPersonsData(),
						HttpStatus.BAD_REQUEST.value(), "Person not found with id " + personId[i]);

			} catch (Exception e) {

				LOG.error(e.getMessage(), e);

				buildPersonResponseError(personDTOResponseWrapperCollection.getPersonsData(),
						HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error occurred while deleting person");
			}
		}

		return ResponseEntity.status(HttpStatus.MULTI_STATUS).body(personDTOResponseWrapperCollection);
	}

	private void buildPersonResponseSuccess(PersonDO personDO, List<PersonDTOResponseWrapper> personsData,
			int httpStatus) {

		PersonDTO personDTO = PersonMapper.makePersonDTO(personDO);
		PersonDTOResponseWrapper personDTOResponseWrapper = new PersonDTOResponseWrapper();
		personDTOResponseWrapper.setPerson(personDTO);
		personDTOResponseWrapper.setHttpStatus(httpStatus);
		personsData.add(personDTOResponseWrapper);
	}

	private void buildPersonResponseError(List<PersonDTOResponseWrapper> personsData, int httpStatus,
			String errorMessage) {

		PersonDTOResponseWrapper personDTOResponseWrapper = new PersonDTOResponseWrapper();
		personDTOResponseWrapper.setHttpStatus(httpStatus);
		personDTOResponseWrapper.setErrorMessage(errorMessage);
		personsData.add(personDTOResponseWrapper);
	}

	 /* Below APIs are for operations on single entity. This is just for a reference. */

	/**
	 * Get single Person API
	 * 
	 * @param personId 
	 * @return ResponseEntity of successful retrieval or error message
	 */
	@GetMapping("/{personId}")
	public ResponseEntity<?> getPerson(@Valid @PathVariable Long personId) {

		try {

			PersonDO personDO = personService.getPerson(personId);
			PersonDTO personDTO = PersonMapper.makePersonDTO(personDO);

			return ResponseEntity.ok(personDTO);

		} catch (EntityNotFoundException e) {

			LOG.error(e.getMessage(), e);

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Person not found with id " + personId);
		}
	}

	/**
	 * Update single Person API
	 * 
	 * @param personId
	 * @param personDTO object to be updated
	 * @return ResponseEntity of successful retrieval or error message
	 */
	@PutMapping("/{personId}")
	public ResponseEntity<?> updatePerson(@Valid @PathVariable Long personId, @Valid @RequestBody PersonDTO personDTO) {

		try {

			PersonDO personDO = PersonMapper.makePersonDO(personDTO);
			personDO.setId(personId);
			personDO = personService.updatePerson(personDO);

			return ResponseEntity.ok(personDTO);

		} catch (EntityNotFoundException e) {

			LOG.error(e.getMessage(), e);

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Person not found with id " + personDTO.getId());
		}
	}

	/**
	 * Delete single Person API
	 * 
	 * @param personId
	 * @return ResponseEntity of successful retrieval or error message
	 */
	@DeleteMapping("/{personId}")
	public ResponseEntity<?> deletePerson(@Valid @PathVariable Long personId) {

		try {

			PersonDO personDO = personService.getPerson(personId);
			personService.deletePerson(personDO.getId());

			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

		} catch (EntityNotFoundException e) {

			LOG.error(e.getMessage(), e);

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Person not found with id " + personId);
		}
	}
}
