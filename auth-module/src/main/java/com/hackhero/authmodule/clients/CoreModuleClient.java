package com.hackhero.authmodule.clients;

import com.hackhero.coremodule.dto.responses.AbstractUserResponse;
import com.hackhero.domainmodule.entities.AbstractEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange(url = "/core-module/utils")
public interface CoreModuleClient {

    @PostExchange("/map-to-response")
    AbstractUserResponse mapToResponse(@RequestBody AbstractEntity entity);
}