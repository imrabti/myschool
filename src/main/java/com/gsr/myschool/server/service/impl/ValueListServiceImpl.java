/**
 * Copyright 2012 Nuvola Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.gsr.myschool.server.service.impl;

import com.gsr.myschool.common.shared.type.ValueTypeCode;
import com.gsr.myschool.server.business.valuelist.ValueList;
import com.gsr.myschool.server.repos.ValueListRepos;
import com.gsr.myschool.server.repos.ValueTypeRepos;
import com.gsr.myschool.server.service.ValueListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ValueListServiceImpl implements ValueListService {
    @Autowired
    private ValueListRepos valueListRepos;
    @Autowired
    private ValueTypeRepos valueTypeRepos;

    @Override
    public void addValueList(ValueList valueList) {
        valueList.setParent(valueList.getParent() != null ?
                valueListRepos.findOne(valueList.getParent().getId()) : null);
        valueList.setValueType(valueList.getValueType() != null ?
                valueTypeRepos.findOne(valueList.getValueType().getId()) : null);
        valueListRepos.save(valueList);
    }

    @Override
    public void deleteValueList(Long id) {
        valueListRepos.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ValueList> findAll() {
        return valueListRepos.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ValueList> findByValueTypeCode(ValueTypeCode valueTypeCode) {
        return valueListRepos.findByValueTypeCode(valueTypeCode);
    }
}
