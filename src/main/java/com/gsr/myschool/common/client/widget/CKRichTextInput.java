package com.gsr.myschool.common.client.widget;

import com.axeiya.gwtckeditor.client.CKConfig;
import com.axeiya.gwtckeditor.client.CKEditor;
import com.axeiya.gwtckeditor.client.Toolbar;
import com.axeiya.gwtckeditor.client.ToolbarLine;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

public class CKRichTextInput extends Composite implements LeafValueEditor<String> {
    private final FlowPanel mainLayout;
    private final CKEditor ckEditor;

    public CKRichTextInput() {
        mainLayout = new FlowPanel();

        initWidget(mainLayout);

        ToolbarLine line = new ToolbarLine();
        line.add(CKConfig.TOOLBAR_OPTIONS.Bold);
        line.add(CKConfig.TOOLBAR_OPTIONS.Italic);
        line.add(CKConfig.TOOLBAR_OPTIONS.Underline);

        line.addBlockSeparator();

        line.add(CKConfig.TOOLBAR_OPTIONS.JustifyLeft);
        line.add(CKConfig.TOOLBAR_OPTIONS.JustifyCenter);
        line.add(CKConfig.TOOLBAR_OPTIONS.JustifyRight);

        line.addBlockSeparator();

        line.add(CKConfig.TOOLBAR_OPTIONS.TextColor);
        line.add(CKConfig.TOOLBAR_OPTIONS.Indent);
        line.add(CKConfig.TOOLBAR_OPTIONS.NumberedList);
        line.add(CKConfig.TOOLBAR_OPTIONS.BulletedList);

        line.addBlockSeparator();

        line.add(CKConfig.TOOLBAR_OPTIONS.Link);
        line.add(CKConfig.TOOLBAR_OPTIONS.RemoveFormat);

        line.addBlockSeparator();

        line.add(CKConfig.TOOLBAR_OPTIONS.Paste);
        line.add(CKConfig.TOOLBAR_OPTIONS.PasteText);

        ToolbarLine line2 = new ToolbarLine();
        line2.add(CKConfig.TOOLBAR_OPTIONS.Styles);
        line2.add(CKConfig.TOOLBAR_OPTIONS.FontSize);
        line2.add(CKConfig.TOOLBAR_OPTIONS.Font);

        Toolbar t = new Toolbar();
        t.add(line);
        t.addSeparator();
        t.add(line2);

        CKConfig ckf = new CKConfig();
        ckf.setToolbar(t);

        ckEditor = new CKEditor(ckf);
        mainLayout.add(ckEditor);
    }

    @Override
    public String getValue() {
        return ckEditor.getHTML();
    }

    @Override
    public void setValue(final String html) {
        ckEditor.setHTML(html);
    }
}
