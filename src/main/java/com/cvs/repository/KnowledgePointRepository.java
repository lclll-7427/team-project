package com.cvs.repository;

import com.cvs.model.Course;
import com.cvs.model.KnowledgePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface KnowledgePointRepository extends JpaRepository<KnowledgePoint, Long> {
    List<KnowledgePoint> findByCourse(Course course);
    List<KnowledgePoint> findByCourseId(Long courseId);
}
