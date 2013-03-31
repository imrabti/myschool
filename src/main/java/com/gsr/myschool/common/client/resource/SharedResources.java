package com.gsr.myschool.common.client.resource;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.gsr.myschool.common.client.resource.style.DatePickerStyle;
import com.gsr.myschool.common.client.resource.style.PopupStyle;
import com.gsr.myschool.common.client.resource.style.SharedStyle;
import com.gsr.myschool.common.client.resource.style.SuggestionBoxStyle;

public interface SharedResources extends ClientBundle {
    @Source("com/gsr/myschool/common/client/resource/style/sharedStyle.css")
    SharedStyle sharedStyleCss();

    @Source("com/gsr/myschool/common/client/resource/style/popupStyle.css")
    PopupStyle popupStyleCss();

    @Source("com/gsr/myschool/common/client/resource/style/suggestionBoxStyle.css")
    SuggestionBoxStyle suggestBoxStyleCss();

    @Source("com/gsr/myschool/common/client/resource/style/datePickerStyle.css")
    DatePickerStyle datePickerStyle();

    @Source("com/gsr/myschool/common/client/resource/image/logo.png")
    @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
    ImageResource logo();

    @Source("com/gsr/myschool/common/client/resource/image/required.png")
    @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
    ImageResource required();

    @Source("com/gsr/myschool/common/client/resource/image/serverDown.png")
    @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
    ImageResource serverDown();

    @Source("com/gsr/myschool/common/client/resource/image/loading.gif")
    ImageResource loading();

    @Source("com/gsr/myschool/common/client/resource/image/checked.png")
    ImageResource checked();

    @Source("com/gsr/myschool/common/client/resource/image/notChecked.png")
    ImageResource notChecked();
}
