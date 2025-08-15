package com.hackhero.domainmodule.entities.users;


import com.hackhero.domainmodule.entities.AbstractEntity;
import com.hackhero.domainmodule.entities.Hackathon;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "judges")
public class Judge extends AbstractUser {

    @OneToOne
    @JoinColumn(name = "auth_user_id", nullable = false)
    private AuthUser authUser;

    private String expertise;
    private String bio;
    private String linkedinUrl;

    @ManyToMany
    @JoinTable(
            name = "judges_hackathons",
            joinColumns = @JoinColumn(name = "judge_id"),
            inverseJoinColumns = @JoinColumn(name = "hackathon_id")
    )
    private Set<Hackathon> hackathons = new HashSet<>();
}