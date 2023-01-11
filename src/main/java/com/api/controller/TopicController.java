package com.api.controller;
import com.api.model.Answer;
import com.api.model.User;
import com.api.model.topic;
import com.api.repository.TopicRepository;
import com.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@RestController
public class TopicController {
    @Autowired
    private TopicRepository ur;
    @GetMapping("/topics")
    public ResponseEntity<?>getAllTopics(){
        List<topic> topics = ur.findAll();
        if (topics.size()>0){
            return new ResponseEntity<List<topic>>(topics, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("no topics", HttpStatus.NOT_FOUND);
        }

    }
    @PostMapping("/topics/add/{idtp}")
    public ResponseEntity<?>addTopic(@PathVariable String idtp,@RequestBody topic topic){
        topic.setSub_topic_id(new ArrayList<String>());
        ur.save(topic);
        topic tp= ur.findById(idtp).get();
        ArrayList<String> ar= tp.getSub_topic_id();
        ar.add(topic.getId());
        tp.setSub_topic_id(ar);
        ur.save(tp);
        Optional<topic> topicOptional = ur.findById(topic.getId());
        if (topicOptional.isPresent()) {
            return new ResponseEntity<>("ADD-SC", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("ADD-NO", HttpStatus.NOT_FOUND);
        }
    }



    @PostMapping("/topics/add")
    public ResponseEntity<?>addTopics(@RequestBody topic topic){
        topic.setSub_topic_id(new ArrayList<String>());
        ur.save(topic);
        Optional<topic> topicOptional = ur.findById(topic.getId());
        if (topicOptional.isPresent()) {
            return new ResponseEntity<>("ADD-SC", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("ADD-NO", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/topics/{id}")
    public ResponseEntity<?>getTopicPerId(@PathVariable String id){
        Optional<topic> topicOptional = ur.findById(id);
        if (topicOptional.isPresent()){
            return new ResponseEntity<>(topicOptional, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("no topics", HttpStatus.NOT_FOUND);
        }
    }

     @GetMapping("/topics/count")

    public int getNumberTopics(){
        int n= (int) ur.count();
        return n;
    }
    @DeleteMapping("/topics/delete/{id}")
    public ResponseEntity<?> deleteTopic(@PathVariable String id) {
        try {
            List<topic> topicOptional = ur.findAll();
            for (int i=0;i<topicOptional.size();i++){
                if (topicOptional.get(i).getSub_topic_id().contains(id)){
                    topic tpsu = topicOptional.get(i);
                    ArrayList<String> su = tpsu.getSub_topic_id();
                    su.remove(id);
                    ur.save(tpsu);
                }
            }


            ur.deleteById(id);
            return new ResponseEntity<>("DEL-SC ", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/topics/{idTps}/sous-topic/{idTpk}")
    public ResponseEntity<?>addSubTopic(@PathVariable String idTps,@PathVariable String idTpk){
        Optional<topic> topicOptional = ur.findById(idTpk);
        topic topic=topicOptional.get();
        ArrayList<String>ss=topic.getSub_topic_id();
        ss.add(idTps);
        topic.setSub_topic_id(ss);
        ur.save(topic);
        topicOptional = ur.findById(topic.getId());
        if (topicOptional.isPresent()) {
            return new ResponseEntity<>("ADD-SC", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("ADD-NO", HttpStatus.NOT_FOUND);
        }
    }



}
