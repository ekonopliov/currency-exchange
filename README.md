# Currency exchange API example
Reads data from .csv, exchanges, validates

# Build and run

**Maven and local** At root directory:<br>
Preconditions: Maven 3.6.3+, OpenJDK11 (and all PATHs)<br><br>
At root directory:<br>
- `mvn clean install`<br>
- `mvn spring-boot:run`<br>
<br>

**Maven and Docker container**<br>
Preconditions: Docker up and running, Maven 3.6.3+, OpenJDK11 (and all PATHs)<br><br>
- uncomment pom.xml build Spotify and dependency plugins<br>
At root directory:<br>
- `mvn clean install`<br>
- `docker-compose up -d`<br>

# Requests
By default application exposes on :8888

URI|request|response|description
---|---|---|---
/exchange|POST `{from":"USD","to":"ETH","amount":10.50}`|200, `{"exchange_amount" : 0.007796718290392317, "exchange_rate" : 846.510000000000000000, "from_currency" : { "name" : "USD", "rate" : 0.809552722 }, "to_currency" : { "name" : "ETH", "rate" : 685.29447470022 }, "request" : { "from" : "USD", "to" : "ETH", "amount" : 6.6}, "timestamp" : "2020-09-13T16:40:34.856+00:00" }`| Exchange currency
/exchange/supportedCurrencies|GET|200, `[{ "name" : "EUR", "rate" : 1 }, { "name" : "USD", "rate" : 0.809552722 }, { "name" : "GBP", "rate" : 1.126695 }, { "name" : "BTC", "rate" : 6977.0896569209 }, { "name" : "ETH", "rate" : 685.29447470022 }, { "name" : "FKE", "rate" : 0.025 }]`| Get all supported currencies