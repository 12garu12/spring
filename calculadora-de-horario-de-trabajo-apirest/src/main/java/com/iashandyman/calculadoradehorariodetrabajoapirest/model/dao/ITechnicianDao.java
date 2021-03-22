package com.iashandyman.calculadoradehorariodetrabajoapirest.model.dao;

import com.iashandyman.calculadoradehorariodetrabajoapirest.model.entity.Technician;
import org.springframework.data.repository.CrudRepository;

public interface ITechnicianDao extends CrudRepository<Technician, Long> {


}
