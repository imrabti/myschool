package com.gsr.myschool.server.repos;

import com.gsr.myschool.server.business.DefLov;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DefLovRepo extends JpaRepository<DefLov, Long>{

    public DefLov findByName(String name);
    public List<DefLov> findByParent_Id(Long id);
}
