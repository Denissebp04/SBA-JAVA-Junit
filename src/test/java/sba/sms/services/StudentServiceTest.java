package sba.sms.services;

import lombok.AccessLevel;

import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sba.sms.models.Student;
import sba.sms.utils.CommandLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertNotNull;


class StudentServiceTest {
	 @Test
	    public void testCreateStudent() {
	        Student student = new Student("test@example.com", "John Doe", "password123");
	        StudentService studentService = new StudentService(new studentDAO(), new courseDao());
	        studentService.createStudent(student);
	        assertNotNull(studentService.getStudentByEmail("test@example.com"));
	    }

}