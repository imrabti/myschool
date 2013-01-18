package com.gsr.myschool.server.repos;

import com.gsr.myschool.server.business.lov.ValueList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ValueListRepos extends JpaRepository<ValueList, Long> {
    ValueList findByValue(String value);

    List<ValueList> findByValueType_Name(String name);

    ValueList findByValueAndValueType_Name(String value, String valueTypeName);

    List<ValueList> findByParentIsNull();

    List<ValueList> findByParentValue(String parentValue);

    List<ValueList> findByValueType_Parent_Name(String name);
}
