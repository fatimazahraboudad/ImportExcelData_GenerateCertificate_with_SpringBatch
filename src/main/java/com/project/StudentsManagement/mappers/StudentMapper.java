package com.project.StudentsManagement.mappers;

import com.project.StudentsManagement.dtos.StudentDto;
import com.project.StudentsManagement.entities.Student;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentMapper mappe = Mappers.getMapper(StudentMapper.class);

    Student toEntity(StudentDto studentDto);

    StudentDto toDto(Student student);

    List<StudentDto> toDtos(List<Student> studentList);

    List<Student> toEntities(List<StudentDto> studentDtoList);
}
