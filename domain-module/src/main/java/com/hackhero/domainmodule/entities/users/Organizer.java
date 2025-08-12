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
@Table(name = "organizers")
public class Organizer extends AbstractEntity {

    @OneToOne
    @JoinColumn(name = "auth_user_id", nullable = false)
    private AuthUser authUser;

    private String organizationName;
    private String positionTitle;
    private String website;
    private String description;

    @ManyToMany
    @JoinTable(
            name = "organizers_hackathons",
            joinColumns = @JoinColumn(name = "organizer_id"),
            inverseJoinColumns = @JoinColumn(name = "hackathon_id")
    )
    private Set<Hackathon> hackathons = new HashSet<>();
}
