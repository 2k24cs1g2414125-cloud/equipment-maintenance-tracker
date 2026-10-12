# Equipment Maintenance & Service Tracker

A Spring Boot REST API to track industrial equipment (engines, generators), log their service history and find equipment that is due for maintenance.

## Features
- Add, view, update and delete equipment
- Log a service record for any equipment (updates its last service date)
- View the full service history of an equipment
- Get a list of equipment due for service in the next N days

## Tech Stack
Java 17, Spring Boot, Spring Data JPA (Hibernate), H2 database, Maven

## How to run
1. Install JDK 17 or above and Maven
2. Clone the repo and open a terminal in the project folder
3. Run: `mvn spring-boot:run`
4. API runs at http://localhost:8080

## API Endpoints
| Method | Endpoint | Description |
| --- | --- | --- |
| POST | /api/equipment | Add equipment |
| GET | /api/equipment | List all equipment |
| GET | /api/equipment/{id} | Get one equipment |
| PUT | /api/equipment/{id} | Update equipment |
| DELETE | /api/equipment/{id} | Delete equipment |
| GET | /api/equipment/due?days=30 | Equipment due for service in next N days |
| POST | /api/equipment/{id}/services | Log a service record |
| GET | /api/equipment/{id}/services | View service history |

## Sample request
POST /api/equipment
```json
{
  "name": "Diesel Generator 500kVA",
  "type": "generator",
  "serialNumber": "DG-1001",
  "nextServiceDate": "2026-11-15"
}
```

## Future work
- MySQL database
- JWT authentication with admin / technician roles
