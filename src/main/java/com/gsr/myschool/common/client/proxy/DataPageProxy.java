package com.gsr.myschool.common.client.proxy;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.gsr.myschool.common.shared.dto.DataPage;

@ProxyFor(DataPage.class)
public interface DataPageProxy extends ValueProxy {
    Integer getPageNumber();

    void setPageNumber(Integer pageNumber);

    Integer getLength();

    void setLength(Integer length);
}
