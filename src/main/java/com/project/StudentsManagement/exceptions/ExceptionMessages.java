package com.project.StudentsManagement.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessages {

    STUDENT_NOT_FOUND("Student with id %s not found."),
    CERTIFICATE_NOT_FOUND_FOR_STUDENT("Certificate not found for student %s"),
    STUDENT_NAME_INVALID("Student with name %s is invalid."),
    STUDENT_NOTE_INVALID("Student grade %s is invalid, out of range (0-20).");



    private final String message;


    public String getMessage(String... args) {
        return String.format(message, (Object[]) args);
    }

}