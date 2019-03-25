package com.itechart.contacts.core.role.repository;

import com.itechart.contacts.core.role.entity.Role;
import com.itechart.contacts.core.role.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
