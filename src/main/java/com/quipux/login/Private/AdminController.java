package com.quipux.login.Private;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    
    @GetMapping
    public String get()
    {
        return "get,ADMIN Bienvenido a la ruta protegida";
    }
    @PostMapping
    public String post()
    {
        return "post, ADMIN Bienvenido a la ruta protegida";
    }

    @PutMapping
    public String put()
    {
        return "put,ADMIN Bienvenido a la ruta protegida";
    }

    @DeleteMapping
    public String delete()
    {
        return "delete ,ADMIN Bienvenido a la ruta protegida";
    }
    
}
