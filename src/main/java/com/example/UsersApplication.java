package com.example;

import com.example.data.access.IRepository;
import com.example.data.access.MongoRepository;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

import java.util.concurrent.CompletableFuture;

public class UsersApplication extends AbstractVerticle {

    private MongoClient mongoClient;

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        System.out.println("privet");

        mongoClient = MongoClient.createShared(vertx,
                new JsonObject().put("db_name", "users"));

        IRepository repository = new MongoRepository(mongoClient);

        repository.getAllUsernames()
                .thenCompose(strings -> {
                    System.out.println("userList: " + strings);
                    return repository.addUser("petya", "hard_pasword", "test@test.ru");
                })
                .thenCompose(user -> {
                    System.out.println(user);
                    return repository.getAllUsernames();
                })
                .thenCompose(strings -> {
                    System.out.println("userList: " + strings);
                    return repository.deleteUser("petya");
                })
                .thenCompose(user -> {
                    System.out.println(user);
                    return repository.getAllUsernames();
                });
    }
}
