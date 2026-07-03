package com.cvs.repository;

import com.cvs.model.Course;
import com.cvs.model.KnowledgePoint;
import com.cvs.model.VoteSession;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VoteSessionRepository extends JpaRepository<VoteSession, Long> {
    List<VoteSession> findByCourse(Course course);
    List<VoteSession> findByCourseId(Long courseId);
    List<VoteSession> findByKnowledgePoint(KnowledgePoint knowledgePoint);
    List<VoteSession> findByTeacherId(Long teacherId);
}
