package com.iashandyman.calculadoradehorariodetrabajoapirest.services;

import com.iashandyman.calculadoradehorariodetrabajoapirest.model.entity.ServicesReport;

import java.util.List;

public interface IServicesReportService {

    ServicesReport save(ServicesReport servicesReport);

    List<ServicesReport> findReportsByIdTechnicianAndNumberWeek(String id, String numberWeek);

}
