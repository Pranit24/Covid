package com.covid.springboot.repository;

import java.util.List;

import com.covid.springboot.model.CovidData;

public interface CovidRepositoryCustomFunctions {

	/**
	 * Only called when adding new data
	 * 
	 * @param data
	 * @param collectionName date of data
	 */
	void save(CovidData data, String collectionName);

	/**
	 * 
	 * Returns all data of the given date
	 * 
	 * @param collectionName
	 * @return
	 */
	List<CovidData> findAll(String collectionName);

	/**
	 * Returns data of a country of the given date
	 * 
	 * @param country
	 * @param collectionName
	 * @return
	 */
	List<CovidData> findByCountry(String country, String collectionName);

	/**
	 * Returns data of the state of a country of a given date
	 * 
	 * @param country
	 * @param state
	 * @param collectionName
	 * @return
	 */
	List<CovidData> findByState(String country, String state, String collectionName);

	/**
	 * US only - returns specific city data
	 * 
	 * @param city
	 * @param collectionName
	 * @return
	 */
	List<CovidData> findByRegion(String region, String collectionName);

	/**
	 * Check if collection exists
	 * 
	 * @param collectionName
	 */
	boolean checkCollection(String date);
}