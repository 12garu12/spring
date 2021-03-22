package com.iashandyman.calculadoradehorariodetrabajoapirest.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "hours_per_week")
public class HoursPerWeek implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Technician technician;

    private String week;

    private Float normalHours;

    private Float nightHours;

    private Float sundayHours;

    private Float extraHours;

    private Float nightOvertime;

    private Float sundayOvertime;

    private Float totalTime;

    /* Constructor ****************************************************************************************************/

    public HoursPerWeek(){
        this.normalHours = 0f;
        this.nightHours= 0f;
        this.sundayHours = 0f;
        this.extraHours = 0f;
        this.nightOvertime = 0f;
        this.sundayOvertime = 0f;
        this.totalTime = 0f;
    }
    /***********************************************************************************************************/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Technician getTechnician() {
        return technician;
    }

    public void setTechnician(Technician technician) {
        this.technician = technician;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public Float getNormalHours() {
        return normalHours;
    }

    public void setNormalHours(Float normalHours) {
        this.normalHours = normalHours;
    }

    public Float getNightHours() {
        return nightHours;
    }

    public void setNightHours(Float nightHours) {
        this.nightHours = nightHours;
    }

    public Float getSundayHours() {
        return sundayHours;
    }

    public void setSundayHours(Float sundayHours) {
        this.sundayHours = sundayHours;
    }

    public Float getExtraHours() {
        return extraHours;
    }

    public void setExtraHours(Float extraHours) {
        this.extraHours = extraHours;
    }

    public Float getNightOvertime() {
        return nightOvertime;
    }

    public void setNightOvertime(Float nightOvertime) {
        this.nightOvertime = nightOvertime;
    }

    public Float getSundayOvertime() {
        return sundayOvertime;
    }

    public void setSundayOvertime(Float sundayOvertime) {
        this.sundayOvertime = sundayOvertime;
    }

    public Float getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Float totalTime) {
        this.totalTime = totalTime;
    }

    private static final Long SerialVersionUID = 1L;

}
