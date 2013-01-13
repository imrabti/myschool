package com.gsr.myschool.front.client.web.application.widget.sider;

import com.gwtplatform.mvp.client.UiHandlers;
import com.gsr.myschool.front.client.web.application.widget.sider.FrontMenuView.MenuItem;

public interface FrontMenuUiHandlers extends UiHandlers {
    void onMenuChanged(MenuItem selectedMenu);
}
