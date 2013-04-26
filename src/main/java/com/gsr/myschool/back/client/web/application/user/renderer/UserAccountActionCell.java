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
import com.gsr.myschool.common.client.proxy.UserProxy;

public class UserAccountActionCell extends AbstractCell<UserProxy> {
    public interface Renderer extends UiRenderer {
        void render(SafeHtmlBuilder sb);

        void onBrowserEvent(UserAccountActionCell o, NativeEvent e, Element p);
    }

    private final Renderer uiRenderer;

    private Delegate<UserProxy> editAccount;
    private Delegate<UserProxy> login;

    private UserProxy selectedObject;

    @Inject
    public UserAccountActionCell(final Renderer uiRenderer,
                                 @Assisted("editAccount") Delegate<UserProxy> editAccount,
                                 @Assisted("login") Delegate<UserProxy> login) {
        super(BrowserEvents.CLICK);

        this.uiRenderer = uiRenderer;
        this.editAccount = editAccount;
        this.login = login;
    }

    @Override
    public void onBrowserEvent(Context context, Element parent, UserProxy value, NativeEvent event,
                               ValueUpdater<UserProxy> valueUpdater) {
        selectedObject = value;
        uiRenderer.onBrowserEvent(this, event, parent);
    }

    @Override
    public void render(Context context, UserProxy value, SafeHtmlBuilder builder) {
        uiRenderer.render(builder);
    }

    @UiHandler({"editAccount"})
    void onEditClicked(ClickEvent event) {
        editAccount.execute(selectedObject);
    }

    @UiHandler({"login"})
    void onLoginClicked(ClickEvent event) {
        login.execute(selectedObject);
    }
}
