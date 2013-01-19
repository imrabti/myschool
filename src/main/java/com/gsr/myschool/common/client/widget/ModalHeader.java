package com.gsr.myschool.common.client.widget;

import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import javax.inject.Inject;

public class ModalHeader extends Composite implements HasText {
    public interface Binder extends UiBinder<Widget, ModalHeader> {
    }

    @UiField
    HeadingElement modalTitle;

    @UiField
    Anchor close;

    @Inject
    public ModalHeader(final Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public String getText() {
        return modalTitle.getInnerHTML();
    }

    @Override
    public void setText(String title) {
        modalTitle.setInnerHTML(title);
    }

    public HandlerRegistration addCloseHandler(ClickHandler handler) {
        return close.addClickHandler(handler);
    }
}
