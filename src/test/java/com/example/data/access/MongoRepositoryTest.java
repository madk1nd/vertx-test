package com.example.data.access;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

public class MongoRepositoryTest {

    private MongoClient mongoClient;
    private MongoRepository repository;

    @Before
    public void init() {
        mongoClient = MongoClient.createShared(Vertx.vertx(), new JsonObject().put("db_name", "users"));
        repository = new MongoRepository(mongoClient);
    }

    @Test
    public void test() throws ExecutionException, InterruptedException {
        String username = "vasya1";
        CompletableFuture<List<String>> future = repository.getAllUsernames();
        List<String> result = future.get();

        List<String> expected = new ArrayList<>();
        expected.add(username);
        assertEquals(expected, result);
    }
}
