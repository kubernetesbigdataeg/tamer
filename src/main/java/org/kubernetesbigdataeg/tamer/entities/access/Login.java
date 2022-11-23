package org.kubernetesbigdataeg.tamer.entities.access;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Login {
    @Schema(required = true, example = "admin@admin.com")
    @NotBlank(message = "username/email may not be blank")
    @Size(min = 5, max = 45, message = "username/email should have size [{min},{max}]")
    @Email
    private String username;

    @Schema(required = true, example = "admin")
    @NotBlank(message = "password may not be blank")
    @Size(min = 8, max = 45, message = "password should have size [{min},{max}]")
    private String password;

    public Login(String username, String password) {
        setUsername(username);
        setPassword(password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
