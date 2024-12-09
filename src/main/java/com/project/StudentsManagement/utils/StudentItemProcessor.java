package com.project.StudentsManagement.utils;

import com.project.StudentsManagement.dtos.StudentDto;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class StudentItemProcessor implements ItemProcessor<StudentDto, StudentDto> {

    @Override
    public StudentDto process(StudentDto student) {
        if (student.getStudentName() == null || student.getStudentName().isEmpty() ||
                student.getNote() < 0 || student.getNote() > 20) {
            throw new IllegalArgumentException("Invalid data for student: " + student);
        }
        return student;
    }
}

