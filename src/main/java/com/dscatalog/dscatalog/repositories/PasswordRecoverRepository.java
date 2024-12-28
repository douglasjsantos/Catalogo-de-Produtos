package com.dscatalog.dscatalog.repositories;

import com.dscatalog.dscatalog.models.PasswordRecover;
import com.dscatalog.dscatalog.models.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRecoverRepository extends JpaRepository<PasswordRecover, Long> {

}
