package com.ngon.backend.health;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class HealthController
{
    @GetMapping("/health")
    public String getHealth()
    {
        return "Healthy";
    }
}
