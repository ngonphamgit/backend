package com.ngon.backend.user;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController
{
    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/me")
    public UserProfileResponse getMe(Authentication auth)
    {
        return userService.getMe(auth);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete")
    public void deleteUser(@RequestBody DeleteUserRequest request)
    {
        userService.deleteUser(request);
    }
}
