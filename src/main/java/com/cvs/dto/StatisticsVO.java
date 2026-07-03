package com.cvs.dto;

import java.util.List;
import java.util.Map;

public class StatisticsVO {

    // 课程掌握度概览
    public static class CourseOverview {
        private String courseName;
        private int totalKnowledgePoints;
        private int totalVoteSessions;
        private int totalStudents;
        private double overallMastery; // 0-100

        public String getCourseName() { return courseName; }
        public void setCourseName(String courseName) { this.courseName = courseName; }
        public int getTotalKnowledgePoints() { return totalKnowledgePoints; }
        public void setTotalKnowledgePoints(int totalKnowledgePoints) { this.totalKnowledgePoints = totalKnowledgePoints; }
        public int getTotalVoteSessions() { return totalVoteSessions; }
        public void setTotalVoteSessions(int totalVoteSessions) { this.totalVoteSessions = totalVoteSessions; }
        public int getTotalStudents() { return totalStudents; }
        public void setTotalStudents(int totalStudents) { this.totalStudents = totalStudents; }
        public double getOverallMastery() { return overallMastery; }
        public void setOverallMastery(double overallMastery) { this.overallMastery = overallMastery; }
    }

    // 知识点掌握度
    public static class KnowledgePointMastery {
        private Long knowledgePointId;
        private String knowledgePointName;
        private int totalVotes;
        private int correctVotes;
        private double masteryRate; // 0-100

        public Long getKnowledgePointId() { return knowledgePointId; }
        public void setKnowledgePointId(Long knowledgePointId) { this.knowledgePointId = knowledgePointId; }
        public String getKnowledgePointName() { return knowledgePointName; }
        public void setKnowledgePointName(String knowledgePointName) { this.knowledgePointName = knowledgePointName; }
        public int getTotalVotes() { return totalVotes; }
        public void setTotalVotes(int totalVotes) { this.totalVotes = totalVotes; }
        public int getCorrectVotes() { return correctVotes; }
        public void setCorrectVotes(int correctVotes) { this.correctVotes = correctVotes; }
        public double getMasteryRate() { return masteryRate; }
        public void setMasteryRate(double masteryRate) { this.masteryRate = masteryRate; }
    }

    // 学生掌握度
    public static class StudentMastery {
        private Long studentId;
        private String studentName;
        private int totalVotes;
        private int correctVotes;
        private double masteryRate; // 0-100

        public Long getStudentId() { return studentId; }
        public void setStudentId(Long studentId) { this.studentId = studentId; }
        public String getStudentName() { return studentName; }
        public void setStudentName(String studentName) { this.studentName = studentName; }
        public int getTotalVotes() { return totalVotes; }
        public void setTotalVotes(int totalVotes) { this.totalVotes = totalVotes; }
        public int getCorrectVotes() { return correctVotes; }
        public void setCorrectVotes(int correctVotes) { this.correctVotes = correctVotes; }
        public double getMasteryRate() { return masteryRate; }
        public void setMasteryRate(double masteryRate) { this.masteryRate = masteryRate; }
    }
}
