package com.gsr.myschool.common.client.resource;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.gsr.myschool.common.client.resource.style.PopupStyle;
import com.gsr.myschool.common.client.resource.style.SharedStyle;

public interface SharedResources extends ClientBundle {
    @Source("com/gsr/myschool/common/client/resource/style/sharedStyle.css")
    SharedStyle sharedStyleCss();

    @Source("com/gsr/myschool/common/client/resource/style/popupStyle.css")
    PopupStyle popupStyleCss();

    @Source("com/gsr/myschool/common/client/resource/image/logo.png")
    @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
    ImageResource logo();

    @Source("com/gsr/myschool/common/client/resource/image/required.png")
    @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
    ImageResource required();
}
