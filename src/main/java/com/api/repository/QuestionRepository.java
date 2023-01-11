package com.api.repository;

import com.api.model.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuestionRepository extends MongoRepository<Question,String> {
    List <Question> findByTopicId(String id);
    //List <Question> findByArchived(boolean archived);
    List <Question> findByIsarchivedIsNot(boolean archived);
    public List <Question> findByUserId(String userId);
    List <Question> findByTitle(String title);
    List <Question> findByIsarchived(boolean archived);

}