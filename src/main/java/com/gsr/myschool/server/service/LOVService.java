package com.gsr.myschool.server.service;

import com.gsr.myschool.server.business.LOV;

import java.util.List;

public interface LOVService {

    public void add(LOV lov);

    public LOV find(Long id);

    public void add(String value, Long parentId, Long defLovId);

    public List<LOV> findAll();

    public List<LOV> findByDefLovName(String defLovName);

    public LOV findByValueAndDefLovName(String value, String defLovName);

    public List<LOV> findByDefLovParentName(String defLovName);

    public void delete(Long id);
}
