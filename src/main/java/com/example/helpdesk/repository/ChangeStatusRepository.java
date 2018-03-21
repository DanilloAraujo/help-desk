package com.example.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.helpdesk.entity.ChangeStatus;

public interface ChangeStatusRepository extends JpaRepository<ChangeStatus, Long> {
	
	Iterable<ChangeStatus> findByTicketIdOrderByDateChangeStatusDesc(Long ticketId);
}
