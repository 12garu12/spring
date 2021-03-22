package com.iashandyman.calculadoradehorariodetrabajoapirest.services;

import com.iashandyman.calculadoradehorariodetrabajoapirest.model.entity.ServicesReport;
import com.iashandyman.calculadoradehorariodetrabajoapirest.model.entity.Technician;

import java.util.List;

public interface ITechnicianService {

    List<Technician> findAll();

    Technician findById(Long id);

    Technician save(Technician technician);

    void deleteByid(Long id);

}
