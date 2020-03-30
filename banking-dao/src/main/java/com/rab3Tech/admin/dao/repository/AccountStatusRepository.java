package com.rab3Tech.admin.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rab3Tech.dao.entity.AccountStatus;

public interface AccountStatusRepository extends JpaRepository<AccountStatus, Integer> {

	public Optional<AccountStatus> findByName(String name);

}
