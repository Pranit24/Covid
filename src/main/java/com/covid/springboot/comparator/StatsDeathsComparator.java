package com.covid.springboot.comparator;

import java.util.Comparator;

import com.covid.springboot.model.Statistics;

public class StatsDeathsComparator implements Comparator<Statistics> {

	@Override
	public int compare(Statistics o1, Statistics o2) {
		return (int) (o1.getDeaths() - o2.getDeaths());
	}

}
