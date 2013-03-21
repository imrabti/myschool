package com.gsr.myschool.common.client.widget.renderer;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiRenderer;
import com.google.inject.Inject;
import com.gsr.myschool.common.shared.dto.LabelValue;

public class LabelValueCell extends AbstractCell<LabelValue> {
    public interface Renderer extends UiRenderer {
        void render(SafeHtmlBuilder sb, String label, String value);
    }

    private final Renderer uiRenderer;

    @Inject
    public LabelValueCell(final Renderer uiRenderer) {
        this.uiRenderer = uiRenderer;
    }

    @Override
    public void render(Context context, LabelValue value, SafeHtmlBuilder builder) {
        uiRenderer.render(builder, value.getLabel(), value.getValue());
    }
}
