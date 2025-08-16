package com.hackhero.coremodule.controllers;

import com.hackhero.coremodule.utils.mapper.GeneralMapper;
import com.hackhero.coremodule.dto.responses.AbstractUserResponse;
import com.hackhero.domainmodule.entities.AbstractEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/core-module/utils")
@RequiredArgsConstructor
public class CoreUtilsController {

    private final GeneralMapper generalMapper;

    @PostMapping("/map-to-response")
    public AbstractUserResponse mapToResponse(@RequestBody AbstractEntity entity) {
        return generalMapper.toResponse(entity);
    }
}

