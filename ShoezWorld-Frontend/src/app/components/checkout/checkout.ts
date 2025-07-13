import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { CartService } from '../../services/cart';
import { CartItem } from '../../models/cart-item.model';
import { OrderService } from '../../services/order';
import { Router } from '@angular/router';

@Component({
  selector: 'app-checkout',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './checkout.html',
  styleUrls: ['./checkout.css']
})
export class Checkout implements OnInit {

  fullName = '';
  address = '';
  city = '';
  pincode = '';
  phoneNumber = '';
  paymentMethod = 'COD';

  cartItems: CartItem[] = [];
  totalPrice = 0;
  username: string | null = null;
  isSubmitting = false;
  successMessage = '';
  orderPlaced = false;  // Flag to hide form after placing order

  constructor(
    private cartService: CartService,
    private orderService: OrderService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.username = localStorage.getItem('username');
    if (this.username) {
      this.cartService.getCartItems(this.username).subscribe({
        next: data => {
          this.cartItems = data;
          this.calculateTotal();
        },
        error: err => {
          console.error('Failed to load cart items', err);
          this.cartItems = [];
        }
      });
    } else {
      alert('Please login first.');
      this.router.navigate(['/login']);
    }
  }

  calculateTotal() {
    this.totalPrice = this.cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0);
  }

  onSubmit(form: NgForm) {
    if (form.invalid) {
      Object.values(form.controls).forEach(control => control.markAsTouched());
      return;
    }
    this.placeOrder();
  }

  placeOrder() {
    if (this.isSubmitting) return;

    if (!this.username) {
      alert('Please login first.');
      this.router.navigate(['/login']);
      return;
    }

    if (this.cartItems.length === 0) {
      alert('Your cart is empty.');
      return;
    }

    const orderPayload = {
      items: this.cartItems.map(item => ({
        productId: item.productId,
        quantity: item.quantity,
        productName: item.productName,
        price: item.price
      })),
      totalAmount: this.totalPrice,
      status: 'Placed',
      deliveryAddress: {
        fullName: this.fullName,
        address: this.address,
        city: this.city,
        pincode: this.pincode,
        phoneNumber: this.phoneNumber,
        paymentMethod: this.paymentMethod
      }
    };

    this.isSubmitting = true;

    this.orderService.placeOrder(this.username, orderPayload).subscribe({
      next: () => {
        this.cartService.clearCart(this.username!).subscribe(() => {
          this.cartItems = [];
          this.totalPrice = 0;

          this.successMessage = 'Order placed successfully! Redirecting to your orders...';
          this.orderPlaced = true;
          this.cdr.detectChanges();

          setTimeout(() => {
            this.router.navigate(['/orders']);
          }, 1800);
        });
      },
      error: err => {
        console.error('Order placement failed', err);
        alert('Something went wrong while placing order.');
        this.isSubmitting = false;
      }
    });
  }
}
