package com.iashandyman.calculadoradehorariodetrabajoapirest.model.dao;

import com.iashandyman.calculadoradehorariodetrabajoapirest.model.entity.ServicesReport;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IServicesReportDao extends CrudRepository<ServicesReport, Long> {

    List<ServicesReport> findByIdTechnicianAndWeek(String id, String numberWeek);


}
