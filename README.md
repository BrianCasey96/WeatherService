# Weather Service Rest API 

Setting up
------------
	- git clone this project 
	- do a gradle clean and build:
     ./gradlew clean
     ./gradlew build
	- to run from a terminal try './gradlew bootRun' or else run the program in IntelliJ/VSCode from the main class Application

## REST API Calls

### Register a sensor
    curl --location --request POST 'localhost:8080/registerSensor/{id}}' \
    --header 'Content-Type: application/json' \
    --data-raw '{ "country": "ireland", "city": "ennis" }'

### Get Sensor Info
    curl --location --request GET 'localhost:8080/getSensor/{id}}'

### Update Sensor Metrics
    curl --location --request POST 'localhost:8080/addSensorData/{id}' \
    --header 'Content-Type: application/json' \
    --data-raw '{ "temperature": 17.0, "humidity": 81.7 }'

### Query Sensor Metrics -
    Get most recent SensorData for all Sensors:
    curl --location --request GET 'localhost:8080/getSensors?ids=all'
    
    Get most recent SensorData for listed ids:
    curl --location --request GET 'localhost:8080/getSensors?ids={id},{id}'
    
    Get average SensorData for all Sensors:
    curl --location --request GET 'localhost:8080/getSensors?ids=all&&from=dd-MM-YYYY&to=dd-MM-YYYY

    Get average SensorData for listed ids:
    curl --location --request GET 'localhost:8080/getSensors?ids={id},{id}&&from=dd-MM-YYYY&to=dd-MM-YYYY
