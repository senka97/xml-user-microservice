package com.team19.usermicroservice.client;

import com.team19.usermicroservice.dto.CommentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "car-service")
public interface CarClient {

    @PutMapping(value = "/api/comments/client/{id}")
    ResponseEntity hideCommentRequestsForBlockedAndRemovedClient(@PathVariable("id") Long id,
                                                                 @RequestHeader("permissions") String permissions,
                                                                 @RequestHeader("userID") String userId,
                                                                 @RequestHeader("Authorization") String token);

    @GetMapping(value = "api/comments/client/{id}")
    List<CommentDTO> findAllRejectedComments(@PathVariable("id") Long id,
                                             @RequestHeader("permissions") String permissions,
                                             @RequestHeader("userID") String userId,
                                             @RequestHeader("Authorization") String token);
}
