package org.kubernetesbigdataeg.tamer.entities.access;

import java.util.Set;

public class LoginInfoResponse {
    private String username;
    private Set<String> groups;
    private Long expirationtime;
    
    public LoginInfoResponse(String username, Set<String> groups, Long expirationtime){
        setUsername(username);
        setGroups(groups);
        setExpirationtime(expirationtime);
    }

    public String getUsername() {
        return username;
    }

    public Set<String> getGroups() {
        return groups;
    }

    public Long getExpirationtime() {
        return expirationtime;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setGroups(Set<String> groups) {
        this.groups = groups;
    }

    public void setExpirationtime(Long expirationtime) {
        this.expirationtime = expirationtime;
    }
}
