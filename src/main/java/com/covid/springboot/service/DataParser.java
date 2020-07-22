package com.covid.springboot.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covid.springboot.model.CovidData;
import com.covid.springboot.repository.CovidRepository;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;

// TODO runs at a given time to fetch latest data
/**
 * @author Pranit Gets the latest data and adds it to new mongoDb collection
 *
 */
@Service
public class DataParser {

	@Autowired
	private CovidRepository covidRepository;

	/**
	 * Gets all the CSV data using the specified date from John Hopkins github
	 * 
	 * @param date
	 * @return List<String> of CSV data
	 */
	public List<String> getData(String date) {
		List<String> list = new ArrayList<>();
		try {
			URL url = new URL(
					"https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports/"
							+ date + ".csv");

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

	private List<String> getLatestData() {
		return getData(getYesterdayDate());
	}

	/**
	 * Convert CSV file to Model class and save it in MongoDb
	 */
	public void convertCSVToBean() {

		List<String> list = getLatestData();
		// drop first row which has the csv column information
		list.remove(0);
		int i = 0;
		for (String row : list) {
			CsvToBeanBuilder<CovidData> beanBuilder = new CsvToBeanBuilder<>(new CSVReader(new StringReader(row)));

			beanBuilder.withType(CovidData.class);
			// build methods returns a list of Beans
			CovidData data = beanBuilder.build().parse().get(0);
			data.set_id(String.valueOf(i++));
			covidRepository.save(data, getYesterdayDate());
			break;
		}

	}

	/**
	 * HELPER FUNCTION - Used to get the yesterday's date
	 * 
	 * @return String - yesterday's date which has the most updated information
	 */
	public String getYesterdayDate() {
		Instant yesterday = Instant.now().minus(1, ChronoUnit.DAYS);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy").withLocale(Locale.US)
				.withZone(ZoneId.systemDefault());
		String date = formatter.format(yesterday);
		return date;
	}

}
