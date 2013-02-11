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

import com.gsr.myschool.common.shared.type.UserStatus;
import com.gsr.myschool.server.business.AdminUser;
import com.gsr.myschool.server.repos.AdminUserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("adminUserDetailsService")
public class StatelessAdminDetailService implements UserDetailsService {
    @Autowired
    private AdminUserRepos adminUserRepos;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AdminUser user = adminUserRepos.findByEmail(email);
        if (user == null || user.getStatus() == UserStatus.INACTIVE) {
            throw new UsernameNotFoundException("Bad credentials");
        } else {
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority(user.getAuthority().name()));
            return new User(user.getEmail(), user.getPassword(), user.getStatus() == UserStatus.ACTIVE,
                    true, true, true, authorities);
        }
    }
}
