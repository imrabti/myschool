package com.gsr.myschool.common.client.widget;

import com.github.gwtbootstrap.client.ui.Button;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class RichTextToolbar extends Composite {
    public interface Binder extends UiBinder<Widget, RichTextToolbar> {
    }

    private class EventHandler implements ClickHandler, KeyUpHandler {
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();

            if (sender == bold) {
                formatter.toggleBold();
            } else if (sender == italic) {
                formatter.toggleItalic();
            } else if (sender == underline) {
                formatter.toggleUnderline();
            } else if (sender == createLink) {
                String url = Window.prompt("Enter a link URL:", "http://");
                if (url != null) {
                    formatter.createLink(url);
                }
            } else if (sender == listOl) {
                formatter.insertOrderedList();
            } else if (sender == listUl) {
                formatter.insertUnorderedList();
            } else if (sender == richText) {
                updateStatus();
            }
        }

        public void onKeyUp(KeyUpEvent event) {
            Widget sender = (Widget) event.getSource();
            if (sender == richText) {
                updateStatus();
            }
        }
    }

    @UiField
    Button bold;
    @UiField
    Button italic;
    @UiField
    Button underline;
    @UiField
    Button listOl;
    @UiField
    Button listUl;
    @UiField
    Button createLink;

    private EventHandler handler;
    private RichTextArea richText;
    private RichTextArea.Formatter formatter;

    @Inject
    public RichTextToolbar(final Binder binder) {
        handler = new EventHandler();

        initWidget(binder.createAndBindUi(this));

        bold.addClickHandler(handler);
        italic.addClickHandler(handler);

    }

    public void initialize(RichTextArea richText) {
        this.richText = richText;
        this.formatter = richText.getFormatter();

        if (formatter != null) {
            richText.addKeyUpHandler(handler);
            richText.addClickHandler(handler);
        }
    }

    private void updateStatus() {
        if (formatter != null) {
            bold.setActive(formatter.isBold());
            italic.setActive(formatter.isItalic());
            underline.setActive(formatter.isUnderlined());
        }
    }
}
