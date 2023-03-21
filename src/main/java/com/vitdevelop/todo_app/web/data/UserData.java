package com.vitdevelop.todo_app.web.data;

import com.vitdevelop.todo_app.core.domain.User;
import lombok.Data;

@Data
public class UserData {
    private String username;
    private String firstname;
    private String lastname;

    public User getUser() {
        var user = new User();
        user.setUsername(this.getUsername());
        user.setFirstName(this.getFirstname());
        user.setLastName(this.getLastname());

        return user;
    }
}
