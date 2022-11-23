package org.kubernetesbigdataeg.tamer.utils;

import javax.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomConstraintViolationException extends CustomGenericException {
    public CustomConstraintViolationException(String field, String message) {
        super.setTitle("Constraint Violation");
        super.setStatus(400);
        super.addViolation(new HashMap<>(){{
            put("field",field);
            put("message",message);
        }});
    }

    public CustomConstraintViolationException(List violations) {
        super.setTitle("Constraint Violation");
        super.setStatus(400);
        super.setViolations(violations);
    }

    public CustomConstraintViolationException(Set<? extends ConstraintViolation<?>> violations) {
        super.setTitle("Constraint Violation");
        super.setStatus(400);
        super.setViolations(violations.stream().map(cv -> cv.getMessage()).collect(Collectors.toList()));
    }
}