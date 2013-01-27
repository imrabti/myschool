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

package com.gsr.myschool.back.client.resource.message;

import com.google.gwt.i18n.client.Messages;

public interface MessageBundle extends Messages {
    String wrongLoginOrPassword();

    String loginPasswordRequired();

    String registerInfoMissing();

    String welcomeMessage(String username);

    String cannotDeleteDefLovException();

    String deleteValueListSuccess();

    @Key("labels.name")
    String labelsName();

    @Key("labels.parent")
    String labelsParent();

    @Key("labels.regex")
    String labelsRegex();

    @Key("labels.age")
    String labelsAge();

    @Key("labels.from")
    String labelsFrom();

    @Key("labels.definition")
    String labelsDefinition();

    @Key("labels.refField")
    String labelsRefField();

    @Key("labels.value")
    String labelsValue();

    @Key("labels.javaType")
    String labelsJavaType();

    @Key("buttons.save")
    String buttonsSave();

    @Key("buttons.delete")
    String buttonsDelete();

    @Key("buttons.modify")
    String buttonsModify();

    @Key("menus.valueList.listDefLov")
    String menusSettingsListDefLov();

    @Key("menus.valueList.addDefLov")
    String menusSettingsAddDefLov();

    @Key("menus.valueList.listLOV")
    String menusSettingsListLOV();

    @Key("menus.valueList.addLOV")
    String menusSettingsAddLOV();

    @Key("labels.systemDefLov")
    String labelsSystemDefLov();

    @Key("labels.label")
    String labelsLabel();
}
