package com.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Questions")
public class Question {
    @Id
    private String id;
    private Date date;
    private String question;
    private String title;
    private String userId;
    private String topicId;
    private boolean isresolved;
    private boolean isarchived;
}
