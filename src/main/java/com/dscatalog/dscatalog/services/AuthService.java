package com.dscatalog.dscatalog.services;

import com.dscatalog.dscatalog.dtos.EmailDTO;
import com.dscatalog.dscatalog.exceptions.EntityNotFoundException;
import com.dscatalog.dscatalog.models.PasswordRecover;
import com.dscatalog.dscatalog.models.UserModel;
import com.dscatalog.dscatalog.repositories.PasswordRecoverRepository;
import com.dscatalog.dscatalog.repositories.UserRepository;
import jakarta.persistence.Transient;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
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

}
