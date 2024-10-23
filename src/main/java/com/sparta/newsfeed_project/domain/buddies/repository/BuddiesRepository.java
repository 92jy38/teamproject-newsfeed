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

    @Query("SELECT b1.toUserId\n" +
            "FROM Buddies b1\n" +
            "         JOIN Buddies b2 ON b1.fromUserId = b2.toUserId AND b1.toUserId = b2.fromUserId\n" +
            "WHERE b1.fromUserId = :memberId AND b1.approved = TRUE AND b2.approved = TRUE")
    List<Long> findIdListByFromUserId(@Param("memberId") Long memberId);

    Buddies findByFromUserId(Long fromUserId);

    List<Buddies> findByFromUserIdOrToUserId(Long fromUserId, Long toUserId);

    Long countByFromUserId(Long fromUserId);

    Long countByToUserId(Long fromUserId);
}
