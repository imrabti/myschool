package com.gsr.myschool.front.client.resource.style;

import com.google.gwt.user.cellview.client.CellList;

public interface InboxListStyle extends CellList.Resources {
    @Source({CellList.Style.DEFAULT_CSS, "com/gsr/myschool/front/client/resource/style/inboxListStyle.css"})
    ListStyle cellListStyle();

    interface ListStyle extends CellList.Style {
    }
}
