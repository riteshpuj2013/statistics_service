# statistics_service
Statistics Service maintains realtime statistics of transactions executed in last 60 seconds.

## Build
mvn clean install

## Run
mvn spring-boot:run

### POST /transaction
    curl -sv -H "Content-type: application/json" -d '{
            "amount" : 1000,
            "timestamp": 1525180773653
    }' 'localhost:8080/transactions/'

### GET /statistics
    curl -sv -XGET 'localhost:8080/statistics/' | json_pp <br>
    
    Output:
    {
       "min" : "Infinity",
       "count" : 0,
       "max" : "-Infinity",
       "sum" : 0,
       "avg" : 0
    }


