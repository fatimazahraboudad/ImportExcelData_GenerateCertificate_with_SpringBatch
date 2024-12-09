package com.project.StudentsManagement.exceptions;

import jakarta.persistence.Id;

public class StudentNotFoundException extends RuntimeException{

    public StudentNotFoundException(String id) {
        super(ExceptionMessages.STUDENT_NOT_FOUND.getMessage(id));
    }
}
