package com.cvs.service;

import com.cvs.dto.CreateVoteRequest;
import com.cvs.dto.VoteSessionVO;
import com.cvs.model.*;
import com.cvs.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VoteService {

    private final VoteSessionRepository voteSessionRepository;
    private final VoteOptionRepository voteOptionRepository;
    private final VoteRecordRepository voteRecordRepository;
    private final CourseService courseService;
    private final KnowledgePointService knowledgePointService;

    public VoteService(VoteSessionRepository voteSessionRepository,
                       VoteOptionRepository voteOptionRepository,
                       VoteRecordRepository voteRecordRepository,
                       CourseService courseService,
                       KnowledgePointService knowledgePointService) {
        this.voteSessionRepository = voteSessionRepository;
        this.voteOptionRepository = voteOptionRepository;
        this.voteRecordRepository = voteRecordRepository;
        this.courseService = courseService;
        this.knowledgePointService = knowledgePointService;
    }

    /**
     * 创建投票（教师）
     */
    @Transactional
    public VoteSessionVO createVote(CreateVoteRequest request, User teacher) {
        Course course = courseService.findById(request.getCourseId());
        KnowledgePoint kp = knowledgePointService.findById(request.getKnowledgePointId());

        if (!course.getTeacher().getId().equals(teacher.getId())) {
            throw new RuntimeException("无权操作");
        }

        VoteSession session = new VoteSession(request.getTitle(), course, kp, teacher);
        session = voteSessionRepository.save(session);

        for (CreateVoteRequest.OptionItem item : request.getOptions()) {
            VoteOption option = new VoteOption(item.getText(), item.getIsCorrect(), session);
            voteOptionRepository.save(option);
        }

        List<VoteOption> options = voteOptionRepository.findByVoteSession(session);
        return VoteSessionVO.fromVoteSession(session, options, 0);
    }

    /**
     * 获取投票详情（含选项和票数统计）
     */
    public VoteSessionVO getVoteDetail(Long sessionId) {
        VoteSession session = voteSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("投票不存在"));

        List<VoteOption> options = voteOptionRepository.findByVoteSession(session);
        List<VoteRecord> records = voteRecordRepository.findByVoteSession(session);

        // 统计每个选项的票数
        Map<Long, Long> optionVoteCount = records.stream()
                .collect(Collectors.groupingBy(r -> r.getOption().getId(), Collectors.counting()));

        VoteSessionVO vo = VoteSessionVO.fromVoteSession(session, options, records.size());

        for (VoteSessionVO.OptionVO optVO : vo.getOptions()) {
            optVO.setVoteCount(optionVoteCount.getOrDefault(optVO.getId(), 0L));
        }

        return vo;
    }

    /**
     * 提交投票（学生）
     */
    @Transactional
    public void castVote(Long sessionId, Long optionId, User student) {
        VoteSession session = voteSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("投票不存在"));

        if (session.getStatus() == VoteSession.Status.CLOSED) {
            throw new RuntimeException("投票已关闭");
        }

        if (!student.getRole().equals(User.Role.STUDENT)) {
            throw new RuntimeException("仅学生可投票");
        }

        if (voteRecordRepository.existsByVoteSessionAndStudent(session, student)) {
            throw new RuntimeException("你已投过票");
        }

        VoteOption option = voteOptionRepository.findById(optionId)
                .orElseThrow(() -> new RuntimeException("选项不存在"));

        if (!option.getVoteSession().getId().equals(sessionId)) {
            throw new RuntimeException("选项不属于该投票");
        }

        VoteRecord record = new VoteRecord(session, option, student);
        voteRecordRepository.save(record);
    }

    /**
     * 关闭投票（教师）
     */
    @Transactional
    public void closeVote(Long sessionId, User teacher) {
        VoteSession session = voteSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("投票不存在"));

        if (!session.getTeacher().getId().equals(teacher.getId())) {
            throw new RuntimeException("无权操作");
        }

        session.setStatus(VoteSession.Status.CLOSED);
        voteSessionRepository.save(session);
    }

    /**
     * 获取课程下的所有投票
     */
    public List<VoteSessionVO> getCourseVotes(Long courseId) {
        List<VoteSession> sessions = voteSessionRepository.findByCourseId(courseId);
        return sessions.stream().map(s -> {
            List<VoteOption> options = voteOptionRepository.findByVoteSession(s);
            List<VoteRecord> records = voteRecordRepository.findByVoteSession(s);
            return VoteSessionVO.fromVoteSession(s, options, records.size());
        }).collect(Collectors.toList());
    }
}
