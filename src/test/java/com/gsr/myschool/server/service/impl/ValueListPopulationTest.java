package com.gsr.myschool.server.service.impl;

import com.gsr.myschool.common.shared.type.ValueTypeCode;
import com.gsr.myschool.server.business.valuelist.ValueList;
import com.gsr.myschool.server.business.valuelist.ValueType;
import com.gsr.myschool.server.repos.ValueListRepos;
import com.gsr.myschool.server.repos.ValueTypeRepos;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:META-INF/applicationContext.xml",
        "classpath*:/META-INF/applicationContext-security.xml",
        "classpath*:/META-INF/applicationContext-activiti.xml"
})
@ActiveProfiles("default")
@TransactionConfiguration(defaultRollback = false)
public class ValueListPopulationTest {
    @Autowired
    private ValueTypeRepos valueTypeRepos;
    @Autowired
    private ValueListRepos valueListRepos;

    @Test
    public void populateValueType() {
        for (ValueTypeCode code : ValueTypeCode.values()) {
            ValueType valueType = new ValueType();
            valueType.setCode(code);
            valueType.setSystem(false);
            valueTypeRepos.save(valueType);
        }
    }

    @Test
    public void populateValueListNationnality() {
        ValueType valueType = valueTypeRepos.findByCode(ValueTypeCode.NATIONALITY);

        ValueList valueList = new ValueList();
        valueList.setValueType(valueType);
        valueList.setLabel("Marocaine");
        valueList.setValue("Marocaine");
        valueListRepos.save(valueList);

        valueList = new ValueList();
        valueList.setValueType(valueType);
        valueList.setLabel("Francaise");
        valueList.setValue("Francaise");
        valueListRepos.save(valueList);

        valueList = new ValueList();
        valueList.setValueType(valueType);
        valueList.setLabel("Anglaise");
        valueList.setValue("Anglaise");
        valueListRepos.save(valueList);

        valueList = new ValueList();
        valueList.setValueType(valueType);
        valueList.setLabel("Portugaise");
        valueList.setValue("Portugaise");
        valueListRepos.save(valueList);
    }

    @Test
    public void populateValueListBacSerie() {
        ValueType valueType = valueTypeRepos.findByCode(ValueTypeCode.BAC_SERIE);

        ValueList valueList = new ValueList();
        valueList.setValueType(valueType);
        valueList.setLabel("Electronique");
        valueList.setValue("Electronique");
        valueListRepos.save(valueList);

        valueList = new ValueList();
        valueList.setValueType(valueType);
        valueList.setLabel("Science Ex");
        valueList.setValue("Science Ex");
        valueListRepos.save(valueList);

        valueList = new ValueList();
        valueList.setValueType(valueType);
        valueList.setLabel("Litérature francaise");
        valueList.setValue("Litérature francaise");
        valueListRepos.save(valueList);

        valueList = new ValueList();
        valueList.setValueType(valueType);
        valueList.setLabel("Mathematique");
        valueList.setValue("Mathematique");
        valueListRepos.save(valueList);
    }

    @Test
    public void populateValueListBacAnnee() {
        ValueType valueType = valueTypeRepos.findByCode(ValueTypeCode.BAC_YEAR);

        ValueList valueList = new ValueList();
        valueList.setValueType(valueType);
        valueList.setLabel("2008");
        valueList.setValue("2008");
        valueListRepos.save(valueList);

        valueList = new ValueList();
        valueList.setValueType(valueType);
        valueList.setLabel("2009");
        valueList.setValue("2009");
        valueListRepos.save(valueList);

        valueList = new ValueList();
        valueList.setValueType(valueType);
        valueList.setLabel("2010");
        valueList.setValue("2010");
        valueListRepos.save(valueList);

        valueList = new ValueList();
        valueList.setValueType(valueType);
        valueList.setLabel("2011");
        valueList.setValue("2011");
        valueListRepos.save(valueList);
    }

    @Test
    public void populateValueListAnneeScolaire() {
        ValueType valueType = valueTypeRepos.findByCode(ValueTypeCode.SCHOOL_YEAR);

        ValueList valueList = new ValueList();
        valueList.setValueType(valueType);
        valueList.setLabel("2006-2007");
        valueList.setValue("2006-2007");
        valueListRepos.save(valueList);

        valueList = new ValueList();
        valueList.setValueType(valueType);
        valueList.setLabel("2007-2008");
        valueList.setValue("2007-2008");
        valueListRepos.save(valueList);

        valueList = new ValueList();
        valueList.setValueType(valueType);
        valueList.setLabel("2008-2009");
        valueList.setValue("2008-2009");
        valueListRepos.save(valueList);

        valueList = new ValueList();
        valueList.setValueType(valueType);
        valueList.setLabel("2009-2010");
        valueList.setValue("2009-2010");
        valueListRepos.save(valueList);

        valueList = new ValueList();
        valueList.setValueType(valueType);
        valueList.setLabel("2010-2011");
        valueList.setValue("2010-2011");
        valueListRepos.save(valueList);

        valueList = new ValueList();
        valueList.setValueType(valueType);
        valueList.setLabel("2011-2012");
        valueList.setValue("2011-2012");
        valueListRepos.save(valueList);

        valueList = new ValueList();
        valueList.setValueType(valueType);
        valueList.setLabel("2012-2013");
        valueList.setValue("2012-2013");
        valueListRepos.save(valueList);
    }
}
