package com.potatospy.photoapp.api.users.ui.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;




// Object backing response to user after creation request
// NOTE: NO password returned, but userId is


public class CreateUserResponseModel {
	@Getter
	@Setter
	@NotNull(message="It's null!")
	@Pattern(regexp="[A-Za-z]*", message="Alphas only")
	private String firstName;
	@Getter
	@Setter
	@NotNull(message="It's null!")
	@Pattern(regexp="[A-Za-z]*", message="Alphas only")
	private String lastName;
	@Getter
	@Setter
	@NotNull(message="It's null!")
	@Size(min=16, max=128)
	private String userId;
	@Getter
	@Setter
	@NotNull(message="It's null!")
	@Email(message="Need an email address")
	private String email;
}
