import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CartService } from '../../services/cart';
import { CartItem } from '../../models/cart-item.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './cart.html',
  styleUrl: './cart.css'
})
export class Cart {
  cartItems: CartItem[] = [];
  totalPrice: number = 0;
  username: string | null = null;

  constructor(
    private cartService: CartService,
    private router: Router
  ) { }

  ngOnInit() {
    const username = localStorage.getItem('username');
    if (username) {
      this.username = username;
      this.cartService.getCartItems(username).subscribe(data => {
        this.cartItems = data;
        this.calculateTotal();
      });
    } else {
      alert('Please login to view your cart.');
    }
  }

  calculateTotal() {
    this.totalPrice = this.cartItems.reduce(
      (sum, item) => sum + item.price * item.quantity,
      0
    );
  }

  removeFromCart(item: CartItem) {
    if (this.username) {
      this.cartService.removeFromCart(this.username, item.productId).subscribe({
        next: () => {
          const targetItem = this.cartItems.find(ci => ci.productId === item.productId);
          if (targetItem) {
            if (targetItem.quantity > 1) {
              targetItem.quantity--;
            } else {
              this.cartItems = this.cartItems.filter(ci => ci.productId !== item.productId);
            }
          }
          this.calculateTotal();
        },
        error: (err) => {
          console.error('Error removing item:', err);
          alert('Failed to remove item.');
        }
      });
    }
  }

  loadCart() {
    if (this.username) {
      this.cartService.getCartItems(this.username).subscribe(data => {
        this.cartItems = data;
        this.calculateTotal();
      });
    }
  }

  // Correct checkout navigation — no API call here
  checkout() {
    if (!this.username) {
      alert('Please login to proceed with checkout.');
      return;
    }

    if (this.cartItems.length === 0) {
      alert('Your cart is empty!');
      return;
    }

    // Just navigate to checkout page
    this.router.navigate(['/checkout']);
  }
}
