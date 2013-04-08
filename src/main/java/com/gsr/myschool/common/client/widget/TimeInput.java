package com.gsr.myschool.common.client.widget;

import com.google.common.base.Strings;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class TimeInput extends Composite implements LeafValueEditor<String> {
    public interface Binder extends UiBinder<Widget, TimeInput> {
    }

    private static final String[] HOURS = {"--", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19"};
    private static final String[] MINUTES = {"--", "00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"};

    @UiField
    ListBox hour;
    @UiField
    ListBox minute;

    @Inject
    public TimeInput(final Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));

        for (String item : HOURS) {
            hour.addItem(item);
        }

        for (String item : MINUTES) {
            minute.addItem(item);
        }

        hour.setItemSelected(0, true);
        minute.setItemSelected(0, true);
    }

    @Override
    public void setValue(String s) {
        if (!Strings.isNullOrEmpty(s)) {
            String hourStr = s.split(":")[0];
            String minuteStr = s.split(":")[1];

            for (int i = 0; i < HOURS.length; i++) {
                if (HOURS[i].equals(hourStr)) {
                    hour.setItemSelected(i, true);
                }
            }

            for (int i = 0; i < MINUTES.length; i++) {
                if (MINUTES[i].equals(minuteStr)) {
                    minute.setItemSelected(i, true);
                }
            }
        } else {
            hour.setItemSelected(0, true);
            minute.setItemSelected(0, true);
        }
    }

    @Override
    public String getValue() {
        int hourIdx = hour.getSelectedIndex();
        int minuteIdx = minute.getSelectedIndex();

        if (hourIdx == 0 || minuteIdx == 0) {
            return null;
        } else {
            return HOURS[hourIdx] + ":" + MINUTES[minuteIdx];
        }
    }

    public void setEnabled(Boolean enabled) {
        hour.setEnabled(enabled);
        minute.setEnabled(enabled);
    }
}
