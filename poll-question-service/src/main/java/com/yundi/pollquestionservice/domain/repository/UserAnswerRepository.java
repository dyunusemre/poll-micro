package com.yundi.pollquestionservice.domain.repository;

import com.yundi.pollquestionservice.domain.model.UserAnswer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAnswerRepository extends MongoRepository<UserAnswer, String> {
}
