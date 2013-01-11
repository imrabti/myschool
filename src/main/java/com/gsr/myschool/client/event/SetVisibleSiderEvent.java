package com.gsr.myschool.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;

public class SetVisibleSiderEvent extends GwtEvent<SetVisibleSiderEvent.SetVisibleSiderHandler> {
    public interface SetVisibleSiderHandler extends EventHandler {
        void onVisibleSider(SetVisibleSiderEvent event);
    }

    private static final Type<SetVisibleSiderHandler> TYPE = new Type<SetVisibleSiderHandler>();

    @SuppressWarnings("rawtypes")
    private PresenterWidget sider;

    @SuppressWarnings("rawtypes")
    public SetVisibleSiderEvent(PresenterWidget sider) {
        this.sider = sider;
    }

    public static Type<SetVisibleSiderHandler> getType() {
        return TYPE;
    }

    @SuppressWarnings("rawtypes")
    public static void fire(HasHandlers source, PresenterWidget sider) {
        source.fireEvent(new SetVisibleSiderEvent(sider));
    }

    @SuppressWarnings("rawtypes")
    public PresenterWidget getSider() {
        return sider;
    }

    @Override
    public Type<SetVisibleSiderHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SetVisibleSiderHandler handler) {
        handler.onVisibleSider(this);
    }
}
