package com.cvs.service;

import com.cvs.dto.KnowledgePointVO;
import com.cvs.model.Course;
import com.cvs.model.KnowledgePoint;
import com.cvs.model.User;
import com.cvs.repository.KnowledgePointRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KnowledgePointService {

    private final KnowledgePointRepository kpRepository;
    private final CourseService courseService;

    public KnowledgePointService(KnowledgePointRepository kpRepository,
                                 CourseService courseService) {
        this.kpRepository = kpRepository;
        this.courseService = courseService;
    }

    /**
     * 为课程添加知识点（教师）
     */
    public KnowledgePointVO addKnowledgePoint(Long courseId, String name,
                                               String description, User teacher) {
        Course course = courseService.findById(courseId);

        if (!course.getTeacher().getId().equals(teacher.getId())) {
            throw new RuntimeException("无权操作该课程");
        }

        KnowledgePoint kp = new KnowledgePoint(name, description, course);
        kp = kpRepository.save(kp);
        return KnowledgePointVO.fromKnowledgePoint(kp);
    }

    /**
     * 获取课程下的所有知识点
     */
    public List<KnowledgePointVO> getKnowledgePointsByCourse(Long courseId) {
        List<KnowledgePoint> kps = kpRepository.findByCourseId(courseId);
        return kps.stream().map(KnowledgePointVO::fromKnowledgePoint).collect(Collectors.toList());
    }

    /**
     * 删除知识点
     */
    public void deleteKnowledgePoint(Long kpId, User teacher) {
        KnowledgePoint kp = kpRepository.findById(kpId)
                .orElseThrow(() -> new RuntimeException("知识点不存在"));

        if (!kp.getCourse().getTeacher().getId().equals(teacher.getId())) {
            throw new RuntimeException("无权操作");
        }

        kpRepository.delete(kp);
    }

    /**
     * 根据ID查找知识点
     */
    public KnowledgePoint findById(Long id) {
        return kpRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("知识点不存在"));
    }
}
