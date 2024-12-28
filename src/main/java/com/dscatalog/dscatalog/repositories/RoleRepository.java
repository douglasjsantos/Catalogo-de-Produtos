package com.dscatalog.dscatalog.repositories;

import com.dscatalog.dscatalog.models.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleModel, Long> {

    RoleModel findByAuthority(String authority);
}
