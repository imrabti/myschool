package com.gsr.myschool.server.service;

import com.gsr.myschool.server.business.DefLov;
import com.gsr.myschool.server.business.LOV;
import com.gsr.myschool.server.exception.CannotDeleteDefLovException;
import com.gsr.myschool.server.exception.DefLovNotFoundException;

import java.util.List;

public interface DefLovService {

    public void add(DefLov toPersist);
    public DefLov findByName(String name);
    public List<DefLov> findAll();
    public void delete(DefLov toDelete) throws CannotDeleteDefLovException, DefLovNotFoundException;
    public List<DefLov> getChildren(Long id);
    public List<LOV> getValues(Long id);
}
