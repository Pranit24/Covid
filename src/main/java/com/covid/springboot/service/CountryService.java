package com.covid.springboot.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covid.springboot.model.Country;
import com.covid.springboot.model.CovidData;
import com.covid.springboot.repository.CountryRepository;

@Service
public class CountryService {

	@Autowired
	private CountryRepository countryRepository;

	/**
	 * Populates country collection with list of countries
	 * 
	 * @param covidData
	 */
	public void createCountryCollection(List<CovidData> covidData) {
		int i = 0;
		Set<String> set = new HashSet<>();
		Set<Country> countrySet = new HashSet<>();

		for (CovidData data : covidData) {
			if (set.add(data.getCountryRegion())) {
				countrySet.add(new Country(String.valueOf(i++), data.getCountryRegion()));
			}
		}
		countryRepository.saveAll(countrySet);
	}

	public List<Country> getAllCountries() {
		return countryRepository.findAll();
	}
}
