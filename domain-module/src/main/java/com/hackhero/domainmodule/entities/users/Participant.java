package com.hackhero.domainmodule.entities.users;

import com.hackhero.domainmodule.enums.GradeStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "participants")
public class Participant extends AbstractUser {

    @OneToOne
    @JoinColumn(name = "auth_user_id", nullable = false)
    private AuthUser authUser;

    private String nickname;
    private String bio;
    private String githubUsername;
    private Integer yearsExperience;
    private String email;
    private GradeStatus gradeStatus;

//    @Column(columnDefinition = "TEXT")
//    private String skills;
//    private String resumeUrl;
}