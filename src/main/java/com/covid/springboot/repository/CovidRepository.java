package com.covid.springboot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.covid.springboot.model.CovidData;

public interface CovidRepository extends MongoRepository<CovidData, String>, CovidRepositoryCustomFunctions {
}
