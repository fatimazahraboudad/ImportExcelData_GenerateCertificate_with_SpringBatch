package com.project.StudentsManagement.exceptions;

public class CertificateNotFoundException extends RuntimeException{

    public CertificateNotFoundException(String name) {
        super(ExceptionMessages.CERTIFICATE_NOT_FOUND_FOR_STUDENT.getMessage(name));
    }
}
