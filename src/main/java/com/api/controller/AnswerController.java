package com.api.controller;

import com.api.model.Answer;
import com.api.model.Question;
import com.api.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class AnswerController {
    @Autowired
    private AnswerRepository Ar;

   //recuperer tous les reponses
    @GetMapping("/answers")
    public ResponseEntity<?> getAnswers(){

        List<Answer> qst = Ar.findAll();
        if (qst.size()>0){
            return new ResponseEntity<>(qst, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("no Answers existing", HttpStatus.NOT_FOUND);
        }
    }

    //recuperer une reponse by its id
    @GetMapping("/answers/get/{id}")
    public ResponseEntity<?> getAnswerById(@PathVariable String id){
        Optional<Answer> answer = Ar.findById(id);
        if (answer.isPresent()){
            return new ResponseEntity<>(answer, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("no response", HttpStatus.NOT_FOUND);
        }
    }

    // recuperer les reponses archivees
    @GetMapping("/answersArchived")
    public ResponseEntity<?> getArAnswers(){

        List<Answer> ans = Ar.findByArchived(true);
        if (ans.size()>0){
            return new ResponseEntity<>(ans, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("no Questions existing", HttpStatus.NOT_FOUND);
        }

    }

    //recuperer les questions non archivee
    @GetMapping("/answersNonArchived")
    public ResponseEntity<?> getNonArAnswers(){

        List<Answer> ans = Ar.findByArchivedIsNot(true);
        if (ans.size()>0){
            return new ResponseEntity<>(ans, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("no answers existing", HttpStatus.NOT_FOUND);
        }

    }
    // recuperer les reponses d'une question by its ID
    @GetMapping("/answers/{idq}")
    public ResponseEntity<?> getAnswersQ(@PathVariable String idq){
      List<Answer> ans = Ar.findByQuestionIdAndArchived(idq,false);
        if (ans.size()>0){
            return new ResponseEntity<>(ans, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("no answers existing", HttpStatus.NOT_FOUND);
        }
    }

    //Ajouter une reponse a une question by question ID
    @PostMapping("/answers/addAnswer")
    public void addAnswer(@RequestBody Answer ans){
        ans.setDate(new Date(System.currentTimeMillis()));
//        ans.setQuestionId(id);
//        ans.setUserId(idUser);
        Ar.insert(ans);

    }

    @PutMapping("/answers/{id}/archive")
    public ResponseEntity<?> archiveAnswer(@PathVariable String id) {
        Optional<Answer> ans = Ar.findById(id);
        if (ans.isPresent()) {
            Answer qstt = ans.get();
            qstt.setArchived(true);
            Ar.save(qstt);
            return new ResponseEntity<>("Archived !!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("NON Archived !!", HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/answers/update/{id}")
    public ResponseEntity<?> updateAnswer(@RequestBody Answer answer, @PathVariable String id) {
        Optional<Answer> answerOptional = Ar.findById(id);
        if (answerOptional.isPresent()) {
            Answer answerUp = answerOptional.get();
            answerUp.setAnswer(answer.getAnswer() !=null ? answer.getAnswer() : answerUp.getAnswer());
            answerUp.setDate(answer.getDate() !=null ? answer.getDate() : answerUp.getDate());
            answerUp.setArchived(answer.isArchived());
            Ar.save(answerUp);
            return new ResponseEntity<>(new Answer(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("UP-NON", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/answer/delete/{id}")
    public ResponseEntity<?> deleteAnswer(@PathVariable String id){
        try {
            Ar.deleteById(id);
            return new ResponseEntity<>("DEL-SC ", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/question/reponse/{id}")
    public ResponseEntity<?> getreponce(@PathVariable String id){
        List <Answer> an = Ar.findByQuestionId(id);
        if (an.size()>0){
            return new ResponseEntity<>(an, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("none", HttpStatus.NOT_FOUND);
        }
    }

}