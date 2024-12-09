package com.project.StudentsManagement.utils;

import com.project.StudentsManagement.dtos.StudentDto;
import com.project.StudentsManagement.services.StudentService;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
public class StudentItemReader implements ItemReader<StudentDto> {

    private final StudentService studentService;
    private Iterator<StudentDto> studentIterator;

    public StudentItemReader(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public StudentDto read() {
        if (studentIterator == null) {
            studentIterator = studentService.getAll().iterator();
        }
        return studentIterator.hasNext() ? studentIterator.next() : null;
    }
}

