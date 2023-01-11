package com.api.repository;

import com.api.model.Answer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface AnswerRepository extends MongoRepository<Answer,String> {
public List<Answer> findByQuestionIdAndArchived(String id,boolean archived);
    List <Answer> findByArchivedIsNot(boolean archived);
    List <Answer> findByQuestionId(String questionId);
    List <Answer> findByArchived(boolean bl);

}