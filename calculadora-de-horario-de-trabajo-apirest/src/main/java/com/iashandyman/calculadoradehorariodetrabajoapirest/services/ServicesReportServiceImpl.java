package com.iashandyman.calculadoradehorariodetrabajoapirest.services;

import com.iashandyman.calculadoradehorariodetrabajoapirest.model.dao.IServicesReportDao;
import com.iashandyman.calculadoradehorariodetrabajoapirest.model.entity.ServicesReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ServicesReportServiceImpl implements IServicesReportService {

    @Autowired
    private IServicesReportDao servicesReport;


    @Override
    public ServicesReport save(ServicesReport servicesR) {
        return servicesReport.save(servicesR);
    }

    @Override
    public List<ServicesReport> findReportsByIdTechnicianAndNumberWeek(String id, String numberWeek) {
        return servicesReport.findByIdTechnicianAndWeek(id, numberWeek);
    }


}
