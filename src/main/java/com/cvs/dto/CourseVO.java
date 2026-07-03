package com.cvs.dto;

import com.cvs.model.Course;

public class CourseVO {

    private Long id;
    private String name;
    private String description;
    private String teacherName;
    private Long teacherId;

    public static CourseVO fromCourse(Course course) {
        CourseVO vo = new CourseVO();
        vo.setId(course.getId());
        vo.setName(course.getName());
        vo.setDescription(course.getDescription());
        vo.setTeacherName(course.getTeacher().getRealName());
        vo.setTeacherId(course.getTeacher().getId());
        return vo;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }
    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }
}
