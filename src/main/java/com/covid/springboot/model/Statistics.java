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
