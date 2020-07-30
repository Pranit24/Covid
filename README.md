## Covid Live Data
This project is made using Spring Boot, ReactJS and MongoDB

## Spring Boot Backend
Gets the latest data from John Hopkins Covid-19 database and provides a RESTful API to read the data. This data is stored in a MongoDB collection for each day and scheduled to run at 5:20 GMT 0:00 everyday to fetch new data from the database. 

### API
1. 
```
/api/data
```
Gets the latest data of every country along with the difference between yesterday's data to show the increase/decrease between days
Is ordered in increasing order of the confirmed cases
```
{
    "_id": int,
    "country": String(Country),
    "confirmed": double,
    "deaths": double,
    "recovered": double,
    "active": double,
    "incidenceRate": double,
    "fatalityRatio": double,
    "difference": {
	    "confirmed": double,
	    "deaths": double,
	    "recovered": double,
	    "active": double,
	    "incidenceRate": double,
	    "fatalityRatio": double,
    }
  },
```

2. 
```
/api/data/{country}
```
Gets the latest data of each state of the country provided as path variable
Is ordered in increasing order of the confirmed cases
```
{
    "_id": int,
    "country": String(State),
    "confirmed": double,
    "deaths": double,
    "recovered": double,
    "active": double,
    "incidenceRate": double,
    "fatalityRatio": double,
    "difference": {
	    "confirmed": double,
	    "deaths": double,
	    "recovered": double,
	    "active": double,
	    "incidenceRate": double,
	    "fatalityRatio": double,
    }
  },
 ```

## ReactJs Frontend
Fetches the latest data and the increase in cases between today and yesterday of every country in different views
Country can be double clicked in list view to view the cases per state

#### Views 
1. List View
![Error displaying Image](https://github.com/Pranit24/Covid/blob/master/Images/ListView.png?raw=true)
 
2. Graph View
![Error displaying Image](https://github.com/Pranit24/Covid/blob/master/Images/GraphView.PNG?raw=true)

3. Map View
![Error displaying Image](https://github.com/Pranit24/Covid/blob/master/Images/MapView.PNG?raw=true)


### Run app

```
mvn spring-boot:run
```

### Build app
```
mvn clean package spring-boot:repackage
```
[John Hopkins Data](https://github.com/CSSEGISandData/COVID-19/tree/master/csse_covid_19_data)
