package com.platform.exchange.controller;

import com.platform.exchange.model.User;
import com.platform.exchange.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<User>> getUserById(@PathVariable("userId") String userId) {
        return Mono.fromCallable(() -> userService.getUser(userId))
                .subscribeOn(Schedulers.boundedElastic())
                .map(ResponseEntity::ok);
    }

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<User>>> getAllUsers() {
        return Mono.fromCallable(() -> userService.getAllUsers())
                .subscribeOn(Schedulers.boundedElastic())
                .map(ResponseEntity::ok);
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<User>>> getAllUsersButMe(@RequestParam String id) {
        return Mono.fromCallable(() -> userService.getAllUsersButMe(id))
                .subscribeOn(Schedulers.boundedElastic())
                .map(ResponseEntity::ok);
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<User>> createUser(@RequestBody User user) {
        return Mono.fromCallable(() -> userService.saveUser(user))
                .subscribeOn(Schedulers.boundedElastic())
                .map(ResponseEntity::ok);
    }

    @PutMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<User>> updateUser(@PathVariable("userId") String userId, @RequestBody User user) {
        user.setId(UUID.fromString(userId));
        return Mono.fromCallable(() -> userService.updateUser(user))
                .subscribeOn(Schedulers.boundedElastic())
                .map(ResponseEntity::ok);
    }

    @DeleteMapping(value = "/{userId}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable("userId") String userId) {
        return Mono.fromRunnable(() -> userService.deleteUser(userId))
                .subscribeOn(Schedulers.boundedElastic())
                .thenReturn(ResponseEntity.accepted().build());
    }
}
