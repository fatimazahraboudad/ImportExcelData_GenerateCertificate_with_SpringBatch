package com.project.StudentsManagement.exceptions;

public class InvalidDataException extends RuntimeException{

    public InvalidDataException(String name) {
        super(ExceptionMessages.STUDENT_NAME_INVALID.getMessage(name));
    }

    public InvalidDataException(double note) {
        super(ExceptionMessages.STUDENT_NOTE_INVALID.getMessage(String.valueOf(note)));
    }

}
