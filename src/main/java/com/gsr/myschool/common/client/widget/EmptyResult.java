package com.gsr.myschool.common.client.widget;

import com.github.gwtbootstrap.client.ui.Alert;
import com.github.gwtbootstrap.client.ui.constants.AlertType;

import static com.google.gwt.query.client.GQuery.$;

public class EmptyResult extends Alert {
    public EmptyResult(String message, AlertType type) {
        super(message, type, false);

        $(this).css("margin-top", "20px");
    }
}
