package com.example.data.access;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class MongoRepository implements IRepository {

    private MongoClient mongoClient;

    public MongoRepository(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    public CompletableFuture<List<String>> getAllUsernames() {
        CompletableFuture<List<String>> future = new CompletableFuture<>();
        mongoClient.find(
                "root",
                new JsonObject(),
                ar -> {
                    if (ar.succeeded()) {
                        future.complete(ar.result()
                                .stream()
                                .map(json -> json.getString("username"))
                                .collect(Collectors.toList()));
                    } else {
                        future.completeExceptionally(new RuntimeException());
                    }
                });
        return future;
    }

    @Override
    public CompletableFuture<String> addUser(String username, String password, String email) {
        CompletableFuture<String> future = new CompletableFuture<>();

        mongoClient.insert(
                "root",
                new JsonObject()
                        .put("username", username)
                        .put("password", password)
                        .put("email", email),
                ar -> {
                    if (ar.succeeded()) {
                        future.complete("user inserted");
                    } else {
                        future.completeExceptionally(new RuntimeException());
                    }
                });
        return future;
    }

    @Override
    public CompletableFuture<String> deleteUser(String username) {
        CompletableFuture<String> future = new CompletableFuture<>();
        mongoClient.remove(
                "root",
                new JsonObject().put("username", username),
                ar -> {
                    if (ar.succeeded()) {
                        future.complete("user deleted");
                    } else {
                        future.completeExceptionally(new RuntimeException());
                    }
                });
        return future;
    }
}
