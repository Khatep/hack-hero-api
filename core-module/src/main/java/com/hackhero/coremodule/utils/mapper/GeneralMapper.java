package com.hackhero.coremodule.utils.mapper;

import com.hackhero.coremodule.dto.responses.AbstractUserResponse;
import com.hackhero.domainmodule.entities.AbstractEntity;
import com.hackhero.domainmodule.entities.users.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
public class GeneralMapper {

    private final ParticipantMapper participantMapper;
    private final OrganizerMapper organizerMapper;
    private final JudgeMapper  judgeMapper;
    private final SponsorMapper sponsorMapper;

    //TODO:: Хардкод, риск что будет вылетать много IllegalArgumentException
    public AbstractUserResponse toResponse(AbstractEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity is null");
        }

        if (entity instanceof Participant) {
            return participantMapper.toResponse((Participant) entity);
        }

        if (entity instanceof Organizer) {
            return organizerMapper.toResponse((Organizer) entity);
        }

        if (entity instanceof Judge) {
            return judgeMapper.toResponse((Judge) entity);
        }

        if (entity instanceof Sponsor) {
            return sponsorMapper.toResponse((Sponsor) entity);
        }

        throw new IllegalArgumentException("Entity is not a valid for response");
    }
}
