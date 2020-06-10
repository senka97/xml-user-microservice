package com.team19.usermicroservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "rent-service")
public interface RentClient {

    @PutMapping(value = "/api/request/client/{id}")
    ResponseEntity<?> rejectAllPendingRequestForBlockedOrRemovedClient(@PathVariable("id") Long id,
                                                                       @RequestHeader("permissions") String permissions,
                                                                       @RequestHeader("userID") String userId,
                                                                       @RequestHeader("Authorization") String token);
}
