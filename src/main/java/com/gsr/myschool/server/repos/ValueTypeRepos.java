package com.gsr.myschool.server.repos;

import com.gsr.myschool.server.business.valuelist.ValueType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface ValueTypeRepos extends JpaRepository<ValueType, Serializable> {
}
