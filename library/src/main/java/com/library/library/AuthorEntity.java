package com.library.library;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;

import org.springframework.lang.NonNull;





@Entity
@Table(name="authors")
public class AuthorEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6780423088501025847L;
	@Id
	@Column(name="id")
	@NonNull
	@GeneratedValue(strategy=GenerationType.AUTO)

	private Integer id;
	@Column(name="first_name")
	private String firstName;
	@Column(name="last_name")
	private String lastName;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	public AuthorEntity(int id, String firstName, String lastName) {
		
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	public AuthorEntity() {

	}
	
}
