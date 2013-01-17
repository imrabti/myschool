package com.gsr.myschool.server.repos;

import com.gsr.myschool.server.business.LOV;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * SuperUser: ridick
 * Date: 19/12/12
 * Time: 19:12
 * To change this template use File | Settings | File Templates.
 */
public interface LOVRepo extends JpaRepository<LOV, Long> {
    public List<LOV> findByDefLov_Name(String defLovName);

    public LOV findByValueAndDefLov_Name(String value, String defLovName);

    public List<LOV> findByDefLov_Parent_Name(String name);
}
