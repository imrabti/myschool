package com.gsr.myschool.common.client.resource;

import com.google.gwt.resources.client.ClientBundle;
import com.gsr.myschool.common.client.resource.style.SharedStyle;

public interface SharedResources extends ClientBundle {
    @Source("com/gsr/myschool/common/client/resource/style/sharedStyle.css")
    SharedStyle sharedStyleCss();
}
