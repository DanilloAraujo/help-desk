package com.example.helpdesk.controller;

import java.time.LocalDate;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.helpdesk.entity.Ticket;
import com.example.helpdesk.entity.User;
import com.example.helpdesk.enums.StatusEnum;
import com.example.helpdesk.response.Response;
import com.example.helpdesk.security.jwt.JwtTokenUtil;
import com.example.helpdesk.service.TicketService;
import com.example.helpdesk.service.UserService;

@RestController
@RequestMapping("/api/ticket")
@CrossOrigin(origins = "*")
public class TicketController {

	@Autowired
	private TicketService ticketService;

	@Autowired
	protected JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<Response<Ticket>> createOrUpdate(HttpServletRequest request, @RequestBody Ticket ticket,
			BindingResult result) {
		Response<Ticket> response = new Response<Ticket>();
		try {
			validateCreateTicket(ticket, result);
			if (result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			ticket.setStatus(StatusEnum.New);
			ticket.setUser(userFromRequest(request));
			ticket.setDate(LocalDate.now());
			ticket.setNumber(generateNumber());
			Ticket ticketPersited = (Ticket) this.ticketService.createOrUpdate(ticket);
			response.setData(ticketPersited);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}

	private void validateCreateTicket(Ticket ticket, BindingResult result) {
		if (ticket.getTitle() == null) {
			result.addError(new ObjectError("Ticket", "Title no information "));
		}
	}
	
	private User userFromRequest(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String email = this.jwtTokenUtil.getUsernameFromToken(token);
		return this.userService.findByEmail(email);
	}
	
	private Long generateNumber() {
		Random random = new Random();
		return (long) random.nextInt(9999);
	}

}
