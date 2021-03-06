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

package com.gsr.myschool.back.client.request;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.gsr.myschool.common.client.proxy.AdminUserProxy;
import com.gsr.myschool.common.client.proxy.UserFilterDTOProxy;
import com.gsr.myschool.common.client.proxy.UserProxy;
import com.gsr.myschool.server.service.impl.UserManagementServiceImpl;
import com.gsr.myschool.server.util.SpringServiceLocator;

import java.util.List;

@Service(value = UserManagementServiceImpl.class, locator = SpringServiceLocator.class)
public interface UserRequest extends RequestContext {
    Request<List<AdminUserProxy>> findAllAdminUser();

	Request<List<UserProxy>> findAllPortalUser();

    Request<Boolean> saveUserAccount(UserProxy userProxy);

    Request<Boolean> saveAdminAccount(AdminUserProxy adminUserProxy);

    Request<Void> deleteAdminUser(Long id);

    Request<List<UserProxy>> findAllUsersByCriteria(UserFilterDTOProxy userFilter);
}
