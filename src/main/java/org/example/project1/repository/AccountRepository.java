package org.example.project1.repository;

import org.example.project1.Entity.Account; //  Ensure correct import
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //  Ensure this is present
public interface AccountRepository extends JpaRepository<Account, Long> {
}
