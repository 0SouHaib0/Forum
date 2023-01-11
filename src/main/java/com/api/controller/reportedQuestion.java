package com.api.controller;

import com.api.model.Answer;
import com.api.model.ReportedAnswer;
import com.api.model.ReportedQuestion;
import com.api.model.User;
import com.api.repository.AnswerRepository;
import com.api.repository.QuestionRepository;
import com.api.repository.ReportedAnswerRepository;
import com.api.repository.reportedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class reportedQuestion {
    @Autowired
    private reportedRepository repo;
    @Autowired
    private QuestionRepository repQ;
    @Autowired
    private AnswerRepository Ar;
    @Autowired
    private ReportedAnswerRepository Ra;

    @PostMapping("/repQuestions/add")
    public ResponseEntity<?> repQuestion(@RequestBody ReportedQuestion rep){
        repo.save(rep);
        Optional<ReportedQuestion> repOptional = repo.findById(rep.getId());
        if (repOptional.isPresent()) {
            return new ResponseEntity<>("ADD-SC", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("ADD-NO", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/repQuestions")
    public ResponseEntity<?>getAllQreported(){
        List<ReportedQuestion> rep = repo.findAll();
        if (rep.size()>0){
            return new ResponseEntity<List<ReportedQuestion>>(rep, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("no repQuestion", HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/repQuestions/{id}")
    public ResponseEntity<?> getReportedQuestion(@PathVariable String id){
        Optional<ReportedQuestion> rQstOptional = repo.findById(id);
        if (rQstOptional.isPresent()){
            return new ResponseEntity<>(rQstOptional, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("no users", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/repQuestions/ignore/{id}")
    public ResponseEntity<?> ingorerQreported(@PathVariable String id) {
        try {
            repo.deleteById(id);
            return new ResponseEntity<>("DEL-SC ", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/repQuestions/delete/{id}")
    public ResponseEntity<?> deleteQreported(@PathVariable String id) {
        try {
            Optional<ReportedQuestion> rQstOptional = repo.findById(id);
            List<Answer> ans = Ar.findByQuestionId(rQstOptional.get().getQuestionId());
            for (Answer el :ans
                 ) {
                List<ReportedAnswer> ansR = Ra.findByAnswerId(el.getId());
                for (ReportedAnswer rl :ansR
                ) {
                    Ra.deleteById(rl.getId());
                }

                Ar.deleteById(el.getId());

            }
            if (rQstOptional.isPresent()){
                ReportedQuestion repp = rQstOptional.get();
                repQ.deleteById(repp.getQuestionId());
                repo.deleteById(id);
            }
            return new ResponseEntity<>("DEL-SC ", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/repQuestions/edit_cause/{id}")
    public ResponseEntity<?> editCause(@PathVariable String id, @RequestBody ReportedQuestion re) {

        Optional<ReportedQuestion> repOptional = repo.findById(id);
        if (repOptional.isPresent()) {
            ReportedQuestion repp = repOptional.get();
            repp.setCause(re.getCause());
            repo.save(repp);
            return new ResponseEntity<>("UP-SC", HttpStatus.OK);
        } else {

            //"Post not found with id = " + id
            return new ResponseEntity<>("UP-NON", HttpStatus.NOT_FOUND);
        }

    }




}
