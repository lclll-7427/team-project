package com.cvs.model;

import jakarta.persistence.*;

@Entity
@Table(name = "vote_options")
public class VoteOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String text;

    @Column(name = "is_correct", nullable = false)
    private Boolean isCorrect;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_session_id", nullable = false)
    private VoteSession voteSession;

    public VoteOption() {}

    public VoteOption(String text, Boolean isCorrect, VoteSession voteSession) {
        this.text = text;
        this.isCorrect = isCorrect;
        this.voteSession = voteSession;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public Boolean getIsCorrect() { return isCorrect; }
    public void setIsCorrect(Boolean isCorrect) { this.isCorrect = isCorrect; }
    public VoteSession getVoteSession() { return voteSession; }
    public void setVoteSession(VoteSession voteSession) { this.voteSession = voteSession; }
}
