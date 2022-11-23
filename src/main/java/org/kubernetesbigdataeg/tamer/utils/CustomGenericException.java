package org.kubernetesbigdataeg.tamer.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomGenericException {
    private String title;
    private int status;
    private List violations = new ArrayList<>();

    public CustomGenericException() {
    }

    public CustomGenericException(String title, int status, List violations) {
        this.setTitle(title);
        this.setStatus(status);
        this.setViolations(violations);
    }

    public CustomGenericException(String title, int status, Map violation) {
        this.setTitle(title);
        this.setStatus(status);
        this.addViolation(violation);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Map> getViolations() {
        return violations;
    }

    public void setViolations(List violations) {
        this.violations = violations;
    }

    public void addViolation(Map violation) {
        this.violations.add(violation);
    }

    public void addViolation(String violation) {
        this.violations.add(violation);
    }
}