insert into technicians(first_name, last_name) values ("Jhony", "Graciano");
insert into technicians(first_name, last_name) values ("Lina", "Peña");
insert into technicians(first_name, last_name) values ("Ana", "Soto");
insert into technicians(first_name, last_name) values ("Pedro", "Fulano");
insert into technicians(first_name, last_name) values ("Andrés", "Perano");
insert into technicians(first_name, last_name) values ("Santiago", "Sultano");


insert into service_reports(technician_id, id_technician, id_service, start_date, finish_date, week, create_at) values (3, '3', '87567', '2019-05-03 07:30:00', '2019-05-03 19:45:00', '32', '2020-08-14 16:30:40');
insert into service_reports(technician_id, id_technician, id_service, start_date, finish_date, week, create_at) values (3, '3', '56787', '2019-05-04 18:00:00', '2019-05-04 23:30:00', '32', '2020-08-14 16:30:40');
insert into service_reports(technician_id, id_technician, id_service, start_date, finish_date, week, create_at) values (3,'3', '18283', '2019-05-05 00:30:01', '2019-05-05 06:30:01', '32', '2020-08-14 16:30:40');
insert into service_reports(technician_id, id_technician, id_service, start_date, finish_date, week, create_at) values (2, '2', '876567', '2019-05-05 00:30:01', '2019-05-05 06:30:01', '32', '2020-08-14 16:30:40');
insert into service_reports(technician_id, id_technician, id_service, start_date, finish_date, week, create_at) values (2, '2', '7655677', '2019-05-06 07:00:01', '2019-05-06 20:00:01', '32', '2020-08-14 16:30:40');


insert into hours_per_week(extra_hours, night_hours, night_overtime, normal_hours, sunday_hours, sunday_overtime, total_time, week, technician_id ) values ('0', '3.5', '0', '14.25', '0', '6', '23.75', '32', 3);
insert into hours_per_week(extra_hours, night_hours, night_overtime, normal_hours, sunday_hours, sunday_overtime, total_time, week, technician_id ) values ('0', '3.3', '0', '10.25', '0', '0', '13.55', '32', 2);
