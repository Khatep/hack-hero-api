package com.hackhero.coremodule.dto.responses;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SponsorResponse.class, name = "SPONSOR"),
        @JsonSubTypes.Type(value = ParticipantResponse.class, name = "PARTICIPANT"),
        @JsonSubTypes.Type(value = JudgeResponse.class, name = "JUDGE"),
        @JsonSubTypes.Type(value = OrganizerResponse.class, name = "ORGANIZER")
})
public abstract class AbstractUserResponse {
}
