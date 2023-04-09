package com.usbank.user.accounts.useraccounts.repository;

import com.usbank.user.accounts.useraccounts.model.Accounts;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAccountsRepository extends JpaRepository<Accounts,String> {

    public List<Accounts> findByUserId(long userId);
}
