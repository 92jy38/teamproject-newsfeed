package com.sparta.newsfeed_project.domain.buddies.repository;

import com.sparta.newsfeed_project.domain.buddies.entity.Buddies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BuddiesRepository extends JpaRepository<Buddies, Long> {

    List<Buddies> findAllByFromUserIdOrToUserId(Long memberId, Long buddyId);

    Buddies findOneByFromUserIdAndToUserId(Long memberId, Long buddyId);

    List<Buddies> findAllByToUserId(Long memberId);

    boolean existsByFromUserIdAndToUserId(Long fromUserId, Long toUserId);

//    @Query("select b.fromUserId from Buddies b where b.toUserId = :memberId and b.approved = true ")
//    List<Long> findAllIdByFromUserId(@Param("memberId") Long memberId);

    @Query("SELECT DISTINCT b1.toUserId\n" +
            "FROM Buddies b1\n" +
            "JOIN Buddies b2 ON b1.toUserId = b2.fromUserId\n" +
            "WHERE b1.fromUserId = :memberId AND b1.approved = TRUE AND b2.approved = TRUE")
    List<Long> findAllIdByFromUserId(@Param("memberId") Long memberId);


}
