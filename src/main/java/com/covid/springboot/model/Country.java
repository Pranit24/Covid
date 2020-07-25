package com.covid.springboot.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "Country")
public class Country {

	@Id
	private String _id;

	@Field(value = "name")
	private String country;

	public Country(String id, String country) {
		this._id = id;
		this.country = country;
	}

	public Country(String country) {
		this.country = country;
	}

	public Country() {

	}
}
