package com.example.helpdesk.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.example.helpdesk.entity.ChangeStatus;
import com.example.helpdesk.entity.Ticket;

@Component
public interface TicketService {

	Ticket createOrUpdate(Ticket ticket);

	Ticket findById(Long id);

	void delete(Long id);

	Page<Ticket> listTicket(int page, int count);

	ChangeStatus createChangeStatus(ChangeStatus changeStatus);

	Iterable<ChangeStatus> lisChangeStatus(Long ticketId);

	Page<Ticket> findByCurrentUser(int page, int count, Long userId);

	Page<Ticket> findByParameters(int page, int count, String title, String status, String priority);

	Page<Ticket> findByParametersAndCurrentUser(int page, int count, String title, String status, String priority,
			Long userId);

	Page<Ticket> findByNumber(int page, int count, Long number);

	Iterable<Ticket> findAll();

	Page<Ticket> findByParameterAndAssignedUser(int page, int count, String title, String status, String priority,
			Long assignedUser);

}
