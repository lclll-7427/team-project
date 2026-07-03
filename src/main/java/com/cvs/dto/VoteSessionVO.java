package com.cvs.dto;

import com.cvs.model.VoteOption;
import com.cvs.model.VoteSession;
import java.util.List;
import java.util.stream.Collectors;

public class VoteSessionVO {

    private Long id;
    private String title;
    private Long courseId;
    private Long knowledgePointId;
    private String knowledgePointName;
    private String status;
    private List<OptionVO> options;
    private int totalVotes;

    public static VoteSessionVO fromVoteSession(VoteSession session, List<VoteOption> options, int totalVotes) {
        VoteSessionVO vo = new VoteSessionVO();
        vo.setId(session.getId());
        vo.setTitle(session.getTitle());
        vo.setCourseId(session.getCourse().getId());
        vo.setKnowledgePointId(session.getKnowledgePoint().getId());
        vo.setKnowledgePointName(session.getKnowledgePoint().getName());
        vo.setStatus(session.getStatus().name());
        vo.setOptions(options.stream().map(OptionVO::fromOption).collect(Collectors.toList()));
        vo.setTotalVotes(totalVotes);
        return vo;
    }

    public static class OptionVO {
        private Long id;
        private String text;
        private long voteCount;

        public static OptionVO fromOption(VoteOption option) {
            OptionVO vo = new OptionVO();
            vo.setId(option.getId());
            vo.setText(option.getText());
            return vo;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
        public long getVoteCount() { return voteCount; }
        public void setVoteCount(long voteCount) { this.voteCount = voteCount; }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
    public Long getKnowledgePointId() { return knowledgePointId; }
    public void setKnowledgePointId(Long knowledgePointId) { this.knowledgePointId = knowledgePointId; }
    public String getKnowledgePointName() { return knowledgePointName; }
    public void setKnowledgePointName(String knowledgePointName) { this.knowledgePointName = knowledgePointName; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<OptionVO> getOptions() { return options; }
    public void setOptions(List<OptionVO> options) { this.options = options; }
    public int getTotalVotes() { return totalVotes; }
    public void setTotalVotes(int totalVotes) { this.totalVotes = totalVotes; }
}
