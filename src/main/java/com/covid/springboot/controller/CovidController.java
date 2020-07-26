package com.covid.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.covid.springboot.model.CovidData;
import com.covid.springboot.model.Statistics;
import com.covid.springboot.service.CovidService;

@RestController
@RequestMapping("api")
public class CovidController {

	@Autowired
	private CovidService covidService;

	@RequestMapping("/all")
	public List<CovidData> getLatestData() {
		return covidService.getData(true);
	}

	// TODO handle bad requests
	@RequestMapping("/search")
	public List<CovidData> getDataByCountry(@RequestParam("country") String country,
			@RequestParam(value = "state", required = false) String state,
			@RequestParam(value = "region", required = false) String region) {

		if (region != null) {
			return covidService.getDataByRegion(region);
		}
		if (state != null) {
			return covidService.getDataByState(country, state);
		}
		return covidService.getDataByCountry(country);
	}

	/**
	 * Get the worldwide stats
	 * 
	 * @return
	 */
	@RequestMapping("/data")
	public List<Statistics> getWorldwideStats() {
		return covidService.getWorldwideStats();
	}

	@RequestMapping("/data/{country}")
	public List<Statistics> getWorldwideStats(@PathVariable String country) {
		return covidService.getCountryStats(country);
	}

}
