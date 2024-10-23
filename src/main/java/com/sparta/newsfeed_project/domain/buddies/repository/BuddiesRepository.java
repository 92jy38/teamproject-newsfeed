package com.sparta.newsfeed_project.domain.buddies.repository;

import com.sparta.newsfeed_project.domain.buddies.entity.Buddies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BuddiesRepository extends JpaRepository<Buddies, Long> {

    Buddies findOneByFromUserIdAndToUserId(Long memberId, Long buddyId);

    List<Buddies> findAllByToUserId(Long memberId);

    boolean existsByFromUserIdAndToUserId(Long fromUserId, Long toUserId);

    Buddies findByFromUserId(Long fromUserId);

}