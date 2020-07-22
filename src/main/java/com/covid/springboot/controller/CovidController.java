package com.covid.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.covid.springboot.model.CovidData;
import com.covid.springboot.service.CovidService;

@RestController
public class CovidController {

	@Autowired
	private CovidService covidService;

	@RequestMapping("/")
	public List<CovidData> getLatestData() {
		return covidService.getData();
	}

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

}
