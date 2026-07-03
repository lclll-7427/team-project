package com.cvs.service;

import com.cvs.dto.CourseVO;
import com.cvs.model.Course;
import com.cvs.model.CourseEnrollment;
import com.cvs.model.User;
import com.cvs.repository.CourseEnrollmentRepository;
import com.cvs.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseEnrollmentRepository enrollmentRepository;

    public CourseService(CourseRepository courseRepository,
                         CourseEnrollmentRepository enrollmentRepository) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    /**
     * 创建课程（教师）
     */
    public CourseVO createCourse(String name, String description, User teacher) {
        if (!teacher.getRole().equals(User.Role.TEACHER)) {
            throw new RuntimeException("仅教师可创建课程");
        }
        Course course = new Course(name, description, teacher);
        course = courseRepository.save(course);
        return CourseVO.fromCourse(course);
    }

    /**
     * 获取教师创建的课程列表
     */
    public List<CourseVO> getTeacherCourses(User teacher) {
        List<Course> courses = courseRepository.findByTeacher(teacher);
        return courses.stream().map(CourseVO::fromCourse).collect(Collectors.toList());
    }

    /**
     * 获取学生已选课程列表
     */
    public List<CourseVO> getStudentCourses(User student) {
        List<CourseEnrollment> enrollments = enrollmentRepository.findByStudent(student);
        return enrollments.stream()
                .map(e -> CourseVO.fromCourse(e.getCourse()))
                .collect(Collectors.toList());
    }

    /**
     * 获取课程详情
     */
    public Course findById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("课程不存在"));
    }

    /**
     * 学生选课
     */
    public void enrollCourse(Long courseId, User student) {
        Course course = findById(courseId);

        if (!student.getRole().equals(User.Role.STUDENT)) {
            throw new RuntimeException("仅学生可加入课程");
        }

        if (enrollmentRepository.existsByCourseAndStudent(course, student)) {
            throw new RuntimeException("你已加入该课程");
        }

        CourseEnrollment enrollment = new CourseEnrollment(course, student);
        enrollmentRepository.save(enrollment);
    }

    /**
     * 删除课程
     */
    public void deleteCourse(Long courseId, User teacher) {
        Course course = findById(courseId);
        if (!course.getTeacher().getId().equals(teacher.getId())) {
            throw new RuntimeException("无权删除该课程");
        }
        courseRepository.delete(course);
    }

    /**
     * 获取课程的所有学生
     */
    public List<User> getCourseStudents(Long courseId) {
        Course course = findById(courseId);
        List<CourseEnrollment> enrollments = enrollmentRepository.findByCourse(course);
        return enrollments.stream()
                .map(CourseEnrollment::getStudent)
                .collect(Collectors.toList());
    }
}
