package com.sparta.newsfeed_project.domain.buddy.repository;

import com.sparta.newsfeed_project.domain.buddy.entity.Buddy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuddyRepository extends JpaRepository <Buddy,Long> {

}
