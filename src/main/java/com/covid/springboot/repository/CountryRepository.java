package com.covid.springboot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.covid.springboot.model.Country;

public interface CountryRepository extends MongoRepository<Country, String> {

}
