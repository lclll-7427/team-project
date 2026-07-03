package com.cvs.service;

import com.cvs.dto.StatisticsVO;
import com.cvs.model.*;
import com.cvs.repository.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    private final VoteSessionRepository voteSessionRepository;
    private final VoteOptionRepository voteOptionRepository;
    private final VoteRecordRepository voteRecordRepository;
    private final KnowledgePointRepository knowledgePointRepository;
    private final CourseService courseService;

    public StatisticsService(VoteSessionRepository voteSessionRepository,
                             VoteOptionRepository voteOptionRepository,
                             VoteRecordRepository voteRecordRepository,
                             KnowledgePointRepository knowledgePointRepository,
                             CourseService courseService) {
        this.voteSessionRepository = voteSessionRepository;
        this.voteOptionRepository = voteOptionRepository;
        this.voteRecordRepository = voteRecordRepository;
        this.knowledgePointRepository = knowledgePointRepository;
        this.courseService = courseService;
    }

    /**
     * 课程掌握度概览
     */
    public StatisticsVO.CourseOverview getCourseOverview(Long courseId) {
        Course course = courseService.findById(courseId);
        List<KnowledgePoint> kps = knowledgePointRepository.findByCourseId(courseId);
        List<VoteSession> sessions = voteSessionRepository.findByCourseId(courseId);
        List<User> students = courseService.getCourseStudents(courseId);

        // 计算总体掌握度
        double overallMastery = 0;
        if (!sessions.isEmpty()) {
            List<VoteRecord> allRecords = new ArrayList<>();
            for (VoteSession s : sessions) {
                allRecords.addAll(voteRecordRepository.findByVoteSession(s));
            }

            long correctCount = allRecords.stream()
                    .filter(r -> r.getOption().getIsCorrect())
                    .count();

            overallMastery = allRecords.isEmpty() ? 0 :
                    Math.round((double) correctCount / allRecords.size() * 10000.0) / 100.0;
        }

        StatisticsVO.CourseOverview overview = new StatisticsVO.CourseOverview();
        overview.setCourseName(course.getName());
        overview.setTotalKnowledgePoints(kps.size());
        overview.setTotalVoteSessions(sessions.size());
        overview.setTotalStudents(students.size());
        overview.setOverallMastery(overallMastery);
        return overview;
    }

    /**
     * 各知识点掌握度统计
     */
    public List<StatisticsVO.KnowledgePointMastery> getKnowledgePointMastery(Long courseId) {
        List<KnowledgePoint> kps = knowledgePointRepository.findByCourseId(courseId);
        List<StatisticsVO.KnowledgePointMastery> result = new ArrayList<>();

        for (KnowledgePoint kp : kps) {
            List<VoteSession> sessions = voteSessionRepository.findByKnowledgePoint(kp);
            List<VoteRecord> allRecords = new ArrayList<>();
            for (VoteSession s : sessions) {
                allRecords.addAll(voteRecordRepository.findByVoteSession(s));
            }

            long correctCount = allRecords.stream()
                    .filter(r -> r.getOption().getIsCorrect())
                    .count();

            double rate = allRecords.isEmpty() ? 0 :
                    Math.round((double) correctCount / allRecords.size() * 10000.0) / 100.0;

            StatisticsVO.KnowledgePointMastery mastery = new StatisticsVO.KnowledgePointMastery();
            mastery.setKnowledgePointId(kp.getId());
            mastery.setKnowledgePointName(kp.getName());
            mastery.setTotalVotes(allRecords.size());
            mastery.setCorrectVotes((int) correctCount);
            mastery.setMasteryRate(rate);
            result.add(mastery);
        }

        return result;
    }

    /**
     * 各学生掌握度统计
     */
    public List<StatisticsVO.StudentMastery> getStudentMastery(Long courseId) {
        List<User> students = courseService.getCourseStudents(courseId);
        List<StatisticsVO.StudentMastery> result = new ArrayList<>();

        for (User student : students) {
            List<VoteRecord> records = voteRecordRepository.findByStudent(student);

            // 过滤该课程相关的记录
            records = records.stream()
                    .filter(r -> r.getVoteSession().getCourse().getId().equals(courseId))
                    .collect(Collectors.toList());

            long correctCount = records.stream()
                    .filter(r -> r.getOption().getIsCorrect())
                    .count();

            double rate = records.isEmpty() ? 0 :
                    Math.round((double) correctCount / records.size() * 10000.0) / 100.0;

            StatisticsVO.StudentMastery mastery = new StatisticsVO.StudentMastery();
            mastery.setStudentId(student.getId());
            mastery.setStudentName(student.getRealName());
            mastery.setTotalVotes(records.size());
            mastery.setCorrectVotes((int) correctCount);
            mastery.setMasteryRate(rate);
            result.add(mastery);
        }

        return result;
    }
}
