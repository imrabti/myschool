package com.gsr.myschool.server.repos;

import com.gsr.myschool.server.business.ValueList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValueListRepos extends JpaRepository<ValueList, Long> {
    ValueList findByValue(String value);

    List<ValueList> findByParent(long id);

    List<ValueList> findByParentIsNull();

    List<ValueList> findByParent_value(String parentNom);

    List<ValueList> findByTypeValue_nom(String nom);
}
