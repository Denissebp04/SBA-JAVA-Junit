package sba.sms.services;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import sba.sms.dao.CourseI;
import sba.sms.models.Course;
import sba.sms.utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * CourseService is a concrete class. This class implements the
 * CourseI interface, overrides all abstract service methods and
 * provides implementation for each method.
 */
public class CourseService implements CourseI {

    private final CourseI courseDao;

    
    public CourseService(CourseI courseDao) {
        this.courseDao = courseDao;
    }

    
    @Override
    public void createCourse(Course course) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.buildSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            courseDao.createCourse(course); 
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

   
    @Override
    public List<Course> getAllCourses() {
        try (Session session = HibernateUtil.buildSessionFactory().openSession()) {
            return courseDao.getAllCourses(); 
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    
    @Override
    public Course getCourseById(int courseId) {
        try (Session session = HibernateUtil.buildSessionFactory().openSession()) {
            return courseDao.getCourseById(courseId); 
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
