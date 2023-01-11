package com.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String nom;
    private String prenom;
    private String password;
    private boolean isAdmin;
    private List<Question> questions;
    private List<Answer> answers;
    private List<ReportedQuestion> reported_questions;
    private List<ReportedAnswer> reported_answers;
    private String email;
    private boolean isblocked;
    private Date createdAt;
    private Date updatedAt;
}
