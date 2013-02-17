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

package com.gsr.myschool.back.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.gwtplatform.mvp.client.ApplicationController;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MySchoolBack implements EntryPoint {
    private static final Logger log = Logger.getLogger(MySchoolBack.class.getName());
    private static ApplicationController controller = GWT.create(ApplicationController.class);

    @Override
    public void onModuleLoad() {
        controller.init();

        GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
            public void onUncaughtException(Throwable e) {
                log.log(Level.SEVERE, e.getMessage(), e);
            }
        });
    }
}
