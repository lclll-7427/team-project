package com.cvs.repository;

import com.cvs.model.Course;
import com.cvs.model.CourseEnrollment;
import com.cvs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CourseEnrollmentRepository extends JpaRepository<CourseEnrollment, Long> {
    List<CourseEnrollment> findByStudent(User student);
    List<CourseEnrollment> findByCourse(Course course);
    Optional<CourseEnrollment> findByCourseAndStudent(Course course, User student);
    boolean existsByCourseAndStudent(Course course, User student);
}
