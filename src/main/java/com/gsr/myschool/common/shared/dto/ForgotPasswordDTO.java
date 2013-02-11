package com.gsr.myschool.common.shared.dto;

import com.gsr.myschool.server.validator.Email;
import com.gsr.myschool.server.validator.NotBlank;

import java.io.Serializable;

public class ForgotPasswordDTO implements Serializable {
    @Email
    @NotBlank
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
