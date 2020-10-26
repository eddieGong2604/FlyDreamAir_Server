INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
INSERT INTO FLIGHT(AIRLINE_NAME, ARRIVE_AIRPORT, ARRIVE_TIME, DEPART_AIRPORT, DEPART_TIME, FLIGHT_NUMBER) VALUES
("VNA","NOI BAI",STR_TO_DATE('10/05/2019 08:45', '%d/%m/%Y %H:%i'),"SYDNEY",STR_TO_DATE('09/05/2019 23:45', '%d/%m/%Y %H:%i'),"VN787");
INSERT INTO SEATING(AVAILABILITY, PRICE, SEATING_CLASS, FLIGHT_FLIGHT_ID) VALUES
(30,1000,"BUSINESS",1);
INSERT INTO SEATING(AVAILABILITY, PRICE, SEATING_CLASS, FLIGHT_FLIGHT_ID) VALUES
(303,500,"ECONOMY",1);

INSERT INTO FLIGHT(AIRLINE_NAME, ARRIVE_AIRPORT, ARRIVE_TIME, DEPART_AIRPORT, DEPART_TIME, FLIGHT_NUMBER) VALUES
("QANTAS","BRISBANE",STR_TO_DATE('10/05/2019 17:45', '%d/%m/%Y %H:%i'),"SYDNEY",STR_TO_DATE('10/05/2019 15:45', '%d/%m/%Y %H:%i'),"QA777");
INSERT INTO SEATING(AVAILABILITY, PRICE, SEATING_CLASS, FLIGHT_FLIGHT_ID) VALUES
(30,600,"BUSINESS",2);
INSERT INTO SEATING(AVAILABILITY, PRICE, SEATING_CLASS, FLIGHT_FLIGHT_ID) VALUES
(303,150,"ECONOMY",2);
INSERT INTO FLIGHT(AIRLINE_NAME, ARRIVE_AIRPORT, ARRIVE_TIME, DEPART_AIRPORT, DEPART_TIME, FLIGHT_NUMBER) VALUES
("JESTAR","PERTH",STR_TO_DATE('11/05/2019 20:45', '%d/%m/%Y %H:%i'),"DARWIN",STR_TO_DATE('11/05/2019 16:45', '%d/%m/%Y %H:%i'),"JS942");
INSERT INTO SEATING(AVAILABILITY, PRICE, SEATING_CLASS, FLIGHT_FLIGHT_ID) VALUES
(30,400,"BUSINESS",3);
INSERT INTO SEATING(AVAILABILITY, PRICE, SEATING_CLASS, FLIGHT_FLIGHT_ID) VALUES
(303,200,"ECONOMY",3);