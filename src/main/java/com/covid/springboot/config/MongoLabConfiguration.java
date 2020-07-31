//package com.covid.springboot.config;
//
//import java.net.UnknownHostException;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.mongodb.MongoException;
//import com.mongodb.client.MongoClient;
//
//@Configuration
//public class MongoLabConfiguration {
//
//	@Bean
//	public DB getDb() throws UnknownHostException, MongoException {
//		String uri = "mongodb://user:password@id.mongolab.com:53178/db";
//		MongoClientURI mongoClientURI = new MongoClientURI(uri);
//		MongoClient mongoClient = new MongoClient(mongoClientURI);
//		DB db = mongoClient.getDB(mongoClientURI.getDatabase());
//		db.authenticate(mongoClientURI.getUsername(), mongoClientURI.getPassword());
//		return db;
//	}
//}