package com.embl.technicaltest.unittestcase.person;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.embl.technicaltest.domainobject.PersonDO;
import com.embl.technicaltest.exception.EntityNotFoundException;
import com.embl.technicaltest.repository.PersonRepository;
import com.embl.technicaltest.service.PersonService;

/**
 * Unit test cases to test Person service	
 * 
 * @author Ashutosh Shimpi
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class PersonServiceUnitTest {

	@Mock
	private PersonRepository personRepository;

	@InjectMocks
	private PersonService personService;

	/**
	 * Positive test case to test createPerson
	 */
	@Test
	public void createPersonTest() {

		when(personRepository.save(getPersonDOInstance())).thenReturn(getPersonDOInstance());
		PersonDO personDO = personService.createPerson(getPersonDOInstance());
		assertions(personDO);
	}

	/**
	 * Positive test case to test getPerson
	 * 
	 * @throws EntityNotFoundException if person not found
	 */
	@Test
	public void getPersonTest() throws EntityNotFoundException {

		when(personRepository.findById(anyLong())).thenReturn(Optional.of(getPersonDOInstance()));
		PersonDO personDO = personService.getPerson(Long.valueOf(1));
		assertions(personDO);
	}

	/**
	 * Positive test case to test updatePerson
	 * 
	 * @throws EntityNotFoundException if person not found
	 */
	@Test
	public void updatePersonTest() throws EntityNotFoundException {

		when(personRepository.findById(anyLong())).thenReturn(Optional.of(getPersonDOInstance()));
		PersonDO personDO = personService.updatePerson(getPersonDOInstance());
		assertions(personDO);
	}

	/**
	 * Positive test case to test deletePerson
	 * 
	 * @throws EntityNotFoundException if person not found
	 */
	@Test
	public void deletePersonTest() throws EntityNotFoundException {

		when(personRepository.findById(anyLong())).thenReturn(Optional.of(getPersonDOInstance()));
		doNothing().when(personRepository).delete(getPersonDOInstance());
		personService.deletePerson(Long.valueOf(1));
	}

	/**
	 * Negative test case to test EntityNotFoundException if person not found
	 * 
	 * @throws EntityNotFoundException if person not found
	 */
	@Test(expected = EntityNotFoundException.class)
	public void getPerson_EntityNotFoundExceptionTest() throws EntityNotFoundException {

		when(personRepository.findById(anyLong())).thenReturn(Optional.empty());
		personService.getPerson(Long.valueOf(1));
	}

	private PersonDO getPersonDOInstance() {

		PersonDO personDO = new PersonDO("Ashutosh", "Shimpi", "28");
		personDO.setId(Long.valueOf(1));
		personDO.setFavouriteColour("Black");

		return personDO;
	}

	private void assertions(PersonDO personDO) {

		assertTrue(personDO.getId().equals(Long.valueOf(1)));
		assertTrue(personDO.getFirstName().equals("Ashutosh"));
		assertTrue(personDO.getLastName().equals("Shimpi"));
		assertTrue(personDO.getFavouriteColour().equals("Black"));
		assertTrue(personDO.getAge().equals("28"));
	}
}
