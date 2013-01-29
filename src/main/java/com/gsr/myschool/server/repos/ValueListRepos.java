package com.gsr.myschool.server.repos;

import com.gsr.myschool.common.shared.type.ValueTypeCode;
import com.gsr.myschool.server.business.valuelist.ValueList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ValueListRepos extends JpaRepository<ValueList, Long> {
    List<ValueList> findByValueTypeCode(ValueTypeCode valueTypeCode);

    ValueList findByValueAndValueTypeCode(String value, ValueTypeCode valueTypeCode);
}
