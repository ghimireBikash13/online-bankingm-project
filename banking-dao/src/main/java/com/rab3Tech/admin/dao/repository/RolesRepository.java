package com.rab3Tech.admin.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rab3Tech.dao.entity.Role;

@Repository
public interface RolesRepository extends JpaRepository<Role, Integer> {

}
