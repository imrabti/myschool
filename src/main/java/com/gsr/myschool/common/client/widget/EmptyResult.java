package com.gsr.myschool.common.client.widget;

import com.github.gwtbootstrap.client.ui.Alert;
import com.github.gwtbootstrap.client.ui.constants.AlertType;

import static com.google.gwt.query.client.GQuery.$;

public class EmptyResult extends Alert {
    public EmptyResult(String message) {
        super(message, AlertType.WARNING, false);

        $(this).css("width", "300px");
        $(this).css("margin-left", "auto");
        $(this).css("margin-right", "auto");
        $(this).css("margin-top", "10px");
    }
}
