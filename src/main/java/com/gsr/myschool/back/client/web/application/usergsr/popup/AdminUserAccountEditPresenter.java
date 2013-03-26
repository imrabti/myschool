package com.gsr.myschool.back.client.web.application.usergsr.popup;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.back.client.request.UserRequest;
import com.gsr.myschool.back.client.web.application.usergsr.event.AdminUserChangedEvent;
import com.gsr.myschool.common.client.mvp.ValidatedPopupView;
import com.gsr.myschool.common.client.proxy.AdminUserProxy;
import com.gsr.myschool.common.client.request.ValidatedReceiverImpl;
import com.gsr.myschool.common.client.resource.message.SharedMessageBundle;
import com.gsr.myschool.common.client.widget.messages.CloseDelay;
import com.gsr.myschool.common.client.widget.messages.Message;
import com.gsr.myschool.common.client.widget.messages.event.MessageEvent;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class AdminUserAccountEditPresenter extends PresenterWidget<AdminUserAccountEditPresenter.MyView>
        implements AdminUserAccountEditUiHandlers {
    public interface MyView extends ValidatedPopupView, HasUiHandlers<AdminUserAccountEditUiHandlers> {
        void edit(AdminUserProxy userProxy);
    }

    private final SharedMessageBundle messageBundle;
    private final BackRequestFactory requestFactory;

    private UserRequest currentContext;
    private AdminUserProxy currentUser;
    private Boolean userViolation;

    @Inject
    public AdminUserAccountEditPresenter(final EventBus eventBus, final MyView view,
                                         final SharedMessageBundle messageBundle,
                                         final BackRequestFactory requestFactory) {
        super(eventBus, view);

        this.messageBundle = messageBundle;
        this.requestFactory = requestFactory;

        getView().setUiHandlers(this);
    }

    public void editDatas(AdminUserProxy adminUser) {
        currentContext = requestFactory.userService();
        currentUser = currentContext.edit(adminUser);
        userViolation = false;

        getView().edit(currentUser);
    }

    public void initDatas() {
        currentContext = requestFactory.userService();
        currentUser = currentContext.create(AdminUserProxy.class);
        userViolation = false;

        getView().edit(currentUser);
    }

    @Override
    public void saveAccount(AdminUserProxy userProxy) {
        if (!userViolation) {
            currentContext.saveAdminAccount(currentUser).fire(new ValidatedReceiverImpl<Boolean>() {
                @Override
                public void onValidationError(Set<ConstraintViolation<?>> violations) {
                    userViolation = true;

                    getView().clearErrors();
                    getView().showErrors(violations);
                }

                @Override
                public void onSuccess(Boolean response) {
                    String messageString = response ? messageBundle.operationSuccess() : messageBundle.operationFailure();
                    AlertType alertType = response ? AlertType.SUCCESS : AlertType.ERROR;
                    Message message = new Message.Builder(messageString)
                            .style(alertType).closeDelay(CloseDelay.DEFAULT).build();
                    MessageEvent.fire(this, message);
                    AdminUserChangedEvent.fire(this);

                    userViolation = false;

                    getView().clearErrors();
                    getView().hide();
                }
            });
        } else {
            currentContext.fire();
        }
    }

    @Override
    protected void onReveal(){
        getView().clearErrors();
    }
}
