package com.potatospy.photoapp.api.users.dao.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;



@Entity
@Table(name="users")
public class UserEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1456019248248L;
	
	
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable=false, length=50)
	@Getter
	@Setter
	private String firstName;
	@Column(nullable=false, length=50)
	@Getter
	@Setter
	private String lastName;
//	@Getter
//	@Setter
//	private String password; // Obv dont save unencrypted
	@Column(nullable=false, length=120, unique=true)
	@Getter
	@Setter
	private String email;
	@Column(nullable=false, unique=true)
	@Getter
	@Setter
	private String encryptedPassword;
	@Column(nullable=false, unique=true)
	@Getter
	@Setter
	private String userId;
	
	

}
