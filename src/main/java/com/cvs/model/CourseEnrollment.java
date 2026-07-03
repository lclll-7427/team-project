package com.cvs.model;

import jakarta.persistence.*;

@Entity
@Table(name = "course_enrollments",
       uniqueConstraints = @UniqueConstraint(columnNames = {"course_id", "student_id"}))
public class CourseEnrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    public CourseEnrollment() {}

    public CourseEnrollment(Course course, User student) {
        this.course = course;
        this.student = student;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
    public User getStudent() { return student; }
    public void setStudent(User student) { this.student = student; }
}
