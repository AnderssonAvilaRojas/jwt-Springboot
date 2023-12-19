package com.quipux.login.Private;




import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserControler {
    

    
    @GetMapping
    public String get()
    {
        return "get, USER Bienvenido a la ruta protegida";
    }
    @PostMapping
    public String post()
    {
        return "post, USER Bienvenido a la ruta protegida";
    }

    @PutMapping
    public String put()
    {
        return "put, USER Bienvenido a la ruta protegida";
    }

        @DeleteMapping
    public String delete()
    {
        return "delete, USER Bienvenido a la ruta protegida";
    }
}
