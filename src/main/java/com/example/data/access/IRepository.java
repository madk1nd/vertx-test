package com.example.data.access;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IRepository {
    CompletableFuture<List<String>> getAllUsernames();
    CompletableFuture<String> addUser(String username, String password, String email);
    CompletableFuture<String> deleteUser(String username);
}
