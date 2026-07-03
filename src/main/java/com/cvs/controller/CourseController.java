package com.cvs.controller;

import com.cvs.dto.ApiResponse;
import com.cvs.dto.CourseVO;
import com.cvs.model.User;
import com.cvs.service.CourseService;
import com.cvs.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;
    private final UserService userService;

    public CourseController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    /**
     * 获取课程列表
     * 教师：返回自己创建的课程
     * 学生：返回已选的课程
     */
    @GetMapping
    public ApiResponse<List<CourseVO>> listCourses(
            @RequestParam Long userId,
            @RequestParam String role) {
        try {
            User user = userService.findById(userId);
            List<CourseVO> courses;

            if ("TEACHER".equalsIgnoreCase(role)) {
                courses = courseService.getTeacherCourses(user);
            } else {
                courses = courseService.getStudentCourses(user);
            }

            return ApiResponse.success(courses);
        } catch (RuntimeException e) {
            return ApiResponse.error(400, e.getMessage());
        }
    }

    /**
     * 创建课程（教师）
     */
    @PostMapping
    public ApiResponse<CourseVO> createCourse(@RequestBody Map<String, String> body) {
        try {
            Long teacherId = Long.parseLong(body.get("teacherId"));
            String name = body.get("name");
            String description = body.getOrDefault("description", "");

            User teacher = userService.findById(teacherId);
            CourseVO course = courseService.createCourse(name, description, teacher);
            return ApiResponse.success("课程创建成功", course);
        } catch (RuntimeException e) {
            return ApiResponse.error(400, e.getMessage());
        }
    }

    /**
     * 学生加入课程
     */
    @PostMapping("/{courseId}/enroll")
    public ApiResponse<Void> enrollCourse(
            @PathVariable Long courseId,
            @RequestBody Map<String, Long> body) {
        try {
            Long studentId = body.get("studentId");
            User student = userService.findById(studentId);
            courseService.enrollCourse(courseId, student);
            return ApiResponse.success("加入课程成功", null);
        } catch (RuntimeException e) {
            return ApiResponse.error(400, e.getMessage());
        }
    }

    /**
     * 删除课程（教师）
     */
    @DeleteMapping("/{courseId}")
    public ApiResponse<Void> deleteCourse(
            @PathVariable Long courseId,
            @RequestParam Long teacherId) {
        try {
            User teacher = userService.findById(teacherId);
            courseService.deleteCourse(courseId, teacher);
            return ApiResponse.success("课程已删除", null);
        } catch (RuntimeException e) {
            return ApiResponse.error(400, e.getMessage());
        }
    }
}
