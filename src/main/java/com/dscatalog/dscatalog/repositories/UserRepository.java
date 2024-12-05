package com.dscatalog.dscatalog.repositories;

import com.dscatalog.dscatalog.models.ProductModel;
import com.dscatalog.dscatalog.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {
}
