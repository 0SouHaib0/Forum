package com.api.repository;

import com.api.model.User;
import com.api.model.topic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicRepository extends MongoRepository<topic,String> {

}
