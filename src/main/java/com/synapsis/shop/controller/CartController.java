package com.synapsis.shop.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.synapsis.shop.dbo.Cart;
import com.synapsis.shop.dbo.CartId;
import com.synapsis.shop.dbo.Product;
import com.synapsis.shop.dbo.Transaction;
import com.synapsis.shop.dbo.User;
import com.synapsis.shop.dto.CartDTO;
import com.synapsis.shop.dto.CartRequest;
import com.synapsis.shop.dto.UserDTO;
import com.synapsis.shop.exception.BadRequestException;
import com.synapsis.shop.repository.CartRepository;
import com.synapsis.shop.repository.ProductRepository;
import com.synapsis.shop.repository.TransactionRepository;
import com.synapsis.shop.repository.UserRepository;
import com.synapsis.shop.util.JwtUtil;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
public class CartController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	CartRepository cartRepository;

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	JwtUtil jwtUtil;

	@GetMapping("/carts")
	public ResponseEntity<List<CartDTO>> carts(
			@Parameter(hidden = true) @RequestHeader(name = "Authorization", required = false) String authToken)
			throws Exception {

		List<CartDTO> response = new ArrayList<>();

		UserDTO userDTO = this.jwtUtil.parseToken(authToken);

		User user = this.userRepository.findById(userDTO.getId())
				.orElseThrow(BadRequestException::new);

		for (Cart cart : user.getCarts()) {
			Product product = cart.getProduct();
			if (product == null) {
				continue;
			}

			CartDTO dto = CartDTO.builder()
					.productId(product.getId())
					.productName(product.getName())
					.quantity(cart.getQuantity())
					.netPrice(cart.getQuantity() * product.getPrice())
					.build();

			response.add(dto);
		}

		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/cart")
	public ResponseEntity<Cart> addCart(
			@Parameter(hidden = true) @RequestHeader(name = "Authorization", required = false) String authToken,
			@RequestBody @Valid CartRequest request)
			throws Exception {

		UserDTO userDTO = this.jwtUtil.parseToken(authToken);

		User user = this.userRepository.findById(userDTO.getId())
				.orElseThrow(BadRequestException::new);

		Product product = this.productRepository.findById(request.getProductId())
				.orElseThrow(BadRequestException::new);

		CartId cartId = CartId.builder()
				.userId(user.getId())
				.productId(product.getId())
				.build();

		Cart cart = this.cartRepository.findById(cartId)
				.orElse(new Cart());

		cart.setUser(user);
		cart.setProduct(product);
		cart.setQuantity(cart.getQuantity() + request.getQuantity());

		this.cartRepository.save(cart);

		return ResponseEntity.ok().body(cart);
	}

	@DeleteMapping("/cart/{product_id}")
	public ResponseEntity<Cart> updateCart(
			@Parameter(hidden = true) @RequestHeader(name = "Authorization", required = false) String authToken,
			@PathVariable(name = "product_id") Integer productId,
			@RequestBody @Valid CartRequest request)
			throws Exception {

		UserDTO userDTO = this.jwtUtil.parseToken(authToken);

		CartId cartId = CartId.builder()
				.userId(userDTO.getId())
				.productId(productId)
				.build();

		Cart cart = this.cartRepository.findById(cartId)
				.orElseThrow(BadRequestException::new);

		this.cartRepository.delete(cart);

		return ResponseEntity.ok().body(null);
	}

	@Transactional
	@PostMapping("/cart/{product_id}/checkout")
	public ResponseEntity<Transaction> checkoutCart(
			@Parameter(hidden = true) @RequestHeader(name = "Authorization", required = false) String authToken,
			@PathVariable(name = "product_id") Integer productId)
			throws Exception {

		UserDTO userDTO = this.jwtUtil.parseToken(authToken);

		CartId cartId = CartId.builder()
				.userId(userDTO.getId())
				.productId(productId)
				.build();

		Cart cart = this.cartRepository.findById(cartId)
				.orElseThrow(BadRequestException::new);

		Transaction transaction = Transaction.builder()
				.productId(cart.getProduct().getId())
				.productName(cart.getProduct().getName())
				.userId(userDTO.getId())
				.quantity(cart.getQuantity())
				.netPrice(cart.getQuantity() * cart.getProduct().getPrice())
				.build();

		this.transactionRepository.save(transaction);
		this.cartRepository.delete(cart);

		return ResponseEntity.ok().body(transaction);
	}

}