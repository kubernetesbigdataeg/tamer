package org.kubernetesbigdataeg.tamer.entities.access;

public class LoginTokenResponse {
    private String token;

    public LoginTokenResponse(String token) {
        setToken(token);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
