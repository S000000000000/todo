package com.vitdevelop.todo_app.core.service;

import com.vitdevelop.todo_app.core.domain.Todo;
import com.vitdevelop.todo_app.core.domain.User;
import com.vitdevelop.todo_app.core.domain.enums.ServiceErrorCode;
import com.vitdevelop.todo_app.core.exception.ServiceException;
import com.vitdevelop.todo_app.core.repository.TodoRepository;
import com.vitdevelop.todo_app.core.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Service
@Transactional
@Validated
public class UserService {

    private final UserRepository userRepository;
    private final TodoService todoService;
    private final TodoRepository todoRepository;

    public UserService(UserRepository userRepository, TodoService todoService,
                       TodoRepository todoRepository) {
        this.userRepository = userRepository;
        this.todoService = todoService;
        this.todoRepository = todoRepository;
    }



    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new ServiceException(ServiceErrorCode.USER_NOT_FOUND));
    }

    public User findUserByUserName(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(() ->
                new ServiceException(ServiceErrorCode.USER_NOT_FOUND));
    }

    public User createUser(@Valid User newUser) {
        if (userRepository.existsUserByUsername(newUser.getUsername())) {
            throw new ServiceException(ServiceErrorCode.USER_ALREADY_EXISTS);
        }

        newUser.setId(null);
        newUser.setCreatedOn(null);
        newUser.setUpdatedOn(null);
        return save(newUser);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long userId, User user) {
        var existingUser = findUserById(userId);
        existingUser.setUsername(user.getUsername());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        return save(existingUser);
    }

    public void deleteUserById(Long id) {
        userRepository.delete(findUserById(id));
    }

    public List<Todo> findUserTodo(Long userId) {
        findUserById(userId);
        return todoService.findTodoByUserId(userId);
    }

    public Todo findUserTodoById(Long userId, Long todoId) {
        findUserById(userId);
        return todoService.findUserTodoById(userId,todoId);
    }

    public Todo createUserTodo( Long userId, Todo todo) {
        findUserById(userId);
        todo.setUserId(userId);
        return todoService.createTodo(todo);
    }
    public  Todo updateUserTodo(Long userId, Long todoId, Todo todo) {
        findUserById(userId);
        findUserTodoById(userId,todoId);
        return todoService.updateTodo(todoId,todo);
    }

    public void deleteUserTodoById(Long userId,Long todoId) {
        findUserById(userId);
        findUserTodoById(userId,todoId);
        todoService.deleteTodoById(todoId);
    }
}
