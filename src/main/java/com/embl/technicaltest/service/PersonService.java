package com.embl.technicaltest.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.embl.technicaltest.domainobject.PersonDO;
import com.embl.technicaltest.exception.EntityNotFoundException;
import com.embl.technicaltest.repository.PersonRepository;

/**
 * Service class provides Persons DB operation like CRUD.
 * 
 * @author Ashutosh Shimpi
 *
 */
@Service
public class PersonService {

	private PersonRepository personRepository;

	private PersonService() {
	}

	/**
	 * Parameterized constructor
	 * 
	 * @param personRepository
	 */
	@Autowired
	public PersonService(PersonRepository personRepository) {

		this.personRepository = personRepository;
	}

	/**
	 * Create Person
	 * 
	 * @param personDO object of PersonDO
	 * @return PersonDO return newly created object
	 */
	public PersonDO createPerson(final PersonDO personDO) {

		return personRepository.save(personDO);
	}

	/**
	 * @param personId Long id
	 * @return PersonDO if found
	 * @throws EntityNotFoundException throws error if Person not found
	 */
	public PersonDO getPerson(final Long personId) throws EntityNotFoundException {

		Optional<PersonDO> optionalPersonDO = personRepository.findById(personId);

		if (optionalPersonDO.isPresent()) {

			return optionalPersonDO.get();
		}

		throw EntityNotFoundException.newInstance("Entity not found with Id " + personId);

	}

	/**
	 * Update the Person
	 * 
	 * @param personDO PersonDO object that needs to be updated
	 * @return PersonDO updated object
	 * @throws EntityNotFoundException if Person not found
	 */
	@Transactional
	public PersonDO updatePerson(final PersonDO personDO) throws EntityNotFoundException {

		PersonDO personDOTmp = getPerson(personDO.getId());

		personDOMapper(personDO, personDOTmp);

		return personDOTmp;
	}

	/**
	 * Delete the Person
	 * 
	 * @param personId Long personId
	 * @throws EntityNotFoundException if Person not found
	 */
	public void deletePerson(Long personId) throws EntityNotFoundException {

		PersonDO personDO = getPerson(personId);
		personRepository.delete(personDO);

	}

	private void personDOMapper(PersonDO source, PersonDO destination) {
		destination.setAge(source.getAge());
		destination.setFavouriteColour(source.getFavouriteColour());
		destination.setLastName(source.getLastName());
		destination.setFirstName(source.getFirstName());
	}
}
