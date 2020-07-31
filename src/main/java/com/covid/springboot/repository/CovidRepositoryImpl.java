package com.covid.springboot.repository;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.covid.springboot.model.CovidData;

/**
 * @author Pranit
 *
 *         Custom functions to query the database
 */
public class CovidRepositoryImpl implements CovidRepositoryCustomFunctions {

	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * 
	 * Create a new collection if it doesn't exist
	 * 
	 * @param collectionName
	 */
	private void collectionExists(String collectionName) {
		if (!mongoTemplate.getCollectionNames().contains(collectionName)) {
			mongoTemplate.createCollection(collectionName);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean checkCollection(String collectionName) {
		if (!mongoTemplate.getCollectionNames().contains(collectionName)) {
			return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Create a new collection for new date
	 */
	@Override
	public void save(CovidData data, String collectionName) {
		collectionExists(collectionName);
		mongoTemplate.save(data, collectionName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CovidData> findAll(String collectionName) {
		return mongoTemplate.findAll(CovidData.class, collectionName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CovidData> findByCountry(String country, String collectionName) {
		Query query = new Query(Criteria.where("countryRegion")
				.regex(Pattern.compile("\\b(" + country + ")\\b", Pattern.CASE_INSENSITIVE)));
		return mongoTemplate.find(query, CovidData.class, collectionName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CovidData> findByState(String country, String state, String collectionName) {

		Query query = new Query(Criteria.where("countryRegion")
				.regex(Pattern.compile("\\b(" + country + ")\\b", Pattern.CASE_INSENSITIVE))
				.andOperator(Criteria.where("provinceState")
						.regex(Pattern.compile("\\b(" + state + ")\\b", Pattern.CASE_INSENSITIVE))));
		return mongoTemplate.find(query, CovidData.class, collectionName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CovidData> findByRegion(String region, String collectionName) {
		Query query = new Query(Criteria.where("countryRegion")
				.regex(Pattern.compile("\\b()\\b", Pattern.CASE_INSENSITIVE)).andOperator(Criteria.where("Admin2")
						.regex(Pattern.compile("\\b(" + region + ")\\b", Pattern.CASE_INSENSITIVE))));
		return mongoTemplate.find(query, CovidData.class, collectionName);
	}

}