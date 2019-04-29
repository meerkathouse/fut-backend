package com.meerkat.house.fut.repository;

import com.meerkat.house.fut.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    List<Account> findAll();

    Account findByUid(Integer uid);
    Account findByIdAndEmailAndSocial(String id, String email, String social);
}
