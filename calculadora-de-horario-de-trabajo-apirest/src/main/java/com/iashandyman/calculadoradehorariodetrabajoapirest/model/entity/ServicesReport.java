package com.iashandyman.calculadoradehorariodetrabajoapirest.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.IsoFields;

@Entity
@Table(name = "service_reports")
public class ServicesReport implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Technician technician;

    @NotEmpty(message = "La identificación del técnico no debe estar vacía.")
    private String idTechnician;

    @Column()
    @NotEmpty(message = "La identificación del servicio no debe estar vacía.")
    private String idService;



    @Pattern(regexp = "[\\d]{4}[-][\\d]{2}[-][\\d]{2}[\\s][\\d]{2}[:][\\d]{2}[:][\\d]{2}",
            message = "El campo no debe estar vacío y debe tener el formato correspondiente, yyyy-MM-dd HH:mm:ss")
    private String startDate;


    @Pattern(regexp = "[\\d]{4}[-][\\d]{2}[-][\\d]{2}[\\s][\\d]{2}[:][\\d]{2}[:][\\d]{2}",
            message = "El campo no debe estar vacío y debe tener el formato correspondiente, AAAA-MM-dd HH:mm:ss")
    private String finishDate;

    private String week;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime createAt;

    @PrePersist
    public void prePersist(){
        this.createAt = LocalDateTime.now();
        this.week = numberOfWeek(startDate);
    }


//  Constructor Vacio ****************************************************************************************************************************************************************************************************************

    public ServicesReport() {
    }

//  Metodos setters and getters ******************************************************************************************************************************************************************************************************

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdTechnician() {
        return idTechnician;
    }

    public void setIdTechnician(String idTechnician) {
        this.idTechnician = idTechnician;
    }

    public String getIdService() {
        return idService;
    }

    public void setIdService(String idService) {
        this.idService = idService;
    }

    public Technician getTechnician() {
        return technician;
    }

    public void setTechnician(Technician technician) {
        this.technician = technician;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public LocalDate dates(String dat){
        String[] date = dat.split(" ");
        String[] date1 = date[0].split("-");
        Integer year = Integer.parseInt(date1[0]);
        Integer month = Integer.parseInt(date1[1]);
        Integer day = Integer.parseInt(date1[2]);

        return LocalDate.of(year, month, day);
    }

    public String numberOfWeek(String startDate){
        LocalDate da = dates(startDate);

        LocalDate localDate = LocalDate.of(da.getYear(), da.getMonth(), da.getDayOfMonth());
        Integer wkNumber = localDate.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        String weekNumber = wkNumber.toString();

        return weekNumber;
    }

    public String compareDates(){
        LocalDate startDat = dates(startDate);
        LocalDate finishDat = dates(finishDate);
        String message = startDat.isEqual(finishDat) ? "" :
                startDat.isBefore(finishDat) ? "" : "La fecha de inicio del servicio no puede ser mayor a la fecha final.";
        return message;
    }

    private static final long serialVersionUID = 1L;

}
