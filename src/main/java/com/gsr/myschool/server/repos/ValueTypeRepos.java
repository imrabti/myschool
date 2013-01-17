package com.gsr.myschool.server.repos;

import java.io.Serializable;
import java.util.List;

import com.gsr.myschool.server.business.ValueType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ValueTypeRepos extends JpaRepository<ValueType, Serializable> {
    ValueType findByNom(String nom);

    List<ValueType> findByParentIsNull();

    List<ValueType> findByParent_nom(String nom);

    @Modifying
    @Query("update ValueType t set t.nom = ?1 where t.id= ?2")
    void updateType(String nom, Long id);
}