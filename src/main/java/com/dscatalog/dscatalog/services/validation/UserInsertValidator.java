package com.dscatalog.dscatalog.services.validation;

import com.dscatalog.dscatalog.dtos.UserInsertDTO;
import com.dscatalog.dscatalog.exceptions.FieldMessage;
import com.dscatalog.dscatalog.models.UserModel;
import com.dscatalog.dscatalog.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UserInsertValid ann) {
    }

    @Override
    public boolean isValid(UserInsertDTO dto, ConstraintValidatorContext context) {



        List<FieldMessage> list = new ArrayList<>();

        UserModel userModel = userRepository.findByEmail(dto.getEmail());
        if(userModel != null){
            list.add(new FieldMessage("email", "Email já existe"));

        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
