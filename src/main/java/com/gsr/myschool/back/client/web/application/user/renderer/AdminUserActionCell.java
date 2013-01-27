package com.gsr.myschool.back.client.web.application.user.renderer;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiRenderer;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.gsr.myschool.common.client.proxy.AdminUserProxy;

public class AdminUserActionCell extends AbstractCell<AdminUserProxy> {
    public interface Renderer extends UiRenderer {
        void render(SafeHtmlBuilder sb);

        void onBrowserEvent(AdminUserActionCell o, NativeEvent e, Element p);
    }

    private final Renderer uiRenderer;

    private Delegate<AdminUserProxy> editAccount;
    private Delegate<AdminUserProxy> editStatus;

    private AdminUserProxy selectedObject;

    @Inject
    public AdminUserActionCell(final Renderer uiRenderer,
            @Assisted("editAccount") Delegate<AdminUserProxy> editAccount,
            @Assisted("editStatus") Delegate<AdminUserProxy> editStatus) {
        super(BrowserEvents.CLICK);

        this.uiRenderer = uiRenderer;
        this.editAccount = editAccount;
        this.editStatus = editStatus;
    }

    @Override
    public void onBrowserEvent(Context context, Element parent, AdminUserProxy value, NativeEvent event,
                               ValueUpdater<AdminUserProxy> valueUpdater) {
        selectedObject = value;
        uiRenderer.onBrowserEvent(this, event, parent);
    }

    @Override
    public void render(Context context, AdminUserProxy value, SafeHtmlBuilder builder) {
        uiRenderer.render(builder);
    }

    @UiHandler({"editAccount"})
    void onPreviewClicked(ClickEvent event) {
        editAccount.execute(selectedObject);
    }

    @UiHandler({"editStatus"})
    void onChangeStatus(ClickEvent event) {
        editStatus.execute(selectedObject);
    }
}
