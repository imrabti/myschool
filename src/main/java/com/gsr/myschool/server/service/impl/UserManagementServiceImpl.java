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

import com.gsr.myschool.server.business.AdminUser;
import com.gsr.myschool.server.business.User;
import com.gsr.myschool.server.repos.AdminUserRepos;
import com.gsr.myschool.server.repos.UserRepos;
import com.gsr.myschool.server.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserManagementServiceImpl implements UserManagementService {
    @Autowired
    private UserRepos userRepos;
    @Autowired
    private AdminUserRepos adminUserRepos;

    @Override
    @Transactional(readOnly = true)
    public List<AdminUser> findAllAdminUser() {
        List<AdminUser> adminUsers = new ArrayList<AdminUser>();
        adminUsers.addAll(adminUserRepos.findAll());

        return adminUsers;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllPortalUser() {
        List<User> users = new ArrayList<User>();
        users.addAll(userRepos.findAll());

        return users;
    }

    @Override
    public Boolean updateUserAccount(User user) {
        User userToUpdate = userRepos.findOne(user.getId());
        return true;
    }

    @Override
    public Boolean updateAdminAccount(AdminUser admin) {
        AdminUser adminToUpdate = adminUserRepos.findOne(admin.getId());
        return true;
    }

    @Override
    public Boolean createAdminAccount(AdminUser newAdmin) {
        try {
            adminUserRepos.save(newAdmin);
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}