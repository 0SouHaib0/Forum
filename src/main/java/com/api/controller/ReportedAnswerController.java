package com.api.controller;

import com.api.model.Answer;
import com.api.model.Question;
import com.api.model.ReportedAnswer;
import com.api.model.ReportedQuestion;
import com.api.repository.AnswerRepository;
import com.api.repository.QuestionRepository;
import com.api.repository.ReportedAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
public class ReportedAnswerController {
    @Autowired
    private ReportedAnswerRepository rAns;
    @Autowired
    private AnswerController Ans;
    @Autowired
    private AnswerRepository AnsR;

    @GetMapping("/reported-answers")
    public ResponseEntity<?>getReportedAnswers(){
        List<ReportedAnswer> rAnsrs = rAns.findAll();
        if (rAnsrs.size()>0){
            return new ResponseEntity<List<ReportedAnswer>>(rAnsrs, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("no users", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/reported-answers/{id}")
    public ResponseEntity<?> getReportedAnswer(@PathVariable String id){
        Optional<ReportedAnswer> rAnsOptional = rAns.findById(id);
        if (rAnsOptional.isPresent()){
            return new ResponseEntity<>(rAnsOptional, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("no users", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/reported-answers/add")
    public ResponseEntity<?> addReportedAns(@RequestBody ReportedAnswer rans){
        rAns.insert(rans);
        Optional<ReportedAnswer> rAnsOptional = rAns.findById(rans.getId());
        if (rAnsOptional.isPresent()) {
            return new ResponseEntity<>(rAnsOptional, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("ADD-NO", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/reported-answers/ignore/{id}")
    public ResponseEntity<?> ignoreReportedAnswer(@PathVariable String id) {
        try {
            rAns.deleteById(id);
            return new ResponseEntity<>("DEL-SC ", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/reported-answers/delete/{id}")
    public ResponseEntity<?> deleteReportedAnswer(@PathVariable String id) {
        try {
            Optional<ReportedAnswer> rAnsOptional = rAns.findById(id);
            if (rAnsOptional.isPresent()){
                ReportedAnswer repp = rAnsOptional.get();
                AnsR.deleteById(repp.getAnswerId());
                rAns.deleteById(id);
            }
            return new ResponseEntity<>("DEL-SC ", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

//    @PutMapping("/reported-answers/delete/{id}")
//    public void deleteReportedAnswer(@PathVariable String id) {
//        Optional<ReportedAnswer> reportedAnsOptional = rAns.findById(id);
//        if (reportedAnsOptional.isPresent()) {
//            ReportedAnswer repQst = reportedAnsOptional.get();
//            Answer answer = new Answer();
//            answer.setArchived(true);
//            Ans.updateAnswer(answer, repQst.getAnswerId());
//            ignoreReportedAnswer(id);
//        }
//    }

}
