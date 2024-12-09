package com.project.StudentsManagement.dtos;

import com.project.StudentsManagement.entities.Student;
import com.project.StudentsManagement.enumeration.Emention;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class StudentDto {

    private String id;
    private String studentName;
    private String email;
    private String mobileNo;
    private double note;
    private Emention mention;

}
