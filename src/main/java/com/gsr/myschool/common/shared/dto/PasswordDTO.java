package com.gsr.myschool.common.shared.dto;

import com.gsr.myschool.server.validator.FieldMatch;
import com.gsr.myschool.server.validator.NotBlank;
import com.gsr.myschool.server.validator.Password;

import java.io.Serializable;

@FieldMatch(first = "password", second = "passwordConfirmation", params = {"passwordConfirmation", "Mot de passe"})
public class PasswordDTO implements Serializable {
    @NotBlank
    private String oldPassword;
    @Password
    @NotBlank
    private String password;
    private String passwordConfirmation;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
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
}
