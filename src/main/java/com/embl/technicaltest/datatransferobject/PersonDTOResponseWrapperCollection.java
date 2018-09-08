package com.embl.technicaltest.datatransferobject;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonDTOResponseWrapperCollection {

	private List<PersonDTOResponseWrapper> personsData;

	public List<PersonDTOResponseWrapper> getPersonsData() {
		return personsData;
	}

	public void setPersonsData(List<PersonDTOResponseWrapper> personsData) {
		this.personsData = personsData;
	}
}
