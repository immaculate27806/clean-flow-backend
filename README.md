# CleanFlow Backend

**Student:** MUTARUTWA Immaculate
**Student ID:** 27806
**Project:** CleanFlow
**Course:** INSY 8322 Web Technology and Internet, Faculty of Information Technology, AUCA
**Lecturer:** Patrick DUSHIMIMANA

CleanFlow replaces the notebooks and phone calls a laundry business uses to follow
customer orders. A customer places a laundry order, an attendant working at a station
handles it, and the order moves from PENDING to WASHING and ends DELIVERED or CANCELLED.
Once an order is delivered, the attendant records delivery notes for it.

Built with Spring Boot, Spring Data JPA and PostgreSQL.

## Technologies

- Java 17
- Spring Boot 4.1.2 (Spring Web, Spring Data JPA)
- PostgreSQL
- Maven (the Maven wrapper is included, nothing to install)

## How to run the API

1. Install Java 17 and PostgreSQL.
2. In pgAdmin, create an empty database named `cleanflow`.
3. Open `src/main/resources/application.properties` and put your own PostgreSQL
   username and password in these two lines:
   ```
   spring.datasource.username=postgres
   spring.datasource.password=YOUR_PASSWORD
   ```
4. Open a terminal in the project folder (the one that contains `pom.xml`) and run:
   ```
   .\mvnw spring-boot:run
   ```
   (on Linux or Mac: `./mvnw spring-boot:run`)
5. Wait for the line `Started CleanFlowApplication`. The API is now running at
   `http://localhost:8080`.
6. The tables are created automatically by Hibernate (`ddl-auto=update`).
7. Import `clean-flow-midterm.postman_collection.json` into Postman to test the endpoints.

## Entities

ServiceType, Station, Attendant, Customer, LaundryOrder (with the enum OrderStatus)
and DeliveryNote. Every entity has a UUID id.

## Endpoints

### CRUD (A2)

For each of `service-types`, `stations`, `attendants`, `customers` and `laundry-orders`:

| Method | Path |
|---|---|
| POST | `/api/{path}/save` |
| GET | `/api/{path}/all` |
| GET | `/api/{path}/{id}` |
| PUT | `/api/{path}/update/{id}` |
| DELETE | `/api/{path}/delete/{id}` |

### Other requirements

| ID | Method | Endpoint |
|---|---|---|
| B1 | GET | `/api/customers/by-last-name?lastName=mukamana` |
| B2 | GET | `/api/laundry-orders/by-status?status=PENDING` |
| B3 | GET | `/api/laundry-orders/between?start=2026-10-01&end=2026-10-31` |
| C1 | GET | `/api/attendants/by-service-type?name=dry cleaning` |
| C2 | GET | `/api/stations/free` |
| C3 | GET | `/api/service-types/unused` |
| C4 | GET | `/api/customers/of-attendant/{attendantId}` |
| D1 | POST | `/api/laundry-orders/save` |
| D2 | PATCH | `/api/laundry-orders/{id}/status?status=WASHING` |
| D3 | PUT | `/api/attendants/{attendantId}/service-types/{serviceTypeId}` |
| D4 | PATCH | `/api/attendants/{attendantId}/station?stationNumber=4` |
| E1 | POST | `/api/delivery-notes/save?laundryOrderId=...` |
| E2 | GET | `/api/delivery-notes/customer/{phone}` |
| E3 | DELETE | `/api/delivery-notes/delete/{id}` and `/api/laundry-orders/delete/{id}` |
| F1 | GET | `/api/laundry-orders/stats/by-status` |
| F2 | PATCH | `/api/laundry-orders/cancel-day?attendantId=...&date=2026-10-20` |

### Bonus

| Method | Endpoint |
|---|---|
| GET | `/api/dashboard/summary` |
| GET | `/api/laundry-orders/page?page=0&size=5&sort=orderDate,desc` |

## Business rules

- A new laundry order cannot have a date in the past (400) and cannot be given to an
  attendant who already has a non-cancelled order on that date (409). It always starts
  as PENDING.
- The status of a laundry order only changes through the PATCH status endpoint:
  PENDING goes to WASHING or CANCELLED, WASHING goes to DELIVERED or CANCELLED, and
  DELIVERED and CANCELLED are final.
- A delivery note can only be written for a DELIVERED laundry order.
- A laundry order that has delivery notes cannot be deleted.
- A station can only be given to an attendant if no other attendant holds it.

## Project structure

```
src/main/java/com/auca/cleanflow
  model         entities and the OrderStatus enum
  repository    Spring Data JPA repositories (derived queries and JPQL)
  service       business rules
  controller    REST endpoints
```