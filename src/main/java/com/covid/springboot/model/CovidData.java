package com.covid.springboot.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Document
@ToString
public class CovidData {

	@Id
	private String _id;

	@CsvBindByName(column = "fips")
	@Field(value = "FIPS")
	private String FIPS;

	@CsvBindByName(column = "Province_State")
	@Field(value = "provinceState")
	private String provinceState;

	@CsvBindByName(column = "Country_Region")
	@Field(value = "countryRegion")
	private String countryRegion;

	@CsvDate(value = "yyyy-mm-dd HH:mm:ss")
	@CsvBindByName(column = "Last_Update")
	@Field(value = "lastUpdate")
	private Date lastUpdate;

	@CsvBindByName(column = "Lat")
	@Field(value = "latitude")
	private Double latitude;

	@CsvBindByName(column = "Long_")
	@Field(value = "longitude")
	private Double longitude;

	@CsvBindByName(column = "Confirmed")
	@Field(value = "confirmed")
	private Double confirmed = 0.0;

	@CsvBindByName(column = "Deaths")
	@Field(value = "deaths")
	private Double deaths = 0.0;

	@CsvBindByName(column = "Recovered")
	@Field(value = "recovered")
	private Double recovered = 0.0;

	@CsvBindByName(column = "Active")
	@Field(value = "active")
	private Double active = 0.0;

	@CsvBindByName(column = "Incidence_Rate")
	@Field(value = "incidenceRate")
	private Double incidenceRate = 0.0;

	@CsvBindByName(column = "Incident_Rate")
	@Field(value = "incidentRate")
	private Double incidentRate = 0.0;

	@CsvBindByName(column = "Case-Fatality_Ratio")
	@Field(value = "fatalityRatio")
	private Double fatalityRatio = 0.0;

	@CsvBindByName(column = "Testing_Rate")
	@Field(value = "testingRate")
	private Double testingRate = 0.0;

	@CsvBindByName(column = "Hospitalization_Rate")
	@Field(value = "hospitalizationRate")
	private Double hospitalizationRate = 0.0;

	@CsvBindByName(column = "People_Tested")
	@Field(value = "peopleTested")
	private Double peopleTested = 0.0;

	@CsvBindByName(column = "People_Hospitalized")
	@Field(value = "peopleHospitalized")
	private Double peopleHospitalized = 0.0;
}
