package com.covid.springboot.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.covid.springboot.model.CovidData;
import com.covid.springboot.repository.CovidRepository;
import com.covid.springboot.util.Helper;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

// TODO runs at a given time to fetch latest data
/**
 * @author Pranit Gets the latest data and adds it to new mongoDb collection
 *
 */
@Service
public class DataParser {

	@Autowired
	private CovidRepository covidRepository;

	@Autowired
	private CovidService covidService;

	private int i = 0;

	/**
	 * Gets all the CSV data using the specified date from John Hopkins github
	 * 
	 * @param date
	 * @return List<String> of CSV data
	 */
	private List<String> getData(String date, String link) {
		List<String> list = new ArrayList<>();
		try {
			URL url = new URL(link + date + ".csv");

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			try (BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
				String line;
				while ((line = input.readLine()) != null) {
					list.add(line);
				}
			} finally {
				connection.disconnect();
			}
		} catch (IOException e) {
			System.out.println("Error in getting the data");
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 
	 * Take csv data and collection and add the data to MongoDb collection
	 * 
	 * @param list
	 * @param date - Date of the data and the collection name
	 * @return - return false if error in adding data
	 */
	private boolean addDataToMongo(List<String> list, String date, boolean skipUS) {
		// drop first row which has the csv column information
		System.out.println("Adding latest data to MongoDb collection " + date + " ...");
		final HeaderColumnNameMappingStrategy<CovidData> strategy = new HeaderColumnNameMappingStrategy<>();
		strategy.setType(CovidData.class);
		String csv = String.join("\n", list);
		try {
//			CsvToBeanBuilder<CovidData> beanBuilder = new CsvToBeanBuilder<>(new CSVReader(new StringReader(csv)));
			CsvToBean<CovidData> csvToBean = new CsvToBeanBuilder<CovidData>(new CSVReader(new StringReader(csv)))
					.withMappingStrategy(strategy).build();
//			beanBuilder.withType(CovidData.class);
			// build methods returns a list of Beans
//			List<CovidData> covidData = beanBuilder.build().parse(); 
			List<CovidData> covidData = csvToBean.parse();
			for (CovidData data : covidData) {
				if (skipUS && data.getCountryRegion().equals("US"))
					continue;
				data.set_id(String.valueOf(this.i++));
				covidRepository.save(data, date);
			}
		} catch (Exception e) {
			System.out.println("Error adding data to mongodb collection " + date);
			System.out.println(e);
			return false;
		}
		System.out.println("Done updating mongodb collection " + date + "!");
		return true;
	}

	/**
	 * Convert CSV file to Model class and save it in MongoDb
	 */
	private boolean convertCSVToBean(String date) {
		String link = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports/";
		List<String> list = getData(date, link);
		try {
			addDataToMongo(list, date, true);
		} catch (Exception e) {
			return false;
		}
		String usLink = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports_us/";
		List<String> usList = getData(date, usLink);
		try {
			addDataToMongo(usList, date, false);
		} catch (

		Exception e) {
			return false;
		}
		return true;
	}

//	@Scheduled(fixedDelay = 500000)
	@Scheduled(zone = "GMT+0:00", cron = "0 20 5 * * ?")
	private void getLatestData() {
		String latestDate = Helper.getYesterdayDate();
//		String latestDate = "07-27-2020";
		System.out.println("Getting latest data " + latestDate);
		if (convertCSVToBean(latestDate)) {
			covidService.setLatestDate(latestDate);
		}

	}

}
