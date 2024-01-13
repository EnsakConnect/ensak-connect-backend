package com.ensak.connect.conversation.model;

import com.ensak.connect.profile.model.Profile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Conversation {

    @Id
    @GeneratedValue
    private Integer id;

    private String lastMessage;

    private Date lastMessageDate;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private Profile sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private Profile receiver;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
}
