package com.api.repository;

import com.api.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,String> {

public Optional<User> findByEmailAndPassword(String email,String password);
    public Optional<User> findByEmail(String email);
    public Optional<User> findByEmailAndIdIsNotIn(String email,String id );
    public List<User> findByIsblockedFalse();
    public List<User> findByIsblockedTrue();
    public Optional<User> findByEmailAndPasswordAndIsblockedFalse(String email,String password);

}
