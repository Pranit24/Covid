package com.covid.springboot.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covid.springboot.comparator.StatsConfirmedComparator;
import com.covid.springboot.model.CovidData;
import com.covid.springboot.model.Statistics;
import com.covid.springboot.repository.CovidRepository;
import com.covid.springboot.util.Helper;

/**
 * @author Pranit
 * 
 *         Get data from https://github.com/CSSEGISandData/COVID-19
 *
 */
@Service
public class CovidService {

//	@Autowired
//	private DataParser dataParser;

	@Autowired
	private CovidRepository covidRepository;

//	@Autowired
//	private CountryService countryService;

	private String latestDate;
	private List<CovidData> covidData;

	/**
	 * Initialize latest data available
	 */
	public CovidService() {
		this.latestDate = Helper.getYesterdayDate();

	}

	private void getLatestData() {
		int i = 1;
		while (!covidRepository.checkCollection(this.latestDate)) {
			this.latestDate = Helper.getDate(++i);
		}
	}

	public void setLatestDate(String date) {
		this.latestDate = date;
	}

	/**
	 * Returns the data if already exists or get the latest data
	 * 
	 * @param forceNew - Has to get the latest data
	 * @return Latest data
	 */
	public List<CovidData> getData(boolean forceNew) {
		if (forceNew) {
			this.getLatestData();
			this.covidData = covidRepository.findAll(latestDate);
			return this.covidData;
		} else if (this.covidData != null) {
			return this.covidData;
		}
		this.covidData = covidRepository.findAll(latestDate);
		return this.covidData;
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
		return covidRepository.findAll(date);
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

	/**
	 * Returns the list statistics containing worldwide stats
	 * 
	 * @param place
	 * @return
	 */
	public List<Statistics> getWorldwideStats() {
		List<CovidData> latestData = getData(false);
		Map<String, Statistics> latestMap = this.getWorldwideStats(latestData);
		List<CovidData> oldData = getData(Helper.getDate(2));
		Map<String, Statistics> oldMap = this.getWorldwideStats(oldData);
		latestMap = this.calculateDifference(latestMap, oldMap);
		List<Statistics> stats = new ArrayList<Statistics>(latestMap.values());
		Collections.sort(stats, new StatsConfirmedComparator());
		return stats;
	}

	private Map<String, Statistics> getWorldwideStats(List<CovidData> data) {
		Map<String, Statistics> map = new HashMap<>();
		Statistics total = new Statistics("Total");
		for (CovidData covid : data) {
			String place = covid.getCountryRegion();
			System.out.println(place);
			if (map.containsKey(place)) {
				Statistics stats = map.get(place);
				total.addStats(covid);
				stats.addStats(covid);
				map.put(place, stats);
			} else {
				total.addStats(covid);
				map.put(place, new Statistics(covid, place));
			}
		}
//		map.put("Total", total);
		return map;
	}

	/**
	 * Returns a list of statistics of a country
	 * 
	 * @param country
	 * @return
	 */
	public List<Statistics> getCountryStats(String country) {
		List<CovidData> latestData = getDataByCountry(country);
		for (CovidData data : latestData) {
			if (!data.getCountryRegion().contains("US")) {
				System.out.println(data);
			}
		}
		Map<String, Statistics> latestMap = getCountryStats(latestData);
		List<CovidData> oldData = getDataByCountry(country, Helper.getDate(2));
		Map<String, Statistics> oldMap = getCountryStats(oldData);
		latestMap = this.calculateDifference(latestMap, oldMap);
		List<Statistics> stats = new ArrayList<Statistics>(latestMap.values());
		Collections.sort(stats, new StatsConfirmedComparator());
		return stats;
	}

	private Map<String, Statistics> calculateDifference(Map<String, Statistics> latestMap,
			Map<String, Statistics> oldMap) {

		for (String key : oldMap.keySet()) {
			if (latestMap.containsKey(key) && oldMap.containsKey(key)) {
				Statistics newStats = latestMap.get(key);
				Statistics oldStats = oldMap.get(key);
				newStats.updateDifference(oldStats);
				latestMap.put(key, newStats);
			}
		}
		return latestMap;
	}

	private Map<String, Statistics> getCountryStats(List<CovidData> latestData) {
		Map<String, Statistics> map = new HashMap<>();
		for (CovidData covid : latestData) {
			String place = covid.getProvinceState();
			if (map.containsKey(place)) {
				Statistics stats = map.get(place);
				stats.addStats(covid);
				map.put(place, stats);
			} else {
				map.put(place, new Statistics(covid, place));
			}
		}
		return map;
	}
}
