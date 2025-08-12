package com.hackhero.domainmodule.entities.users;

import com.hackhero.domainmodule.entities.AbstractEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "participants")
public class Participant extends AbstractEntity {

    @OneToOne
    @JoinColumn(name = "auth_user_id", nullable = false)
    private AuthUser authUser;

    private String nickname;
    private String bio;
    private String githubUsername;
    private Integer yearsExperience;

    @Column(columnDefinition = "TEXT")
    private String skills;

    private String resumeUrl;
}