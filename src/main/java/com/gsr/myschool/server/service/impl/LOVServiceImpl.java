package com.gsr.myschool.server.service.impl;

import com.gsr.myschool.server.business.DefLov;
import com.gsr.myschool.server.business.LOV;
import com.gsr.myschool.server.repos.DefLovRepo;
import com.gsr.myschool.server.repos.LOVRepo;
import com.gsr.myschool.server.service.LOVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * SuperUser: ridick
 * Date: 21/12/12
 * Time: 15:23
 * To change this template use File | Settings | File Templates.
 */
@Service
@Transactional
public class LOVServiceImpl implements LOVService {

    @Autowired
    LOVRepo lovRepo;

    @Autowired
    DefLovRepo dlr;

    @Override
    @Transactional
    public void add(LOV lov) {
        DefLov defLov = dlr.findOne(lov.getDefLov().getId());
        lov.setDefLov(defLov);
        if(lov.getParent() != null)
        {
            LOV parent = lovRepo.findOne(lov.getParent().getId());
            lov.setParent(parent);
        }
        lovRepo.save(lov);
    }

    @Override
    public LOV find(Long id) {
        return lovRepo.findOne(id);
    }

    @Override
    public void add(String value, Long parentId, Long defLovId) {
        LOV parent = lovRepo.findOne(parentId);
        DefLov defLov = dlr.findOne(defLovId);
        lovRepo.save(new LOV(value, parent,defLov));
    }

    @Override
    public List<LOV> findAll() {
        return lovRepo.findAll();
    }

    @Override
    public List<LOV> findByDefLovName(String defLovName) {
        return lovRepo.findByDefLov_Name(defLovName);
    }

    @Override
    public LOV findByValueAndDefLovName(String value, String defLovName){
        return lovRepo.findByValueAndDefLov_Name(value, defLovName);
    }

    @Override
    public List<LOV> findByDefLovParentName(String defLovName) {
        DefLov defLov = dlr.findByName(defLovName);
        DefLov parent = defLov.getParent();
        if(parent != null)
            return lovRepo.findByDefLov_Name(parent.getName());
        else
            return new ArrayList<LOV>();
    }

    @Override
    public void delete(Long id) {
        lovRepo.delete(id);
    }

}
