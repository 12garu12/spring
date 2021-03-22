package com.iashandyman.calculadoradehorariodetrabajoapirest.services;

import com.iashandyman.calculadoradehorariodetrabajoapirest.model.dao.ITechnicianDao;
import com.iashandyman.calculadoradehorariodetrabajoapirest.model.entity.Technician;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnicianServiceImpl implements ITechnicianService {

    @Autowired
    private ITechnicianDao technicianDao;

    @Override
    public List<Technician> findAll() {
        return (List<Technician>) technicianDao.findAll();
    }

    @Override
    public Technician findById(Long id) {
        return technicianDao.findById(id).orElse(null);
    }

    @Override
    public Technician save(Technician technician) {
        return technicianDao.save(technician);
    }

    @Override
    public void deleteByid(Long id) {
        technicianDao.deleteById(id);
    }



}
