package com.iashandyman.calculadoradehorariodetrabajoapirest.model.dao;

import com.iashandyman.calculadoradehorariodetrabajoapirest.model.entity.HoursPerWeek;
import org.springframework.data.repository.CrudRepository;


public interface IHoursPerWeekDao extends CrudRepository<HoursPerWeek, Long> {

    HoursPerWeek findByTechnician_IdAndWeek(Long id, String Week);

}
