package com.java.akdev.reviewservice.repository;

import com.java.akdev.reviewservice.entity.Review;
import com.java.akdev.commonmodels.enumeration.Receiver;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT rev.review FROM Review rev " +
           "WHERE rev.receiverId = :userId AND " +
           "rev.receiver = :receiver " +
           "ORDER BY rev.id DESC")
    List<Double> findAllByUser(UUID userId, Receiver receiver, Pageable pageable);

    List<Review> findAllByReceiver(Receiver receiver);

    List<Review> findAllByReceiverId(UUID receiverId);
}
