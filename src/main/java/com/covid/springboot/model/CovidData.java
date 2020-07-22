package com.covid.springboot.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
public class CovidData {

	@Id
	private String _id;

	@CsvBindByPosition(position = 0)
	@Field(value = "FIPS")
	private String FIPS;

	@CsvBindByPosition(position = 1)
	@Field(value = "Admin2")
	private String Admin2;

	@CsvBindByPosition(position = 2)
	@Field(value = "provinceState")
	private String provinceState;

	@CsvBindByPosition(position = 3)
	@Field(value = "countryRegion")
	private String countryRegion;

	@CsvDate(value = "yyyy-mm-dd HH:mm:ss")
	@CsvBindByPosition(position = 4)
	@Field(value = "lastUpdate")
	private Date lastUpdate;

	@CsvBindByPosition(position = 5)
	@Field(value = "latitude")
	private Double latitude;

	@CsvBindByPosition(position = 6)
	@Field(value = "longitude")
	private Double longitude;

	@CsvBindByPosition(position = 7)
	@Field(value = "confirmed")
	private Long confirmed;

	@CsvBindByPosition(position = 8)
	@Field(value = "deaths")
	private Integer deaths;

	@CsvBindByPosition(position = 9)
	@Field(value = "recovered")
	private Integer recovered;

	@CsvBindByPosition(position = 10)
	@Field(value = "active")
	private Integer active;

	@CsvBindByPosition(position = 11)
	@Field(value = "combinedKey")
	private String combinedKey;

	@CsvBindByPosition(position = 12)
	@Field(value = "incidenceRate")
	private Float incidenceRate;

	@CsvBindByPosition(position = 13)
	@Field(value = "fatalityRatio")
	private Float fatalityRatio;
}
