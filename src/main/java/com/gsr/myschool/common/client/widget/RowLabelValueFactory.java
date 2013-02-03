package com.gsr.myschool.common.client.widget;

import com.google.gwt.safehtml.shared.SafeHtml;

public interface RowLabelValueFactory {
    RowLabelValue create(String label, SafeHtml value);
}
