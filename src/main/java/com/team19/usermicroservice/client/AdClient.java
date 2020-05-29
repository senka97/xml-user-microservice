package com.team19.usermicroservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "ad-service")
public interface AdClient {

    @PutMapping(value = "/api/ad/block/client/{id}")
    ResponseEntity hideAdsForBlockedClient(@PathVariable("id") Long id, @RequestHeader("permissions") String permissions,
                                           @RequestHeader("userID") String userId, @RequestHeader("Authorization") String token);

    @PutMapping(value = "/api/ad/activate/client/{id}")
    ResponseEntity showAdsForActiveClient(@PathVariable("id") Long id, @RequestHeader("permissions") String permissions,
                                           @RequestHeader("userID") String userId, @RequestHeader("Authorization") String token);

}
