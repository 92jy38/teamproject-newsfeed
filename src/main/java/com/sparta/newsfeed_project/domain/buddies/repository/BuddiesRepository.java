package com.sparta.newsfeed_project.domain.buddies.repository;

import com.sparta.newsfeed_project.domain.buddies.entity.Buddies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BuddiesRepository extends JpaRepository<Buddies, Long> {

    Buddies findOneByFromUserIdAndToUserId(Long memberId, Long buddyId);

    List<Buddies> findAllByToUserId(Long memberId);

    boolean existsByFromUserIdAndToUserId(Long fromUserId, Long toUserId);

    @Query("select b.toUserId from Buddies b where b.fromUserId = :memberId and b.approved = true ")
    List<Long> findIdListByFromUserId(@Param("memberId") Long memberId);

    Buddies findByFromUserID(Long fromUserId);

}