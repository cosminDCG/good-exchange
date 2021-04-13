package com.platform.exchange.controller;

import com.platform.exchange.model.Meeting;
import com.platform.exchange.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/meeting")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Meeting>> createMeeting(@RequestBody Meeting meeting) {
        return Mono.fromCallable(() -> meetingService.saveMeeting(meeting))
                .subscribeOn(Schedulers.boundedElastic())
                .map(ResponseEntity::ok);
    }

    @PutMapping(value = "/{meetingId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Meeting>> updateMeeting(@PathVariable("meetingId") String meetingId, @RequestBody Meeting meeting) {
        meeting.setId(UUID.fromString(meetingId));
        return Mono.fromCallable(() -> meetingService.updateMeeting(meeting))
                .subscribeOn(Schedulers.boundedElastic())
                .map(ResponseEntity::ok);
    }

    @PostMapping(value = "/{meetingId}/respond", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Meeting>> approveMeeting(@PathVariable("meetingId") String meetingId, @RequestParam boolean response) {
        return Mono.fromCallable(() -> meetingService.respondToMeeting(meetingId, response))
                .subscribeOn(Schedulers.boundedElastic())
                .map(ResponseEntity::ok);
    }

    @PostMapping(value = "/{meetingId}/failure", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Meeting>> failureMeeting(@PathVariable("meetingId") String meetingId) {
        return Mono.fromRunnable(() -> meetingService.failureMeeting(meetingId))
                .subscribeOn(Schedulers.boundedElastic())
                .thenReturn(ResponseEntity.accepted().build());
    }

    @DeleteMapping(value = "/{meetingId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Void>> deleteMeeting(@PathVariable("meetingId") String meetingId) {
        return Mono.fromRunnable(() -> meetingService.deleteMeeting(meetingId))
                .subscribeOn(Schedulers.boundedElastic())
                .thenReturn(ResponseEntity.accepted().build());
    }

    @GetMapping(path = "/previous/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<Meeting>>> getPreviousMeetings(@PathVariable("userId") String userId) {
        return Mono.fromCallable(() -> meetingService.getPreviousMeetingsByUser(userId))
                .subscribeOn(Schedulers.boundedElastic())
                .map(ResponseEntity::ok);
    }

    @GetMapping(path = "/upcoming/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<Meeting>>> getUpcomingMeetings(@PathVariable("userId") String userId) {
        return Mono.fromCallable(() -> meetingService.getUpcomingMeetingsByUser(userId))
                .subscribeOn(Schedulers.boundedElastic())
                .map(ResponseEntity::ok);
    }

    @GetMapping(path = "/user/{userId}/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<Meeting>>> allMeetings(@PathVariable("userId") String userId) {
        return Mono.fromCallable(() -> meetingService.getAllMeetingsByUser(userId))
                .subscribeOn(Schedulers.boundedElastic())
                .map(ResponseEntity::ok);
    }
}
