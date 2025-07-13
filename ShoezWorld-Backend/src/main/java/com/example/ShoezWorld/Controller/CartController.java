package com.example.ShoezWorld.Controller;

import com.example.ShoezWorld.Model.CartItem;
import com.example.ShoezWorld.Model.Product;
import com.example.ShoezWorld.Model.User;
import com.example.ShoezWorld.DTO.CartItemDTO;
import com.example.ShoezWorld.Service.CartService;
import com.example.ShoezWorld.Service.ProductService;
import com.example.ShoezWorld.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @GetMapping("/{username}")
    public ResponseEntity<List<CartItemDTO>> getCartItems(@PathVariable String username) {
        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isEmpty())
            return ResponseEntity.badRequest().build();

        List<CartItem> items = cartService.getCartItems(userOpt.get());

        // Convert to DTO list
        List<CartItemDTO> itemDTOs = items.stream().map(item -> new CartItemDTO(item.getId(),
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getProduct().getDescription(),
                item.getProduct().getPrice(),
                item.getQuantity(),item.getProduct().getImage()
                )).toList();

        return ResponseEntity.ok(itemDTOs);
    }

    @PostMapping("/{username}/add")
    public ResponseEntity<?> addCartItem(@PathVariable String username, @RequestBody CartItemDTO cartItemDTO) {
        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isEmpty())
            return ResponseEntity.badRequest().body("User not found");

        Optional<Product> productOpt = productService.findById(cartItemDTO.getProductId());
        if (productOpt.isEmpty())
            return ResponseEntity.badRequest().body("Product not found");

        CartItem cartItem = new CartItem();
        cartItem.setUser(userOpt.get());
        cartItem.setProduct(productOpt.get());
        cartItem.setQuantity(cartItemDTO.getQuantity());

         CartItem savedItem = cartService.addOrUpdateCartItem(userOpt.get(), productOpt.get(), cartItemDTO.getQuantity());
        return ResponseEntity.ok(savedItem);
    }

    @DeleteMapping("/{username}/remove/{productId}")
    public ResponseEntity<?> removeCartItem(@PathVariable String username, @PathVariable Long productId) {
        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isEmpty())
            return ResponseEntity.badRequest().body("User not found");

        cartService.removeCartItem(userOpt.get(), productId);
        return ResponseEntity.ok("Item removed");
    }

    @DeleteMapping("/{username}/clear")
    public ResponseEntity<?> clearCart(@PathVariable String username) {
        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isEmpty())
            return ResponseEntity.badRequest().body("User not found");

        cartService.clearCart(userOpt.get());
        return ResponseEntity.ok("Cart cleared");
    }
}
