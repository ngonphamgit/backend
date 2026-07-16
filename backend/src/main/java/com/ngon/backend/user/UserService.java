package com.ngon.backend.user;

import com.ngon.backend.exception.UserNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

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
            throw new UserNotFoundException("User not found");
        }

        userRepo.delete(user);
    }
}
