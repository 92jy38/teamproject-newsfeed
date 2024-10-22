package com.sparta.newsfeed_project.domain.buddies.repository;

import com.sparta.newsfeed_project.domain.buddies.entity.Buddies;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BuddiesRepository extends JpaRepository <Buddies,Long> {

    List<Buddies> findAllByFromUesrId(Long memberId);

    Buddies findOneByFromUesrIdAndToUserId(Long memberId, Long buddyId);
}