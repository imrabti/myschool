package com.gsr.myschool.back.client.web.application.validation.renderer;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiRenderer;
import com.google.inject.Inject;

public class VerificationRatioCell extends AbstractCell<String> {
    public interface Renderer extends UiRenderer {
        void render(SafeHtmlBuilder builder, String ratio);
    }

    private final Renderer uiRenderer;

    @Inject
    public VerificationRatioCell(final Renderer uiRenderer) {
        super();

        this.uiRenderer = uiRenderer;
    }

    @Override
    public void render(Context context, String value, SafeHtmlBuilder builder) {
        String ratio = value;
        uiRenderer.render(builder, ratio);
    }
}
