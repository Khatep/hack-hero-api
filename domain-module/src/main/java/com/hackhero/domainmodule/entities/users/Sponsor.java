package com.hackhero.domainmodule.entities.users;

import com.hackhero.domainmodule.entities.Hackathon;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "sponsors")
public class Sponsor extends AbstractUser {

    @OneToOne
    @JoinColumn(name = "auth_user_id", nullable = false)
    private AuthUser authUser;

    private String companyName;
    private String logoUrl;
    private String website;
    private Double contributionAmount;

    @ManyToMany
    @JoinTable(
            name = "sponsors_hackathons",
            joinColumns = @JoinColumn(name = "sponsor_id"),
            inverseJoinColumns = @JoinColumn(name = "hackathon_id")
    )
    private Set<Hackathon> hackathons = new HashSet<>();
}
