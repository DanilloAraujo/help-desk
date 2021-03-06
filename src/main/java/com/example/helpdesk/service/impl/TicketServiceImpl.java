package com.example.helpdesk.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.helpdesk.entity.ChangeStatus;
import com.example.helpdesk.entity.Ticket;
import com.example.helpdesk.enums.PriorityEnum;
import com.example.helpdesk.enums.StatusEnum;
import com.example.helpdesk.repository.ChangeStatusRepository;
import com.example.helpdesk.repository.TicketRepository;
import com.example.helpdesk.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private ChangeStatusRepository changeStatusRepository;

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Ticket createOrUpdate(Ticket ticket) {
		return this.ticketRepository.save(ticket);
	}

	@Override
	public Ticket findById(Long id) {
		return this.ticketRepository.findOne(id);
	}

	@Override
	public void delete(Long id) {
		this.ticketRepository.delete(id);
	}

	@Override
	public Page<Ticket> listTicket(int page, int count) {
		Pageable pages = new PageRequest(page, count);
		return this.ticketRepository.findAll(pages);
	}

	@Override
	public ChangeStatus createChangeStatus(ChangeStatus changeStatus) {
		return this.changeStatusRepository.save(changeStatus);
	}

	@Override
	public Iterable<ChangeStatus> lisChangeStatus(Long ticketId) {
		return this.changeStatusRepository.findByTicketIdOrderByDateChangeStatusDesc(ticketId);
	}

	@Override
	public Page<Ticket> findByCurrentUser(int page, int count, Long userId) {
		Pageable pages = new PageRequest(page, count);
		return this.ticketRepository.findByUserIdOrderByDateDesc(pages, userId);
	}

	@Override
	public Page<Ticket> findByParameters(int page, int count, String title, String status, String priority) {
		Pageable pages = new PageRequest(page, count);
		// return this.ticketRepository
		// .findByTitleIgnoreCaseContainingAndStatusContainingAndPriorityContainingOrderByDateDesc(title,
		// status,
		// priority, pages);
		StringBuilder query = new StringBuilder();
		query.append("SELECT t FROM Ticket t WHERE t.title LIKE :title");
		if (status != "")
			query.append(" AND t.status = :status");
		if (priority != "")
			query.append(" AND t.priority = :priority");

		TypedQuery<Ticket> result = manager.createQuery(query.toString(), Ticket.class);
		result.setParameter("title", "%" + title + "%");
		if (status != "")
			result.setParameter("status", StatusEnum.valueOf(status));
		if (priority != "")
			result.setParameter("priority", PriorityEnum.valueOf(priority));

		return new PageImpl<>(result.getResultList(), pages, count);
	}

	@Override
	public Page<Ticket> findByParametersAndCurrentUser(int page, int count, String title, String status,
			String priority, Long userId) {
		Pageable pages = new PageRequest(page, count);
		// return this.ticketRepository
		// .findByTitleIgnoreCaseContainingAndStatusContainingAndPriorityContainingAndUserIdOrderByDateDesc(title,
		// status, priority, userId, pages);

		StringBuilder query = new StringBuilder();
		query.append("SELECT t FROM Ticket t WHERE lower(t.title) LIKE lower(:title)");
		if (status != "")
			query.append(" AND t.status = :status");
		if (priority != "")
			query.append(" AND t.priority = :priority");
		query.append(" AND t.user.id = :userId");

		TypedQuery<Ticket> result = manager.createQuery(query.toString(), Ticket.class);
		result.setParameter("title", "%" + title + "%");
		if (status != "")
			result.setParameter("status", StatusEnum.valueOf(status));
		if (priority != "")
			result.setParameter("priority", PriorityEnum.valueOf(priority));
		result.setParameter("userId", userId);

		return new PageImpl<>(result.getResultList(), pages, count);
	}

	@Override
	public Page<Ticket> findByNumber(int page, int count, Long number) {
		Pageable pages = new PageRequest(page, count);
		return this.ticketRepository.findByNumber(number, pages);
	}

	@Override
	public Iterable<Ticket> findAll() {
		return this.ticketRepository.findAll();
	}

	@Override
	public Page<Ticket> findByParameterAndAssignedUser(int page, int count, String title, String status,
			String priority, Long assignedUser) {
		Pageable pages = new PageRequest(page, count);
		// return this.ticketRepository
		// .findByTitleIgnoreCaseContainingAndStatusContainingAndPriorityContainingAndAssignedUserIdOrderByDateDesc(
		// title, status, priority, assignedUser, pages);
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT t FROM Ticket t WHERE t.title LIKE :title");
		if (status != "")
			query.append(" AND t.status = :status");
		if (priority != "")
			query.append(" AND t.priority = :priority");
		query.append(" AND t.assignedUser.id = :assignedUserId");

		TypedQuery<Ticket> result = manager.createQuery(query.toString(), Ticket.class);
		result.setParameter("title", "%" + title + "%");
		if (status != "")
			result.setParameter("status", StatusEnum.valueOf(status));
		if (priority != "")
			result.setParameter("priority", PriorityEnum.valueOf(priority));
		result.setParameter("assignedUserId", assignedUser);

		return new PageImpl<>(result.getResultList(), pages, count);
	}

}
