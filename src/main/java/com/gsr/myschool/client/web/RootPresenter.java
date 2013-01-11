package com.gsr.myschool.client.web;

import com.google.gwt.event.shared.GwtEvent;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gsr.myschool.client.web.widget.message.MessagePresenter;

public class RootPresenter extends Presenter<RootPresenter.MyView, RootPresenter.MyProxy> {
    public interface MyView extends View {
    }

    @ProxyStandard
    public interface MyProxy extends Proxy<RootPresenter> {
    }

    @ContentSlot
    public static final GwtEvent.Type<RevealContentHandler<?>> TYPE_SetMainContent = new GwtEvent.Type<RevealContentHandler<?>>();
    public static final Object TYPE_SetMessageContent = new Object();

    private final MessagePresenter messagePresenter;

    @Inject
    public RootPresenter(final EventBus eventBus, final MyView view,
                         final MyProxy proxy, final MessagePresenter messagePresenter) {
        super(eventBus, view, proxy, RevealType.RootLayout);

        this.messagePresenter = messagePresenter;
    }

    @Override
    protected void onReveal() {
        setInSlot(TYPE_SetMessageContent, messagePresenter);
    }
}
