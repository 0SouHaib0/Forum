package com.api.controller;

import com.api.model.*;
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
public class QuestionController {
    @Autowired
    private QuestionRepository Qr;
    @Autowired
    private AnswerRepository Ar;
    @Autowired
    private reportedRepository repo;
    @Autowired
    private ReportedAnswerRepository rAns;

    // recupere tous les questions
//    @GetMapping("/questions")
//    public ResponseEntity<?> getQuestions(){
//
//        List<Question> qst = Qr.findAll();
//        if (qst.size()>0){
//            return new ResponseEntity<>(qst, HttpStatus.OK);
//        }else{
//            return new ResponseEntity<>("no Questions existing", HttpStatus.NOT_FOUND);
//        }
//
//    }

    // recuperer les questions archivees
    @GetMapping("/questionsArchived")
    public ResponseEntity<?> getArchivedQuestions(){

        List<Question> qst = Qr.findByIsarchived(true);
        if (qst.size()>0){
            return new ResponseEntity<>(qst, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("no Questions existing", HttpStatus.NOT_FOUND);
        }

    }

    //recuperer les questions non archivee
    @GetMapping("/questions")
    public ResponseEntity<?> getNonArchivedQuestions(){

        List<Question> qst = Qr.findByIsarchivedIsNot(true);
        if (qst.size()>0){
            return new ResponseEntity<>(qst, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("no Questions existing", HttpStatus.NOT_FOUND);
        }

    }
    
    @GetMapping("/questions/count")
    public int getNumberQuestions(){
        int n= (int) Qr.count();
return n;
    }

    //Get the question by its ID
    @GetMapping("/questions/question/{id}")
    public ResponseEntity<?> getQuestionById(@PathVariable String id){
        Optional<Question> question = Qr.findById(id);
        if (question.isPresent()){
            return new ResponseEntity<>(question, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("no questions", HttpStatus.NOT_FOUND);
        }
    }
    // recupere les questions d'un user by its ID
    @GetMapping("/questions/{idUser}")
    public ResponseEntity<?> getQuestionByUserId(@PathVariable String idUser){
        List <Question> question = Qr.findByUserId(idUser);
        if (question.size()>0){
            return new ResponseEntity<>(question, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("no Questions yet", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/questions/chercher")
    public ResponseEntity<?> chercher(@RequestBody Question Quest){
        List <Question> question = Qr.findByTitle(Quest.getTitle());
        //Optional<Question> question = Qr.findByTitle(title);
        //System.out.println(title);
        if (question.size()>0){
            return new ResponseEntity<>(question.get(0), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("no Questions yet", HttpStatus.NOT_FOUND);
        }
    }


    // add a new question
    @PostMapping("/questions/add")
    public String addQuestion(@RequestBody Question qst ){
        qst.setDate(new Date(System.currentTimeMillis()));
        qst.setIsresolved(false);
        Qr.insert(qst);
        Optional<Question> qstt = Qr.findById(qst.getId());
        if (qstt.isPresent()) {
            return "Question " +qst.getTitle() +"cree" ;
        } else {
            return "Question non cree";
        }
    }

    //ajouter une question by user
    @PostMapping("/questions/add/{idUser}")
    public String addQuestionByUser(@RequestBody Question qst ,@PathVariable String idUser){
        qst.setDate(new Date(System.currentTimeMillis()));
        qst.setIsresolved(false);
        qst.setUserId(idUser);
        Qr.insert(qst);
        Optional<Question> qstt = Qr.findById(qst.getId());
        if (qstt.isPresent()) {
            return "Question " +qst.getTitle() +"cree" ;
        } else {
            return "Question non cree";
        }
    }

    //Archive question by its id
    @PutMapping("/questions/{id}/archive")
    public ResponseEntity<?> archiveQuestion(@PathVariable String id) {
        Optional<Question> qst = Qr.findById(id);
        if (qst.isPresent()) {
            Question qstt = qst.get();
            qstt.setIsarchived(true);
            Qr.save(qstt);
            return new ResponseEntity<>("Archived !!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("NON Archived !!", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/questions/{id}/setAsResolved")
    public ResponseEntity<?> SetAsResolved(@PathVariable String id) {
        Optional<Question> qst = Qr.findById(id);
        if (qst.isPresent()) {
            Question qstt = qst.get();
            qstt.setIsresolved(true);
            Qr.save(qstt);
            return new ResponseEntity<>("Question resolu !!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("no question detected   !!", HttpStatus.NOT_FOUND);
        }
    }



    @PostMapping("/questions/add/{userId}/{topicId}")
    public ResponseEntity<?> addQuestion(@RequestBody Question qst, @PathVariable String userId, @PathVariable String topicId){
        qst.setDate(new Date(System.currentTimeMillis()));
        qst.setIsresolved(false);
        qst.setIsarchived(false);
        qst.setUserId(userId);
        qst.setTopicId(topicId);
        Qr.save(qst);
        Optional<Question> questionOptional = Qr.findById(qst.getId());
        if (questionOptional.isPresent()) return new ResponseEntity<>("ADD-SC", HttpStatus.OK);
        else {
            return new ResponseEntity<>("ADD-NO", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/questions/update/{id}")
    public ResponseEntity<?> updateQuestion(@RequestBody Question question, @PathVariable String id) {
        Optional<Question> questionOptional = Qr.findById(id);
        if (questionOptional.isPresent()) {
            Question userup = questionOptional.get();
            userup.setQuestion(question.getQuestion() !=null ? question.getQuestion() : userup.getQuestion());
            userup.setDate(question.getDate() !=null ? question.getDate() : userup.getDate());
            userup.setIsarchived(question.isIsarchived());
            userup.setIsresolved(question.isIsresolved());
            Qr.save(userup);
            return new ResponseEntity<>(new Question(), HttpStatus.OK);
        } else {

            //"Post not found with id = " + id
            return new ResponseEntity<>("UP-NON", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/questions/delete/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable String id) {
        try {
            Optional<Question>  questions = Qr.findById(id);
            List<Answer> ans =Ar.findByQuestionId(id);
            List<ReportedQuestion> rqs = repo.findByQuestionId(id);
            for (ReportedQuestion s : rqs) {
                repo.deleteById(s.getId());
            }
            for (Answer ss : ans) {
                List<ReportedAnswer> rass = rAns.findByAnswerId(ss.getId());
                for (ReportedAnswer sss : rass) {
                    rAns.deleteById(sss.getId());
                }

                Ar.deleteById(ss.getId());
            }
            Qr.deleteById(id);
            return new ResponseEntity<>("DEL-SC ", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("/topics/question/{id}")
    public ResponseEntity<?> getquestion(@PathVariable String id){
        List <Question> an = Qr.findByTopicId(id);
        if (an.size()>0){
            return new ResponseEntity<>(an, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("none", HttpStatus.NOT_FOUND);
        }
    }
}