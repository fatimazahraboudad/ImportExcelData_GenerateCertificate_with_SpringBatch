package com.project.StudentsManagement.entities;

import com.project.StudentsManagement.enumeration.Emention;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table
@ToString
public class Student {

    @Id
    private String id;
    private String studentName;
    private String email;
    private String mobileNo;
    private double note;
    @Enumerated(EnumType.STRING)
    private Emention mention;


}
