/**
 * Copyright 2012 Nuvola Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.gsr.myschool.front.client.resource;

import com.google.gwt.resources.client.ImageResource;
import com.gsr.myschool.front.client.resource.style.FrontStyle;
import com.google.gwt.resources.client.ClientBundle;

public interface FrontResources extends ClientBundle {
    @Source("com/gsr/myschool/front/client/resource/style/frontStyle.css")
    FrontStyle frontStyleCss();

    @Source("com/gsr/myschool/front/client/resource/image/mainBack.png")
    @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
    ImageResource mainBack();

    @Source("com/gsr/myschool/front/client/resource/image/loginPicture.png")
    @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
    ImageResource loginPicture();
}
