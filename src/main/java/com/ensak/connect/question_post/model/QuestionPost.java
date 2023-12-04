package com.ensak.connect.question_post.model;

import com.ensak.connect.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "question_posts")
public class QuestionPost {
    @Id
    @GeneratedValue
    private Integer id;

    private String question;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

}
