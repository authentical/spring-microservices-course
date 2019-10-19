package com.potatospy.photoapp.api.users.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CreateUserRequestModel {
	
	
	
	@Email(message="Invalid Email")
	@NotNull(message="Can't be null")
	private String email;
	@Pattern(regexp="[^0-9]*")
	@NotNull(message="Can't be null")
	private String firstName;
	@Pattern(regexp="[^0-9]*")
	@NotNull(message="Can't be null")
	private String lastName;
	@NotNull(message="Can't be null")
	@Size(min=16, max=128)
	private String password;
	
	
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
