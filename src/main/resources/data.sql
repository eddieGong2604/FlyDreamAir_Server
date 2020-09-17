INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
INSERT INTO flight(airline_name, arrive_airport, arrive_time, depart_airport, depart_time, flight_number) VALUES
('VNA','NOI BAI',TO_TIMESTAMP('10/05/2019 08:45', 'DD-MM-YYYY HH24:MI:SS'),'SYDNEY',TO_TIMESTAMP('10/05/2019 08:45', 'DD-MM-YYYY HH24:MI:SS'),'VN787');
INSERT INTO seating(availability, price, seating_class, flight_flight_id) VALUES
(30,1000,'BUSINESS',1);
INSERT INTO seating(availability, price, seating_class, flight_flight_id) VALUES
(303,500,'ECONOMY',1);

INSERT INTO flight(airline_name, arrive_airport, arrive_time, depart_airport, depart_time, flight_number) VALUES
('QANTAS','BRISBANE',TO_TIMESTAMP('10/05/2019 08:45', 'DD-MM-YYYY HH24:MI:SS'),'SYDNEY',TO_TIMESTAMP('10/05/2019 08:45', 'DD-MM-YYYY HH24:MI:SS'),'QA777');
INSERT INTO seating(availability, price, seating_class, flight_flight_id) VALUES
(30,600,'BUSINESS',2);
INSERT INTO seating(availability, price, seating_class, flight_flight_id) VALUES
(303,150,'ECONOMY',2);
INSERT INTO flight(airline_name, arrive_airport, arrive_time, depart_airport, depart_time, flight_number) VALUES
('JESTAR','PERTH',TO_TIMESTAMP('10/05/2019 08:45', 'DD-MM-YYYY HH24:MI:SS'),'DARWIN',TO_TIMESTAMP('10/05/2019 08:45', 'DD-MM-YYYY HH24:MI:SS'),'JS942');
INSERT INTO seating(availability, price, seating_class, flight_flight_id) VALUES
(30,400,'BUSINESS',3);
INSERT INTO seating(availability, price, seating_class, flight_flight_id) VALUES
(303,200,'ECONOMY',3);