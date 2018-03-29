package com.example.helpdesk.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.helpdesk.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

	Page<Ticket> findByUserIdOrderByDateDesc(Pageable pages, Long userId);

	Page<Ticket> findByTitleIgnoreCaseContainingAndStatusContainingAndPriorityContainingOrderByDateDesc(String title,
			String status, String priority, Pageable pages);

	Page<Ticket> findByTitleIgnoreCaseContainingAndStatusContainingAndPriorityContainingAndUserIdOrderByDateDesc(
			String title, String status, String priority, Long userId, Pageable pages);

	Page<Ticket> findByTitleIgnoreCaseContainingAndStatusContainingAndPriorityContainingAndAssignedUserIdOrderByDateDesc(
			String title, String status, String priority, Long userId, Pageable pages);

	Page<Ticket> findByNumber(Long number, Pageable pages);
}
