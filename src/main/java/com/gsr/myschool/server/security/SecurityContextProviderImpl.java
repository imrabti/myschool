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

package com.gsr.myschool.server.security;

import com.gsr.myschool.server.business.AdminUser;
import com.gsr.myschool.server.business.User;
import com.gsr.myschool.server.repos.AdminUserRepos;
import com.gsr.myschool.server.repos.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SecurityContextProviderImpl implements SecurityContextProvider {
    @Autowired
    private UserRepos userRepos;
	@Autowired
	private AdminUserRepos adminUserRepos;

    @Override
    @Transactional(readOnly = true)
    public User getCurrentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext != null) {
            String email = securityContext.getAuthentication().getName();
            return userRepos.findByEmail(email);
        }

        return null;
    }

	@Override
	@Transactional(readOnly = true)
	public AdminUser getCurrentAdmin() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if (securityContext != null) {
			String email = securityContext.getAuthentication().getName();
			return adminUserRepos.findByEmail(email);
		}

		return null;
	}
}
