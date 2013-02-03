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

import com.gsr.myschool.server.business.valuelist.ValueList;
import com.gsr.myschool.server.business.valuelist.ValueType;
import com.gsr.myschool.server.repos.ValueListRepos;
import com.gsr.myschool.server.repos.ValueTypeRepos;
import com.gsr.myschool.server.service.ValueTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ValueTypeServiceImpl implements ValueTypeService {
    @Autowired
    private ValueTypeRepos valueTypeRepos;
    @Autowired
    private ValueListRepos valueListRepos;

    @Override
    public void updateValueType(ValueType valueType) {
        valueType.setParent(valueType.getParent() != null ?
                valueTypeRepos.findOne(valueType.getParent().getId()) : null);
        valueType.setRegex(valueType.getRegex() != null ?
                valueListRepos.findOne(valueType.getRegex().getId()) : null);
        valueTypeRepos.save(valueType);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ValueType> findAll() {
        return valueTypeRepos.findAll();
    }

    @Override
    public void deleteValueType(Long id) {
        ValueType valueType = valueTypeRepos.findOne(id);
        List<ValueList> toRemove = valueListRepos.findByValueTypeCode(valueType.getCode());
        valueListRepos.delete(toRemove);
        valueTypeRepos.delete(valueType);
    }
}
