package com.covid.springboot.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
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

	Logger logger = Logger.getLogger(DataParser.class.getName());

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
			logger.error("Error in getting the data");
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
		logger.info("Adding latest data to MongoDb collection " + date + " ...");
		final HeaderColumnNameMappingStrategy<CovidData> strategy = new HeaderColumnNameMappingStrategy<>();
		strategy.setType(CovidData.class);
		String csv = String.join("\n", list);
		try {
			CsvToBean<CovidData> csvToBean = new CsvToBeanBuilder<CovidData>(new CSVReader(new StringReader(csv)))
					.withMappingStrategy(strategy).build();
			List<CovidData> covidData = csvToBean.parse();
			for (CovidData data : covidData) {
				if (skipUS && data.getCountryRegion().equals("US"))
					continue;
				data.set_id(String.valueOf(this.i++));
				covidRepository.save(data, date);
			}
		} catch (Exception e) {
			logger.error("Error adding data to mongodb collection " + date);
			logger.error(e);
			return false;
		}
		logger.info("Done updating mongodb collection " + date + "!");
		return true;
	}

	/**
	 * Convert CSV file to Model class and save it in MongoDb
	 */
	private boolean convertCSVToBean(String date) {
//		if (covidRepository.checkCollection(date)) {
//			return true;
//		}
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

	public static boolean doesURLExist(URL url) throws IOException {
		// We want to check the current URL
		HttpURLConnection.setFollowRedirects(false);

		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

		// We don't need to get data
		httpURLConnection.setRequestMethod("HEAD");

		// Some websites don't like programmatic access so pretend to be a browser
		httpURLConnection.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)");
		int responseCode = httpURLConnection.getResponseCode();

		// We only accept response code 200
		return responseCode == HttpURLConnection.HTTP_OK;
	}

	@Scheduled(fixedDelay = 86400000)
//	@Scheduled(cron = "0 5 20 * * ?")
	private void getLatestData() {
		String latestDate = Helper.getYesterdayDate();
		logger.info("Downloading latest data ");
//		String latestDate = "07-30-2020";
		String link = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports/";
		try {
			while (!doesURLExist(new URL(link + latestDate + ".csv"))) {
				latestDate = Helper.getDate(++this.i);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info(latestDate);
		if (convertCSVToBean(latestDate)) {
			covidService.setLatestDate(latestDate);
		}

	}

}
