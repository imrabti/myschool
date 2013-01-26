package com.gsr.myschool.server.repos;

import com.gsr.myschool.server.business.lov.ValueType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

public interface ValueTypeRepos extends JpaRepository<ValueType, Long> {
    ValueType findByName(String name);

    List<ValueType> findByParentIsNull();

    List<ValueType> findByParentId(Long id);
}