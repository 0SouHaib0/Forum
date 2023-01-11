package com.api.repository;

import com.api.model.ReportedAnswer;
import com.api.model.ReportedQuestion;
import com.api.model.topic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportedAnswerRepository extends MongoRepository<ReportedAnswer,String> {
    public List<ReportedAnswer> findByAnswerId(String id);


}
