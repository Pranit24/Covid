package com.covid.springboot.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Statistics extends Country implements Comparable<Statistics> {

	private Double confirmed = 0.0;

	private Double deaths = 0.0;

	private Double recovered = 0.0;

	private Double active = 0.0;

	private Float incidenceRate;

	private Float fatalityRatio;

	private Statistics difference;

	public void updateDifference(Statistics diff) {
		difference = new Statistics();
		this.difference.setConfirmed(this.confirmed - diff.getConfirmed());
		this.difference.setDeaths(this.deaths - diff.getDeaths());
		this.difference.setRecovered(this.recovered - diff.getRecovered());
		this.difference.setActive(this.active - diff.getActive());
	}

	public void addStats(CovidData data) {
		this.confirmed = this.confirmed + (data.getConfirmed() == null ? 0 : data.getConfirmed());
		this.deaths = this.deaths + (data.getDeaths() == null ? 0 : data.getDeaths());
		this.recovered = this.recovered + (data.getRecovered() == null ? 0 : data.getRecovered());
		this.active = this.active + (data.getActive() == null ? 0 : data.getActive());
	}

	public Statistics() {

	}

	public Statistics(String location) {
		super(location);
	}

	public Statistics(CovidData data, String location) {
		super(location);
		this.addStats(data);
	}

	@Override
	public int compareTo(Statistics o) {
		return (int) (o.getConfirmed() - this.confirmed);
	}
}
//package com.covid.springboot.model;
//
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
//public class Statistics extends Country implements Comparable<Statistics> {
//
//	private Integer[] confirmed = { 0, 0 };
//
//	private Integer[] deaths = { 0, 0 };
//
//	private Integer[] recovered = { 0, 0 };
//
//	private Integer[] active = { 0, 0 };
//
//	private Float incidenceRate;
//
//	private Float fatalityRatio;
//
//	public void updateDifference(Statistics diff) {
//		this.confirmed[1] = this.confirmed[0] + diff.getConfirmed()[0];
//		this.deaths[1] = this.deaths[0] + diff.getDeaths()[0];
//		this.recovered[1] = this.recovered[0] + diff.getRecovered()[0];
//		this.active[1] = this.active[0] + diff.getActive()[0];
//	}
//
//	public void addStats(Integer confirmed, Integer deaths, Integer recovered, Integer active) {
//		this.confirmed[0] = this.confirmed[0] + confirmed;
//		this.deaths[0] = this.deaths[0] + deaths;
//		this.recovered[0] = this.recovered[0] + recovered;
//		this.active[0] = this.active[0] + active;
//	}
//
//	public void addStats(CovidData data) {
//		this.confirmed[0] = this.confirmed[0] + (data.getConfirmed() == null ? 0 : data.getConfirmed());
//		this.deaths[0] = this.deaths[0] + (data.getDeaths() == null ? 0 : data.getDeaths());
//		this.recovered[0] = this.recovered[0] + (data.getRecovered() == null ? 0 : data.getRecovered());
//		this.active[0] = this.active[0] + (data.getActive() == null ? 0 : data.getActive());
//	}
//
//	public Statistics() {
//
//	}
//
//	public Statistics(String location) {
//		super(location);
//	}
//
//	public Statistics(CovidData data, String location) {
//		super(location);
//		this.addStats(data);
//	}
//
//	@Override
//	public int compareTo(Statistics o) {
//		return o.getConfirmed()[0] - this.confirmed[0];
//	}
//}
