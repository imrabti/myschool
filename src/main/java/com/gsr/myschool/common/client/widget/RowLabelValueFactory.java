package com.gsr.myschool.common.client.widget;

import com.google.gwt.safehtml.shared.SafeHtml;

public interface RowLabelValueFactory {
    RowHeader createHeader(String label);

    RowLabelValue createValueLabel(String label, SafeHtml value);
}
