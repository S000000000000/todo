package com.vitdevelop.todo_app.web.controller;

import com.vitdevelop.todo_app.core.domain.Todo;
import com.vitdevelop.todo_app.core.domain.User;
import com.vitdevelop.todo_app.core.service.TodoService;
import com.vitdevelop.todo_app.core.service.UserService;
import com.vitdevelop.todo_app.web.data.TodoData;
import com.vitdevelop.todo_app.web.data.UserData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")


public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/info")
    public ResponseEntity<User> getUser(@RequestParam String username){
        return ResponseEntity.ok(userService.findUserByUserName(username));
    }



    @GetMapping
    public ResponseEntity<User> getAllUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.findUserById(userId));
    }
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok((User) userService.findUserById(userId));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserData userData) {
        return ResponseEntity.ok(userService.createUser(userData.getUser()));
    }

    @PostMapping("/{userId}/todo")
    public ResponseEntity<Todo> createUserTodo(@PathVariable Long userId,
                                               @RequestBody TodoData todoData) {
        return ResponseEntity.ok(userService.createUserTodo(userId,todoData.getTodo()));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId,
                                           @RequestBody UserData userData) {
        return ResponseEntity.ok(userService.updateUser(userId, userData.getUser()));
    }
    @PutMapping("{userId}/todo/{todoId}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long userId,
                                           @PathVariable Long todoId,
                                           @RequestBody TodoData todoData) {
        return ResponseEntity.ok(userService.updateUserTodo(userId,todoId, todoData.getTodo()));
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{userId}/todo/{todoId}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long userId,
                                           @PathVariable Long todoId
                                          ) {
        userService.deleteUserTodoById(userId,todoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/todo")
    public ResponseEntity<List<Todo>> getAllUserTodo(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.findUserTodo(userId));
    }

  @GetMapping("/{userId}/todo/{todoId}")
   public ResponseEntity<Todo> getTodoById(@PathVariable Long userId,@PathVariable Long todoId) {
        return ResponseEntity.ok(userService.findUserTodoById(userId,todoId));
   }
//
//    @PostMapping
//    public ResponseEntity<Todo> createTodo(@RequestBody TodoData todoData) {
//        return ResponseEntity.ok(todoService.createTodo(todoData.getTodo()));
//    }
//
//    @PutMapping("/{todoId}")
//    public ResponseEntity<Todo> updateTodo(@PathVariable Long todoId,
//                                           @RequestBody TodoData todoData) {
//        return ResponseEntity.ok(todoService.updateTodo(todoId, todoData.getTodo()));
//    }
//
//    @DeleteMapping("/{todoId}")
//    public ResponseEntity<Void> deleteTodo(@PathVariable Long todoId) {
//        todoService.deleteTodoById(todoId);
//        return ResponseEntity.noContent().build();
//    }
}
