package com.embl.technicaltest.datatransferobject;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Person DTO
 * 
 * @author Ashutosh Shimpi
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonDTO {

	private Long id;

	@NotNull(message = "First Name can not be null!")
	@JsonProperty("first_name")
	private String firstName;

	@NotNull(message = "Last Name can not be null!")
	@JsonProperty("last_name")
	private String lastName;

	@NotNull(message = "Age can not be null!")
	private String age;

	@JsonProperty("favourite_colour")
	private String favouriteColour;

	private PersonDTO() {

	}

	private PersonDTO(Long id, String firstName, String lastName, String age) {

		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
	}

	public static PersonDTOBuilder newBuilder() {
		return new PersonDTOBuilder();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getFavouriteColour() {
		return favouriteColour;
	}

	public void setFavouriteColour(String favouriteColour) {
		this.favouriteColour = favouriteColour;
	}

	public static class PersonDTOBuilder {

		private Long id;
		private String firstName;
		private String lastName;
		private String age;
		private String favouriteColour;

		public PersonDTOBuilder setId(Long id) {
			this.id = id;
			return this;
		}

		public PersonDTOBuilder setFirstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public PersonDTOBuilder setLastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public PersonDTOBuilder setAge(String age) {
			this.age = age;
			return this;
		}

		public PersonDTOBuilder setFavouriteColour(String favouriteColour) {
			this.favouriteColour = favouriteColour;
			return this;
		}

		public PersonDTO createPersonDTO() {

			PersonDTO personDTO = new PersonDTO(id, firstName, lastName, age);
			personDTO.setFavouriteColour(favouriteColour);
			return personDTO;
		}
	}
}
