package com.covid.springboot.comparator;

import java.util.Comparator;

import com.covid.springboot.model.Statistics;

public class StatsConfirmedComparator implements Comparator<Statistics> {

	@Override
	public int compare(Statistics o1, Statistics o2) {
		return (int) (o2.getConfirmed() - o1.getConfirmed());
	}

}
