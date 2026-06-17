package com.ngon.backend.service;

import com.ngon.backend.entity.User;
import com.ngon.backend.dto.DeleteUserRequest;
import com.ngon.backend.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService
{
    private final UserRepository userRepo;

    public UserService(UserRepository userRepo)
    {
        this.userRepo = userRepo;
    }

    public String getMe(Authentication auth)
    {
        return auth.getName();
    }

    public void deleteUser(DeleteUserRequest request)
    {
        User user = userRepo.findByUsername(request.username());

        if (user == null)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        userRepo.delete(user);
    }
}
