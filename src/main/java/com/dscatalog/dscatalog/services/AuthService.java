package com.dscatalog.dscatalog.services;

import com.dscatalog.dscatalog.dtos.EmailDTO;
import com.dscatalog.dscatalog.dtos.NewPasswordDTO;
import com.dscatalog.dscatalog.exceptions.EntityNotFoundException;
import com.dscatalog.dscatalog.models.PasswordRecover;
import com.dscatalog.dscatalog.models.UserModel;
import com.dscatalog.dscatalog.repositories.PasswordRecoverRepository;
import com.dscatalog.dscatalog.repositories.UserRepository;
import jakarta.persistence.Transient;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Value("${email.password-recover.token.minutes}")
    private Long tokenMinutes;

    @Value("${email.password-recover.uri}")
    private String recoverUri;

    @Autowired
    private PasswordRecoverRepository passwordRecoverRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void createRecoverToken(EmailDTO body) {

        UserModel userModel = userRepository.findByEmail(body.getEmail());

        if(userModel == null ){
            throw new EntityNotFoundException("Email não encontrado");
        }

        PasswordRecover entity = new PasswordRecover();

        String token = UUID.randomUUID().toString();

        entity.setEmail(body.getEmail());
        entity.setToken(token);
        entity.setExpiration(Instant.now().plusSeconds(tokenMinutes * 60L));

        entity = passwordRecoverRepository.save(entity);

        String text = "Acesse o link para definir uma nova senha\n\n" + recoverUri + token + " .Validade de " + tokenMinutes + " minutos";
        emailService.sendEmail(body.getEmail(), "Recuperação de senha", text);
    }

    @Transactional
    public void saveNewPassword(NewPasswordDTO body) {

        List<PasswordRecover> result = passwordRecoverRepository.searchValidTokens(body.getToken(), Instant.now());
        if(result.isEmpty()){
            throw new jakarta.persistence.EntityNotFoundException("Token inválido");
        }

        UserModel userModel = userRepository.findByEmail(result.get(0).getEmail());
        userModel.setPassword(passwordEncoder.encode(body.getPassword()));
        userModel = userRepository.save(userModel);

    }

    protected UserModel authenticated(){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
            String username = jwtPrincipal.getClaim("username");

            return userRepository.findByEmail(username);
        } catch (Exception e){
            throw new UsernameNotFoundException("Invalid user");
        }
    }
}
