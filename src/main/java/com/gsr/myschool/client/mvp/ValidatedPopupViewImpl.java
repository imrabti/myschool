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

package com.gsr.myschool.client.mvp;

import com.arcbees.core.client.mvp.PopupViewImpl;
import com.github.gwtbootstrap.client.ui.ControlGroup;
import com.github.gwtbootstrap.client.ui.constants.ControlGroupType;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.web.bindery.event.shared.EventBus;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static com.google.gwt.query.client.GQuery.$;

public abstract class ValidatedPopupViewImpl extends PopupViewImpl implements ValidatedPopupView {
    private final ValidationErrorPopup errorPopup;

    protected ValidatedPopupViewImpl(EventBus eventBus, final ValidationErrorPopup errorPopup) {
        super(eventBus);
        this.errorPopup = errorPopup;
    }

    @Override
    public void showErrors(Set<ConstraintViolation<?>> violations) {
        for (ConstraintViolation violation : violations) {
            String fieldId = "#" + violation.getPropertyPath();
            $(fieldId).attr("message", violation.getMessage());
            $(fieldId).addClass("errorField");
            ControlGroup wrapper = $(fieldId).parent().widget();
            wrapper.setType(ControlGroupType.ERROR);

            $(fieldId).focus(new Function() {
                @Override
                public void f(Element e) {
                    showErrorFieldPopup(e);
                }
            });

            $(fieldId).blur(new Function() {
                @Override
                public void f(Element e) {
                    errorPopup.hide();
                }
            });
        }
    }

    @Override
    public void clearErrors() {
        $(".errorField").each(new Function() {
            @Override
            public void f(Element e) {
                String fieldId = "#" + e.getId();
                $(fieldId).unbind(Event.ONFOCUS);
                $(fieldId).unbind(Event.ONBLUR);
                $(fieldId).removeClass("errorField");
                ControlGroup wrapper = $(fieldId).parent().widget();
                wrapper.setType(ControlGroupType.NONE);
            }
        });
    }

    protected void showErrorFieldPopup(final Element element) {
        errorPopup.setMessage(element.getAttribute("message"));
        errorPopup.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
            @Override
            public void setPosition(int offsetWidth, int offsetHeight) {
                int left = element.getAbsoluteLeft();
                int top = element.getAbsoluteTop() - errorPopup.getOffsetHeight();
                errorPopup.setPopupPosition(left, top);
            }
        });
    }
}
