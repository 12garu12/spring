package com.iashandyman.calculadoradehorariodetrabajoapirest.services;


import com.iashandyman.calculadoradehorariodetrabajoapirest.model.entity.HoursPerWeek;
import com.iashandyman.calculadoradehorariodetrabajoapirest.model.entity.ServicesReport;
import com.iashandyman.calculadoradehorariodetrabajoapirest.model.entity.Technician;

import java.util.List;

public interface IHoursPerWeekServices {

    HoursPerWeek workTime(List<ServicesReport> reports, Technician technician);

}
