package com.api.repository;

import com.api.model.ReportedQuestion;
import com.api.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface reportedRepository extends MongoRepository<ReportedQuestion,String> {
    public List<ReportedQuestion> findByQuestionId(String id);
}
