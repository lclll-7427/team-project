package com.cvs.repository;

import com.cvs.model.VoteOption;
import com.cvs.model.VoteSession;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VoteOptionRepository extends JpaRepository<VoteOption, Long> {
    List<VoteOption> findByVoteSession(VoteSession voteSession);
}
