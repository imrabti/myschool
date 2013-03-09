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

package com.gsr.myschool.server.service.impl;

import com.gsr.myschool.common.shared.type.SettingsKey;
import com.gsr.myschool.server.business.Settings;
import com.gsr.myschool.server.repos.SettingsRepos;
import com.gsr.myschool.server.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SettingServiceImpl implements SettingService {
    @Autowired
    private SettingsRepos settingsRepos;

    @Override
    public void updateSettings(SettingsKey key, String value) {
        Settings setting = settingsRepos.findOne(key);
        setting.setValue(value);
        settingsRepos.save(setting);
    }

    @Override
    public String getSetting(SettingsKey key) {
        Settings setting = settingsRepos.findOne(key);
        return setting != null ? setting.getValue() : "";
    }
}
