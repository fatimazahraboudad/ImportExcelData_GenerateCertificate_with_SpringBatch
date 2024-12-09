package com.project.StudentsManagement.utils;

import com.project.StudentsManagement.entities.Student;
import com.project.StudentsManagement.enumeration.Emention;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ExcelUtility {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }
    public static List<Student> excelToStuList(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);
            List<Student> students = new ArrayList<>();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    Student student = new Student();
                    if (row.getCell(0)==null) {
                        student.setId(UUID.randomUUID().toString());
                    }
                    student.setId((row.getCell(0) != null
                            ? row.getCell(0).getStringCellValue()
                            : UUID.randomUUID().toString()));
                    student.setStudentName((row.getCell(1) != null
                            ? row.getCell(1).getStringCellValue()
                            : ""));
                    student.setEmail((row.getCell(2) != null
                            ? row.getCell(2).getStringCellValue()
                            :""));
                    student.setMobileNo((row.getCell(3) != null && row.getCell(3).getCellType() == CellType.NUMERIC)
                            ? String.valueOf((long) row.getCell(3).getNumericCellValue())
                            : (row.getCell(3) != null && row.getCell(3).getCellType() == CellType.STRING)
                            ? row.getCell(3).getStringCellValue()
                            : "");
                    student.setNote(row.getCell(4).getNumericCellValue());
                    student.setMention(student.getNote()<10 ? Emention.FAILED : Emention.PASSED );

                    students.add(student);
                }
            }
            workbook.close();
            return students;
        } catch (IOException e) {
            throw new RuntimeException("failed to parse Excel file: " + e.getMessage());
        }
    }
}