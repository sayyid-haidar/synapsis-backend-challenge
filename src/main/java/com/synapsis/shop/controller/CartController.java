package com.synapsis.shop.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.synapsis.shop.dbo.Cart;
import com.synapsis.shop.dbo.CartId;
import com.synapsis.shop.dbo.Product;
import com.synapsis.shop.dbo.User;
import com.synapsis.shop.dto.CartDTO;
import com.synapsis.shop.dto.CartRequest;
import com.synapsis.shop.dto.UserDTO;
import com.synapsis.shop.exception.BadRequestException;
import com.synapsis.shop.repository.CartRepository;
import com.synapsis.shop.repository.ProductRepository;
import com.synapsis.shop.repository.UserRepository;
import com.synapsis.shop.util.JwtUtil;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
public class CartController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    JwtUtil jwtUtil;

    @GetMapping("/carts")
    public ResponseEntity<List<CartDTO>> carts(
            @RequestHeader("Authorization") String authToken)
            throws Exception {

        List<CartDTO> response = new ArrayList<>();
        String jwt = authToken.replace("Bearer ", "");
        UserDTO userDTO = this.jwtUtil.parseToken(jwt);

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
            @RequestHeader("Authorization") String authToken,
            @RequestBody @Valid CartRequest request)
            throws Exception {

        String jwt = authToken.replace("Bearer ", "");
        UserDTO userDTO = this.jwtUtil.parseToken(jwt);

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

    @DeleteMapping("/cart/{productId}")
    public ResponseEntity<Cart> updateCart(
            @PathVariable Integer productId,
            @RequestHeader("Authorization") String authToken,
            @RequestBody @Valid CartRequest request)
            throws Exception {

        String jwt = authToken.replace("Bearer ", "");
        UserDTO userDTO = this.jwtUtil.parseToken(jwt);

        CartId cartId = CartId.builder()
                .userId(userDTO.getId())
                .productId(productId)
                .build();

        Cart cart = this.cartRepository.findById(cartId)
                .orElseThrow(BadRequestException::new);

        this.cartRepository.delete(cart);

        return ResponseEntity.ok().body(null);
    }

}