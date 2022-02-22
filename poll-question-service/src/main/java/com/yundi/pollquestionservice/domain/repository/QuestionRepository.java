package com.yundi.pollquestionservice.domain.repository;

import com.yundi.pollquestionservice.domain.model.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends MongoRepository<Question, String> {
}
