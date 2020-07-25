package com.covid.springboot.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import com.covid.springboot.model.Statistics;

public final class Helper {

	/**
	 * HELPER FUNCTION - Used to get the yesterday's date
	 * 
	 */
	public static String getYesterdayDate() {
		return getDate(1);
	}

	/**
	 * HELPER FUNCTION - Used to get date relative to today
	 * 
	 * @return minus - Get date minus days before current date
	 */
	public static String getDate(Integer minus) {
		Instant yesterday = Instant.now().minus(minus, ChronoUnit.DAYS);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy").withLocale(Locale.US)
				.withZone(ZoneId.systemDefault());
		String date = formatter.format(yesterday);
		return date;

	}

	public static Map<String, Statistics> sort(Map<String, Statistics> map) {
		List<Entry<String, Statistics>> list = new LinkedList<Entry<String, Statistics>>(map.entrySet());

		// Sorting the list based on values
		Collections.sort(list, new Comparator<Entry<String, Statistics>>() {
			public int compare(Entry<String, Statistics> o1, Entry<String, Statistics> o2) {
				return o1.getValue().compareTo(o2.getValue());

			}
		});

		// Maintaining insertion order with the help of LinkedList
		Map<String, Statistics> sortedMap = new LinkedHashMap<String, Statistics>();
		for (Entry<String, Statistics> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
	}
}
