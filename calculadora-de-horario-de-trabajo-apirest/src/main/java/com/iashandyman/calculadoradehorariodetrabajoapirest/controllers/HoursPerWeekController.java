package com.iashandyman.calculadoradehorariodetrabajoapirest.controllers;


import com.iashandyman.calculadoradehorariodetrabajoapirest.model.entity.HoursPerWeek;
import com.iashandyman.calculadoradehorariodetrabajoapirest.model.entity.ServicesReport;
import com.iashandyman.calculadoradehorariodetrabajoapirest.model.entity.Technician;
import com.iashandyman.calculadoradehorariodetrabajoapirest.services.IHoursPerWeekServices;
import com.iashandyman.calculadoradehorariodetrabajoapirest.services.ITechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping({"/api", "/"})
public class HoursPerWeekController {

    private ITechnicianService technicianService;
    private IHoursPerWeekServices hoursPerWeekServices;

    @Autowired
    public HoursPerWeekController(ITechnicianService technicianService, IHoursPerWeekServices hoursPerWeekServices) {
        this.technicianService = technicianService;
        this.hoursPerWeekServices = hoursPerWeekServices;
    }

    @GetMapping("/hoursperweek/{id}/{weekNumber}")
    public ResponseEntity<?> hoursPerWeek(@PathVariable Long id, @PathVariable String weekNumber){

        Map<String, Object> response = new HashMap<>();
        Technician technician;
        HoursPerWeek hoursPerWeek;
        List<ServicesReport> reports = null;

        try {
            technician = technicianService.findById(id);

            if (technician == null){
                response.put("message", "El tecnico con el ID: " + id + " no existe en la base de datos!");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            reports = technician.reportsTechnician(weekNumber);

            hoursPerWeek = hoursPerWeekServices.workTime(reports, technician);

        }catch (DataAccessException e){
            response.put("message", "Error al realizar el insert en la base de datos!");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }catch (NumberFormatException e){
            response.put("message", "Por favor revice la entrada del campo debe ser un numero entero!");
            response.put("error", e.getMessage() + e.getCause());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        response.put("hoursPerweek", hoursPerWeek);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
