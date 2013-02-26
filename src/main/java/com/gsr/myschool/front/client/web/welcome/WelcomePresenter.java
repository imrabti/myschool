package com.gsr.myschool.front.client.web.welcome;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.front.client.place.NameTokens;
import com.gsr.myschool.front.client.web.RootPresenter;
import com.gsr.myschool.front.client.web.welcome.widget.RegisterPresenter;
import com.gsr.myschool.front.client.web.welcome.widget.LoginPresenter;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

public class WelcomePresenter extends Presenter<WelcomePresenter.MyView, WelcomePresenter.MyProxy> {
    public interface MyView extends View {
    }

    @ProxyStandard
    @NameToken(NameTokens.welcome)
    public interface MyProxy extends ProxyPlace<WelcomePresenter> {
    }

    public static final Object TYPE_SetLoginContent = new Object();
    public static final Object TYPE_SetRegisterContent = new Object();

    private final LoginPresenter loginPresenter;
    private final RegisterPresenter registerPresenter;

    @Inject
    public WelcomePresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
                            final LoginPresenter loginPresenter,
                            final RegisterPresenter registerPresenter) {
        super(eventBus, view, proxy, RootPresenter.TYPE_SetMainContent);

        this.loginPresenter = loginPresenter;
        this.registerPresenter = registerPresenter;
    }

    @Override
    protected void onReveal() {
        setInSlot(TYPE_SetLoginContent, loginPresenter);
        setInSlot(TYPE_SetRegisterContent, registerPresenter);
    }
}
