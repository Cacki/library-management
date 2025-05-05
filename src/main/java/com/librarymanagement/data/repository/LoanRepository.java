package com.librarymanagement.data.repository;

import com.librarymanagement.data.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
