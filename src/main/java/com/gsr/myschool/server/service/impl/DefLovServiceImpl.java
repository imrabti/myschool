package com.gsr.myschool.server.service.impl;

import com.gsr.myschool.server.business.DefLov;
import com.gsr.myschool.server.business.LOV;
import com.gsr.myschool.server.exception.CannotDeleteDefLovException;
import com.gsr.myschool.server.exception.DefLovNotFoundException;
import com.gsr.myschool.server.repos.DefLovRepo;
import com.gsr.myschool.server.service.DefLovService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DefLovServiceImpl implements DefLovService {

    @Autowired
    DefLovRepo dlr;

    public void add(DefLov toPersist)
    {
        dlr.save(toPersist);
    }

    @Override
    public DefLov findByName(String name) {
        return dlr.findByName(name);
    }

    @Override
    public List<DefLov> findAll() {
        return dlr.findAll();
    }

    @Override
    public void delete(DefLov toDelete) throws CannotDeleteDefLovException, DefLovNotFoundException {
        if(toDelete != null)
            if(toDelete.getSystem())
                throw new CannotDeleteDefLovException();
            else
                dlr.delete(toDelete);
        else
            throw new DefLovNotFoundException();
    }

    @Override
    public List<DefLov> getChildren(Long id) {
        return dlr.findByParent_Id(id);
    }

    @Override
    public List<LOV> getValues(Long id) {
        DefLov defLov = dlr.findOne(id);
        return new ArrayList<LOV>();
    }

}
