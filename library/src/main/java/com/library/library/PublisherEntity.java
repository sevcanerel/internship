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
@Table(name="publishers")
public class PublisherEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4579603310450785290L;
	@Id

	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	@NonNull
	private Integer id ;
	@Column(name="name")
	private String name;
	@Column(name="address")
	private String address;
	@Column(name="phone")
	private String phone;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public PublisherEntity(int id, String name, String address, String phone) {
		
		this.id = id;
		this.name = name;
		this.address = address;
		this.phone = phone;
	}
	public PublisherEntity() {
			}
	
}
