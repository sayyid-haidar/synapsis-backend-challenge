package com.synapsis.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.synapsis.shop.dbo.Transaction;
import com.synapsis.shop.dto.TransactionListResponse;
import com.synapsis.shop.dto.UserDTO;
import com.synapsis.shop.exception.BadRequestException;
import com.synapsis.shop.repository.TransactionRepository;
import com.synapsis.shop.util.JwtUtil;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin(origins = "*")
public class TransactionController {
	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	JwtUtil jwtUtil;

	@GetMapping("/transactions")
	public ResponseEntity<TransactionListResponse> getTransactions(
			@Parameter(hidden = true) @RequestHeader(name = "Authorization", required = false) String authToken,
			@RequestParam(name = "page", required = false, defaultValue = "1") @Min(1) @Valid Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "10") @Min(1) @Valid Integer size)
			throws Exception {

		UserDTO userDTO = this.jwtUtil.parseToken(authToken);

		Pageable pageable = PageRequest.of(page - 1, size);
		Page<Transaction> transactions = this.transactionRepository.findByUserId(userDTO.getId(), pageable);

		TransactionListResponse response = TransactionListResponse.builder()
				.data(transactions.getContent())
				.totalData(transactions.getTotalElements())
				.totalPage(transactions.getTotalPages())
				.sizePage(transactions.getSize())
				.build();

		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/transaction/{transaction_id}")
	public ResponseEntity<Transaction> getTransaction(
			@PathVariable(name = "transaction_id") Integer transactionId,
			@Parameter(hidden = true) @RequestHeader(name = "Authorization", required = false) String authToken)
			throws Exception {

		UserDTO userDTO = this.jwtUtil.parseToken(authToken);

		Transaction transaction = this.transactionRepository
				.findOneByIdAndUserId(transactionId, userDTO.getId())
				.orElseThrow(BadRequestException::new);

		return ResponseEntity.ok().body(transaction);
	}

	@PostMapping("/transaction/{transaction_id}/payment")
	public ResponseEntity<Transaction> payTransaction(
			@PathVariable(name = "transaction_id") Integer transactionId,
			@Parameter(hidden = true) @RequestHeader(name = "Authorization", required = false) String authToken)
			throws Exception {

		UserDTO userDTO = this.jwtUtil.parseToken(authToken);

		Transaction transaction = this.transactionRepository
				.findOneByIdAndUserId(transactionId, userDTO.getId())
				.orElseThrow(BadRequestException::new);

		return ResponseEntity.ok().body(transaction);
	}
}
