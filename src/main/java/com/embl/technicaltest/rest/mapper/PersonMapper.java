package com.embl.technicaltest.rest.mapper;

import com.embl.technicaltest.datatransferobject.PersonDTO;
import com.embl.technicaltest.domainobject.PersonDO;

/**
 * DO, DTO mapper class
 *
 */
public class PersonMapper {

	/**
	 * Maps PersonDTO to PersonDO
	 * 
	 * @param personDTO
	 * @return PersonDO
	 */
	public static PersonDO makePersonDO(PersonDTO personDTO) {

		PersonDO personDO = new PersonDO(personDTO.getFirstName(), personDTO.getLastName(), personDTO.getAge());
		personDO.setFavouriteColour(personDTO.getFavouriteColour());

		return personDO;
	}

	/**
	 * Maps PersonDO to PersonDTO
	 * 
	 * @param personDO
	 * @return PersonDTO
	 */
	public static PersonDTO makePersonDTO(PersonDO personDO) {

		PersonDTO.PersonDTOBuilder personDTOBuilder = PersonDTO.newBuilder();
		personDTOBuilder.setAge(personDO.getAge());
		personDTOBuilder.setFirstName(personDO.getFirstName());
		personDTOBuilder.setLastName(personDO.getLastName());
		personDTOBuilder.setId(personDO.getId());
		personDTOBuilder.setFavouriteColour(personDO.getFavouriteColour());

		return personDTOBuilder.createPersonDTO();
	}
}
