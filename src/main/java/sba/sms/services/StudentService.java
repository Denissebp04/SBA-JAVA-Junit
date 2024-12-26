package sba.sms.services;

import lombok.extern.java.Log;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import sba.sms.dao.StudentI;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * StudentService is a concrete class. This class implements the
 * StudentI interface, overrides all abstract service methods and
 * provides implementation for each method. Lombok @Log used to
 * generate a logger file.
 */

@Log
public class StudentService implements StudentI {

    private final StudentI studentDao;

    
    public StudentService(StudentI studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public void createStudent(Student student) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.buildSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            studentDao.createStudent(student); 
            transaction.commit();
            Log.info("Student created successfully: {}", student.getEmail());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            Log.error("Error creating student: {}", student.getEmail(), e); 
        }
    }

    @Override
    public List<Student> getAllStudents() {
        try (Session session = HibernateUtil.buildSessionFactory().openSession()) {
            List<Student> students = studentDao.getAllStudents(); 
            Log.info("Retrieved {} students from database", students.size()); 
            return students;
        } catch (Exception e) {
            Log.error("Error retrieving all students", e); 
            return null;
        }
    }

    @Override
    public Student getStudentByEmail(String email) {
        try (Session session = HibernateUtil.buildSessionFactory().openSession()) {
            Student student = studentDao.getStudentByEmail(email); 
            if (student != null) {
                Log.info("Student found: {}", student.getEmail()); 
            } else {
                Log.warn("Student with email {} not found", email); 
            }
            return student;
        } catch (Exception e) {
            Log.error("Error retrieving student with email: {}", email, e); 
            return null;
        }
    }

    @Override
    public boolean validateStudent(String email, String password) {
        try (Session session = HibernateUtil.buildSessionFactory().openSession()) {
            boolean isValid = studentDao.validateStudent(email, password); 
            if (isValid) {
                Log.info("Student login successful: {}", email); 
            } else {
                Log.warn("Invalid credentials for student: {}", email); 
            }
            return isValid;
        } catch (Exception e) {
            Log.error("Error validating student credentials for email: {}", email, e); 
            return false;
        }
    }

    @Override
    public void registerStudentToCourse(String email, int courseId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.buildSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            studentDao.registerStudentToCourse(email, courseId); 
            transaction.commit();
            Log.info("Student with email {} registered to course {}", email, courseId); 
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            Log.error("Error registering student with email {} to course {}", email, courseId, e); 
        }
    }

    
    @Override
    public List<Course> getStudentCourses(String email) {
        try (Session session = HibernateUtil.buildSessionFactory().openSession()) {
            List<Course> courses = studentDao.getStudentCourses(email); 
            Log.info("Retrieved {} courses for student with email {}", courses.size(), email); 
            return courses;
        } catch (Exception e) {
            Log.error("Error retrieving courses for student with email {}", email, e); 
            return null;
        }
    }
}
