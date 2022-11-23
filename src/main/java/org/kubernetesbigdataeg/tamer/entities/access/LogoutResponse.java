package org.kubernetesbigdataeg.tamer.entities.access;


public class LogoutResponse {
    private String username;

    public LogoutResponse(String username) {
        removeUserToken(username);
    }

    public String removeUserToken(String username) {
        return "ok";
    }
}
