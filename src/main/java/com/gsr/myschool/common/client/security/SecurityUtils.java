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

package com.gsr.myschool.common.client.security;

import com.google.gwt.storage.client.Storage;
import com.google.gwt.storage.client.StorageMap;

import java.util.List;

public class SecurityUtils {
    private final static String USERNAME_OLD = "USERNAME_OLD";
    private final static String PASSWORD_OLD = "PASSWORD_OLD";

    private final StorageMap sessionStorage;

    public SecurityUtils() {
        sessionStorage = new StorageMap(Storage.getSessionStorageIfSupported());
    }

    public void setCredentials(String username, String password) {
        sessionStorage.put(Credentials.USERNAME.name(), username);
        sessionStorage.put(Credentials.PASSWORD.name(), password);
    }

    public void backupCredentials() {
        sessionStorage.put(USERNAME_OLD, getUsername());
        sessionStorage.put(PASSWORD_OLD, getPassword());
    }

    public void restoreBackup() {
        String username = sessionStorage.get(USERNAME_OLD);
        String password = sessionStorage.get(PASSWORD_OLD);

        sessionStorage.put(Credentials.USERNAME.name(), username);
        sessionStorage.put(Credentials.PASSWORD.name(), password);

        sessionStorage.remove(USERNAME_OLD);
        sessionStorage.remove(PASSWORD_OLD);
    }

    public void setAuthorities(List<String> authorities) {
        for (String authority : authorities) {
            sessionStorage.put(authority, authority);
        }
    }

    public void clearCredentials() {
        sessionStorage.clear();
    }

    public void updatePassword(String password) {
        sessionStorage.put(Credentials.PASSWORD.name(), password);
    }

    public void updateUsername(String username) {
        sessionStorage.put(Credentials.USERNAME.name(), username);
    }

    public String getUsername() {
        return sessionStorage.get(Credentials.USERNAME.name());
    }

    public String getPassword() {
        return sessionStorage.get(Credentials.PASSWORD.name());
    }

    public Boolean isLoggedIn() {
        return sessionStorage.containsKey(Credentials.USERNAME.name()) &&
                sessionStorage.containsKey(Credentials.PASSWORD.name());
    }

    public Boolean isSuperUser() {
        return sessionStorage.containsKey(USERNAME_OLD) && sessionStorage.containsKey(PASSWORD_OLD);
    }

    public Boolean hasAuthority(String... authorities) {
        for (String authority : authorities) {
            if (sessionStorage.containsKey(authority)) {
                return true;
            }
        }

        return false;
    }
}
