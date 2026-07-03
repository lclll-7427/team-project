package com.cvs.repository;

import com.cvs.model.User;
import com.cvs.model.VoteRecord;
import com.cvs.model.VoteSession;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VoteRecordRepository extends JpaRepository<VoteRecord, Long> {
    List<VoteRecord> findByVoteSession(VoteSession voteSession);
    List<VoteRecord> findByStudent(User student);
    List<VoteRecord> findByVoteSessionAndStudent(VoteSession voteSession, User student);
    boolean existsByVoteSessionAndStudent(VoteSession voteSession, User student);
}
