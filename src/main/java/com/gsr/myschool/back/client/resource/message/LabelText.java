package com.gsr.myschool.back.client.resource.message;

import com.google.gwt.i18n.client.Constants;

public interface LabelText extends Constants {

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
