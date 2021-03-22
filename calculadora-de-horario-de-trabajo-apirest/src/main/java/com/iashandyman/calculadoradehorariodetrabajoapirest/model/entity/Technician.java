package com.iashandyman.calculadoradehorariodetrabajoapirest.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "technicians")
public class Technician implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @OneToMany(mappedBy = "technician", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ServicesReport> reports;

    @OneToMany(mappedBy = "technician",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<HoursPerWeek> hoursPerWeek;

    public Technician() {
        this.reports = new ArrayList<>();
        this.hoursPerWeek = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<ServicesReport> getReports() {
        return reports;
    }

    public void setReports(List<ServicesReport> reports) {
        this.reports = reports;
    }

    public void addServiceReport(ServicesReport serviceRe){
        reports.add(serviceRe);
    }

    public List<HoursPerWeek> getHoursPerWeek() {
        return hoursPerWeek;
    }

    public void setHoursPerWeek(List<HoursPerWeek> hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
    }

    public List<ServicesReport> reportsTechnician(String weekNumber){
        List<ServicesReport> rports = new ArrayList<>();
        for (ServicesReport report: reports){
            if (report.getWeek().equals(weekNumber)){
                rports.add(report);
            }
        }
        return rports;
    }

    private static final long serialVersionUID = 1L;
}
