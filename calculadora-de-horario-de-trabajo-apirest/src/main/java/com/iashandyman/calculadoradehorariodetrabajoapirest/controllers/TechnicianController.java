package com.iashandyman.calculadoradehorariodetrabajoapirest.controllers;

import com.iashandyman.calculadoradehorariodetrabajoapirest.model.entity.ServicesReport;
import com.iashandyman.calculadoradehorariodetrabajoapirest.model.entity.Technician;
import com.iashandyman.calculadoradehorariodetrabajoapirest.services.ITechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*@CrossOrigin(origins = "http://localhost:4200")*/
@RestController
@RequestMapping({"/api", "/"})
public class TechnicianController {

    @Autowired
    private ITechnicianService technicianService;

    /**
     * Consulta la base de datos para obtener la información de todos los tecnicos.
     * @return la lista de los tecnicos de la empresa.
     */
    @GetMapping("/technicians")
    public List<Technician> index(){
        return technicianService.findAll();
    }

    /**
     * Consulta la base de datos para inforamación de un solo técnico.
     * @param id del técnico.
     * @return los datos del técnico.
     */
    @GetMapping("/technicians/{id}")
    public ResponseEntity<?> showTechnicianById(@PathVariable Long id){

        Technician technician = null;

        Map<String, Object> response = new HashMap<>();

        try {
            technician = technicianService.findById(id);
            if(technician == null){
                response.put("message", "El Técnico con ID: " + id.toString() + " no existe en la base de datos.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return new ResponseEntity<>(technician, HttpStatus.OK);
    }

    /**
     * Persiste un técnico en la base de datos.
     * @param technician recibe un Objeto con los datos del técnico en formato json.
     * @return devuelve un objeto con los datos y mensajes en formato json.
     */
    @PostMapping("/technicians")
    public ResponseEntity<?> createTechnician(@RequestBody Technician technician){

        Technician technicianSaved = null;

        Map<String, Object> response = new HashMap<>();

        try {
            technicianSaved = technicianService.save(technician);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar el insert en la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "El técnico fue creado con exito!");
        response.put("technician", technicianSaved);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Actualiza un registro de un técnico en la base de datos.
     * @param technician objeto en formato json con los datos del técnico.
     * @param id identificacion del técnico.
     * @return un objeto json con los datos y mensajes.
     */
    @PutMapping("/technicians/{id}")
    public ResponseEntity<?> updateTechnician(@RequestBody Technician technician, @PathVariable Long id){

        Technician technicianFound = null, technicianUpdate = null;

        Map<String, Object> response = new HashMap<>();

        try {
            technicianFound = technicianService.findById(id);
            if(technician == null){
                response.put("message", "El Técnico con ID: " + id.toString() + " no existe en la base de datos.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta en la base de datos.");
            response.put("error", HttpStatus.NOT_FOUND);
        }

       try {
           technicianFound.setFirstName(technician.getFirstName());
           technicianFound.setLastName(technician.getLastName());
           technicianUpdate = technicianService.save(technicianFound);
       }catch (DataAccessException e){
           response.put("message", "El técnico con ID: " + technician.getId().toString() + " no se pudo actualizar en la base de datos");
           response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
           return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
       }

       response.put("message", "El técnico se ha sido actualizado con éxito!");
       response.put("technician", technicianUpdate);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Elimina los registros de un técnico en la base de datos por su id.
     * @param id identificacion del técnico en la base de datos.
     * @return un objeto en formato json con los mensajes correspondientes.
     */
    @DeleteMapping("/technicians/{id}")
    public ResponseEntity<?> dropTechnician(@PathVariable Long id){

        Map<String, Object> response = new HashMap<>();

        try {
            technicianService.deleteByid(id);
        }catch (DataAccessException e){
            response.put("mensaje", "El técnico con el ID: ".concat(id.toString()).concat(" no existe en la base de datos!"));
            response.put("error", e.getMessage().concat(": "). concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("mensaje", "El técnico ha sido eliminado con exito!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/technicians/reports/{id}")
    public ResponseEntity<?> servicesReports(@PathVariable Long id){

        Technician technician = null;
        List<ServicesReport> reports;
        Map<String, Object> response = new HashMap<>();

        try {
            technician = technicianService.findById(id);
            if (technician == null){
                response.put("message", "El cliente con el ID: " + id.toString() + " no existe en la base de datos!");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            reports = technician.getReports();
            if (reports.size() == 0){
                response.put("message", "El técnico con ID: " + id.toString() + " no tiene reportes de servicios");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("reports", reports);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }





}
