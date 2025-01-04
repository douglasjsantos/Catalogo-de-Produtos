package com.dscatalog.dscatalog.controllers;

import com.dscatalog.dscatalog.dtos.EmailDTO;
import com.dscatalog.dscatalog.dtos.NewPasswordDTO;
import com.dscatalog.dscatalog.dtos.UserDTO;
import com.dscatalog.dscatalog.dtos.UserInsertDTO;
import com.dscatalog.dscatalog.services.AuthService;
import com.dscatalog.dscatalog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/recover-token")
    public ResponseEntity<Void> createRecoverToken(@Valid @RequestBody EmailDTO body){
        authService.createRecoverToken(body);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/new-password")
    public ResponseEntity<Void> saveNewPassword(@Valid @RequestBody NewPasswordDTO body){
        authService.saveNewPassword(body);
        return ResponseEntity.noContent().build();
    }


}
