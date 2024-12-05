package com.dscatalog.dscatalog.dtos;

import com.dscatalog.dscatalog.services.validation.UserInsertValid;

@UserInsertValid
public class UserInsertDTO extends UserDTO{

    public String password;

    public UserInsertDTO(){
        super();
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
