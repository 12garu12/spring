package com.iashandyman.calculadoradehorariodetrabajoapirest.controllers;


import com.iashandyman.calculadoradehorariodetrabajoapirest.model.entity.ServicesReport;
import com.iashandyman.calculadoradehorariodetrabajoapirest.model.entity.Technician;
import com.iashandyman.calculadoradehorariodetrabajoapirest.services.IHoursPerWeekServices;
import com.iashandyman.calculadoradehorariodetrabajoapirest.services.IServicesReportService;
import com.iashandyman.calculadoradehorariodetrabajoapirest.services.ITechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/*@CrossOrigin(origins = {"http://localhost:4200"})*/
@RestController
@RequestMapping("/api")
public class ServicesReportController {

    private IServicesReportService reportService;
    private ITechnicianService technicianService;
    private IHoursPerWeekServices hoursPerWeekServices;

    @Autowired
    public ServicesReportController(IServicesReportService reportService, ITechnicianService technicianService) {
        this.reportService = reportService;
        this.technicianService = technicianService;
    }

    @PostMapping("/reports")
    public ResponseEntity<?> createReport(@Valid @RequestBody ServicesReport servicesReport, BindingResult result){

        ServicesReport newReport = null;
        Technician technician = null;
        Long idTechnician;

        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()){
            List<String> errors = new ArrayList<>();

            result.getFieldErrors().forEach( err ->{
                errors.add(err.getDefaultMessage());
            });

            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            if (servicesReport.compareDates().length() > 0){
                response.put("error", servicesReport.compareDates());
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            idTechnician = Long.parseLong(servicesReport.getIdTechnician());

            technician = technicianService.findById(idTechnician);

            if (technician == null){
                response.put("message", "El tecnico con el ID: " + servicesReport.getIdTechnician() +
                        " no existe en la base de datos!");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            servicesReport.setTechnician(technician);

            newReport = reportService.save(servicesReport);

        }catch (NumberFormatException e){
            response.put("message", "Error el numero ingresado no es un entero!");
            response.put("error", e.getMessage() + ": " + e.getCause());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar el Insert en la base de datos!");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Reporte de servicios creado con exito!");
        response.put("reporte", newReport);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/reports/{idTechnician}/{numberWeek}")
    public ResponseEntity<?> findReportsForIdTechnicianAndWeekNumber(@PathVariable String idTechnician,
                                                                     @PathVariable String numberWeek){

        Map<String, Object> response = new HashMap<>();
        List<ServicesReport> reports;

        try {
            Integer idTech = Integer.parseInt(idTechnician);
            Integer weekNumber = Integer.parseInt(numberWeek);

            reports = reportService.findReportsByIdTechnicianAndNumberWeek(idTechnician, numberWeek);

        }catch (NumberFormatException e){
            response.put("error", "Por favor revise el campo de entrada debe ser un numero entero.");
            response.put("message", e.getMessage() + ": " + e.getCause());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }catch (DataAccessException e){
            response.put("error", "error al realizar la consulta en la base de datos.");
            response.put("message", e.getMessage().concat(": ").concat(e.getMostSpecificCause().toString()));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("reports", reports);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}