package com.cvs.dto;

import com.cvs.model.KnowledgePoint;

public class KnowledgePointVO {

    private Long id;
    private String name;
    private String description;
    private Long courseId;

    public static KnowledgePointVO fromKnowledgePoint(KnowledgePoint kp) {
        KnowledgePointVO vo = new KnowledgePointVO();
        vo.setId(kp.getId());
        vo.setName(kp.getName());
        vo.setDescription(kp.getDescription());
        vo.setCourseId(kp.getCourse().getId());
        return vo;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
}
