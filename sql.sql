create database simpledb;
use simpledb;
Create Table specialist(
IDNum int primary key,
SName varchar(30),
phoneNumber varchar(11),
speciality varchar(30),
salaryPerApp double);
insert into specialist values (302001981,'Stephen Strange','0599876543','Oral and Maxillofacial Surgeon',1650);
insert into specialist values (302678900,'Loki Laufeyson','0569112321','Prosthodontic',1400);
insert into specialist values (303897123,'Wanda Maximoff','0569097456','Periodontist',1280);
insert into specialist values (304567890,'Peter Parker','0599456123','Endodontist',800);
select * from specialist;
drop table patient;
create table patient(
patientID_num int  not null Auto_increment primary key,
patientName varchar(30) not null unique, 
contact varchar(11),
p_age int,
medical_record varchar(100),
p_password varchar(100));
insert into patient values (null,"Steve Rogers","0599345678","30", "Nothing","america");
insert into patient values ( null,"Tony Stark","0569123765","47",  "Nothing","money");
insert into patient values (null, "Natasha Romanoff","0569876543","35",  "Nothing","russia");
insert into patient values (null, "Thor Odinson","0599000123","1000", "Nothing","killLoki");
insert into patient values (null, "Bruce Banner","0596890011","49", "Nothing","hulk");
insert into patient values (null, "Clint Barton","0596890011","39", "Nothing","hawkeye");
select * from doctors;

create table Doctors(
ID_Number int primary key,
Doctor_Name varchar(30),
specialty varchar(30),
salary int,
phoneNumber varchar(11));
drop table doctors;
insert into Doctors values(456734567,"Mohammad Ali","Endodontist",3000,0599123456);
insert into Doctors values(123456789,"Mustafa Mohammad","Oral and Maxillofacial Surgeon",3500,0569333456);
insert into Doctors values(987654321,"Areej Qadi","Orthodontist ",5000,0599567234);
insert into Doctors values(124578345,"Mariam Naji","Periodontist ",4500,0569987654);
 
create table doctorUser(
username varchar(50) primary key,
password1 varchar(30));

insert into doctorUser values("marvelDentalClinic.user","marvelDentalClinic.password");

create table teeth( 
patientID_num int  not null Auto_increment,
patientName varchar(30) not null,
tooth_number int,
tooth_condition 
varchar(30),
tooth_treatment varchar(30), 
Cost int,
primary key(patientID_num,tooth_number),
foreign key (patientID_num) references patient(patientID_num) on delete cascade ,
foreign key (patientName) references patient(patientName)
); 
select * from teeth;
insert into teeth values 
(1, "Steve Rogers", 3, "tooth decay", "fillings", 400),
(3, "Natasha Romanoff", 6, "Tooth Sensitivity", "Desensitizing toothpaste", 100),
(4, "Thor Odinson", 23, "Stained Teeth", "Veneers", 1000), 
(6, "Clint Barton", 5, "Cavities", "Tooth extraction", 500);
drop table teeth;
insert into teeth values (1, "Steve Rogers", 7, "Tooth Sensitivity", "Desensitizing toothpaste", 400);
insert into teeth values (1, "Steve Rogers", 20, "Stained Teeth", "Veneers", 1200);
insert into teeth values (2, "Tony Stark", 7, "Tooth Sensitivity", "Desensitizing toothpaste", 400);

update teeth set Cost =1500 where patientID_num = 1;

update Teeth set Cost= 500 where patientID_num = 1 and tooth_number = 3;

create table DentalLabs (
Name varchar(30),
Contact_Num varchar(30), 
Address varchar(30), 
DateSent varchar(30), 
ExpectedDuration varchar(30), 
Cost int, 
primary key (Name)); 

drop table dentalLabs;
insert into DentalLabs values ("Al-Awartani", "0599674555", "Ramallah", "1/1/2020", "1 month", 100);

insert into DentalLabs values 
("Al-Awartani", "0599674555", "Ramallah", "01/01/2020", "1 month", 100),
("DDS", "0599243666", "Nablus", "30/02/2020", "2 weeks", 500), 
("Smart Dent", "0568765292", "Ramallah", "05/12/2020", "5 days", 1500),
("SDL", "0598382321", "Tulkarm","06/10/2020", "15 days", 200);

-- Normalized  
create table services( 
service_name varchar(30) primary key,
contact varchar(30), 
address varchar(30));

-- insert into services values ("Raymond Polt","New york");
-- insert into services values ("Jessica Day","California");
  
-- Normalized
create table appoint_needs_spec(
DID int,
appointment_date date, 
Timing time,
ID_Num int,
primary key ( ID_Num), 
foreign key (ID_Num) references specialist(IDNum));
-- Normalized
create table doctor_has_appointment( 
DID int,
PID int,  
app_date date, 
Timing time,
SID int,
primary key (DID,Timing,app_date),
foreign key (DID) references Doctors(ID_number),
foreign key (PID) references patient(patientID_num),
foreign key (SID) references specialist(IDnum)
);
insert into doctor_has_appointment values (401123321, 2 ,'2020-02-01',"3:00", 302001981);
insert into doctor_has_appointment  values (401234567, 1 ,'2005-05-20',"4:30", 304567890);
insert into doctor_has_appointment  values (401234567, 1 ,'2020-06-20',"3:30", 302001981);
insert into doctor_has_appointment  values (401123321, 1 ,'2021-01-23',"2:00", 302678900);
 update doctor_has_appointment set DID =401123321 where DID = 401234567 AND app_date= '2021-01-23'  AND timing="2:00";
 select * from doctor_has_appointment;
create table patient_pays_payment(
patientID_num int,
pvalue real,
value_in_NIS real,
currency varchar(30), 
method varchar(30), 
date_of_payment date,
primary key(patientID_num,date_of_payment),
foreign key (patientID_num) references patient(patientID_num));
insert into patient_pays_payment values (1,2300,7590,"USD","cash",'2020-05-11');
insert into patient_pays_payment values (1,2300,7590,"USD","cash",'2020-05-11');
insert into patient_pays_payment values (2,40,1176, "JD","cash",'2021-03-20');
select * from patient_pays_payment;
create table debts_for_services( 
service_name varchar(30), 
pvalue real,
value_in_NIS real,
currency varchar(30), 
method varchar(30), 
date_of_payment date,
primary key(service_name,date_of_payment),
foreign key (service_name) references services(service_name));
drop table patient_pays_payment;
-- insert into debts_for_services values ("Jessica Day",2300,"$","cash",'2020-05-11');
-- insert into debts_for_services values ("Raymond Polt",400, "dinar","cash",'2021-03-20');

create table debts_for_specialist( 
ID_num int,
pvalue real,
value_in_NIS real,
currency varchar(30), 
method varchar(30), 
date_of_payment date,
primary key(ID_num,date_of_payment),
foreign key (ID_num) references specialist(IDNum));

-- insert into debts_for_specialist values (44,"Nick",2300,"$","cash",'2020-05-11');
-- insert into debts_for_specialist values (99,"Winston",400, "dinar","cash",'2021-03-20');
select app_date,timing, DID, SID from doctor_has_appointment where PID =1;

select * from dentallabs;