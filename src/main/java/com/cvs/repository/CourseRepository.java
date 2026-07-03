package com.cvs.repository;

import com.cvs.model.Course;
import com.cvs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByTeacher(User teacher);
    List<Course> findByTeacherId(Long teacherId);
}
