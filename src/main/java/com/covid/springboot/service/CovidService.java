package com.covid.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covid.springboot.model.CovidData;
import com.covid.springboot.repository.CovidRepository;

/**
 * @author Pranit
 * 
 *         Get data from https://github.com/CSSEGISandData/COVID-19
 *
 */
@Service
public class CovidService {

	@Autowired
	private DataParser dataParser;

	@Autowired
	private CovidRepository covidRepository;

	private String latestDate = "Covid";

	public CovidService() {
//		this.latestDate = dataParser.getYesterdayDate();
	}

	/**
	 * Returns all data the latest data
	 * 
	 * @return Latest data
	 */
	public List<CovidData> getData() {
		List<CovidData> covidData = covidRepository.findAll(latestDate);
//		if (covidData.size() == 0) {
//			
//			dataParser.convertCSVToBean();
//		}
//		dataParser.convertCSVToBean();
		return covidData;
	}

	/**
	 * Returns a list of data by country using the latest data
	 * 
	 * @param country
	 * @return
	 */
	public List<CovidData> getDataByCountry(String country) {
		return covidRepository.findByCountry(country, latestDate);
	}

	/**
	 * Returns a list of data by state and country using the latest data
	 * 
	 * @param country
	 * @param state
	 * @return
	 */
	public List<CovidData> getDataByState(String country, String state) {
		return covidRepository.findByState(country, state, latestDate);
	}

	/**
	 * US only- returns a list of data by city using the latest data
	 * 
	 * @param city
	 * @return
	 */
	public List<CovidData> getDataByRegion(String region) {
		return covidRepository.findByRegion(region, latestDate);
	}

	/**
	 * Returns all data of the specified date
	 * 
	 * @param date
	 * @return
	 */
	public List<CovidData> getData(String date) {
		List<CovidData> covidData = covidRepository.findAll(date);
		return covidData;
	}

	/**
	 * Returns a list of data by country using specified date
	 * 
	 * @param country
	 * @param date
	 * @return
	 */
	public List<CovidData> getDataByCountry(String country, String date) {
		return covidRepository.findByCountry(country, date);
	}

	/**
	 * Returns a list of data by state and country using the specified date
	 * 
	 * @param country
	 * @param state
	 * @param date
	 * @return
	 */
	public List<CovidData> getDataByState(String country, String state, String date) {
		return covidRepository.findByState(country, state, date);
	}

	/**
	 * US only- returns a list of data by city using the specified date
	 * 
	 * @param city
	 * @param date
	 * @return
	 */
	public List<CovidData> getDataByRegion(String region, String date) {
		return covidRepository.findByRegion(region, date);
	}
}
