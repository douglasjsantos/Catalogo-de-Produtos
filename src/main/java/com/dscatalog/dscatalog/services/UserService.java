package com.dscatalog.dscatalog.services;

import com.dscatalog.dscatalog.dtos.RoleDTO;
import com.dscatalog.dscatalog.dtos.UserDTO;
import com.dscatalog.dscatalog.dtos.UserInsertDTO;
import com.dscatalog.dscatalog.exceptions.DatabaseException;
import com.dscatalog.dscatalog.exceptions.EntityNotFoundException;
import com.dscatalog.dscatalog.models.CategoryModel;
import com.dscatalog.dscatalog.models.RoleModel;
import com.dscatalog.dscatalog.models.UserModel;
import com.dscatalog.dscatalog.projections.UserDetailsProjection;
import com.dscatalog.dscatalog.repositories.CategoryRepository;
import com.dscatalog.dscatalog.repositories.RoleRepository;
import com.dscatalog.dscatalog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthService authService;

    @Transactional(readOnly = true)
    public Page<UserDTO> listAllPaged(Pageable pageable){

        Page<UserModel> UserList = repository.findAll(pageable);

        return UserList.map(x -> new UserDTO(x));
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id){

        Optional<UserModel> obj = repository.findById(id);
        UserModel entity = obj.orElseThrow(() -> new EntityNotFoundException("User not found"));

        return new UserDTO(entity);

    }

    @Transactional
    public UserDTO save(UserInsertDTO userInsertDTO) {
        UserModel userModel = new UserModel();
        userModel.setFirstName(userInsertDTO.getFirstName());
        userModel.setLastName(userInsertDTO.getLastName());
        userModel.setEmail(userInsertDTO.getEmail());
        userModel.setPassword(passwordEncoder.encode(userInsertDTO.getPassword()));

        // Verificar se a role "role_operator" já existe no banco
        RoleModel roleOperator = roleRepository.findByAuthority("ROLE_OPERATOR");

        // Se não existir, cria a role e salva no banco
        if (roleOperator == null) {
            roleOperator = new RoleModel();
            roleOperator.setAuthority("ROLE_OPERATOR");
            roleRepository.save(roleOperator);
        }

        // Adicionar a role ao usuário
        userModel.getRoles().add(roleOperator);

        // Adicionar roles do DTO ao usuário
        userInsertDTO.getRoles().forEach(roleDto -> {
            RoleModel role = new RoleModel();
            role.setId(roleDto.getId());
            role.setAuthority(roleDto.getAuthority());
            userModel.getRoles().add(role);
        });

        UserModel savedModel = repository.save(userModel);
        return new UserDTO(savedModel);
    }



    @Transactional
    public UserDTO update(Long id, UserDTO userDTO) {

        try{
            UserModel userModel = repository.getReferenceById(id);
            userModel.setEmail(userModel.getEmail());
            userModel.setFirstName(userDTO.getFirstName());
            userModel.setLastName(userDTO.getLastName());
            userModel.setId(userDTO.getId());
            //userModel.setPassword(userDTO.getPassword());

            return new UserDTO(userModel);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Id not found" + id);
        }

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {

        if(!repository.existsById(id)){
            throw new EntityNotFoundException("Entity not found");
        }
        try {
            repository.deleteById(id);

        }catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<UserDetailsProjection> result = repository.searchUserAndRolesByEmail(username);
        if (result.isEmpty()) {
            throw new UsernameNotFoundException("Email not found");
        }

        UserModel user = new UserModel();
        user.setEmail(result.get(0).getUsername());
        user.setPassword(result.get(0).getPassword());
        for(UserDetailsProjection projection : result){
            user.addRole(new RoleModel(projection.getRoleId(), projection.getAuthority()));
        }

        return user;
    }

    @Transactional(readOnly = true)
    public UserDTO findMe(){

        UserModel entity = authService.authenticated();


        return new UserDTO(entity);

    }
}