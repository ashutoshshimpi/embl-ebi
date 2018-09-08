package com.embl.technicaltest.datatransferobject;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonDTORequestWrapper {

	@Valid
	@NotNull
	private List<PersonDTO> person;

	public PersonDTORequestWrapper() {

	}

	public List<PersonDTO> getPerson() {
		return person;
	}

	public void setPerson(List<PersonDTO> person) {
		this.person = person;
	}
}
