package com.gsr.myschool.server.business;

import com.gsr.myschool.common.shared.type.Authority;
import com.gsr.myschool.server.validator.Email;
import com.gsr.myschool.server.validator.FieldMatch;
import com.gsr.myschool.server.validator.Name;
import com.gsr.myschool.server.validator.NotBlank;
import com.gsr.myschool.server.validator.Password;
import com.gsr.myschool.server.validator.Unique;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Entity
@Unique.List({
        @Unique(entity = User.class, property = "email", params = "email"),
        @Unique(entity = User.class, property = "username", params = "username")
})
@FieldMatch(first = "password", second = "passwordConfirmation", params = {"passwordConfirmation", "Mot de passe" })
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String username;
    @Email
    @NotBlank
    private String email;
    @Password
    @NotBlank
    private String password;
    @Transient
    private String passwordConfirmation;
    @Name
    @NotBlank
    private String firstName;
    @Name
    @NotBlank
    private String lastName;
    @Enumerated
    private Authority authority;
    private Boolean active;
    private Date created;
    private Date updated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
