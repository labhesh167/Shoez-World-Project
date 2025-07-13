import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrderService } from '../../services/order';
import { Order } from '../../models/order.model';

@Component({
  selector: 'app-order-history',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './order-history.html',
  styleUrl: './order-history.css'
})
export class OrderHistory implements OnInit {
  orders: Order[] = [];

  constructor(private orderService: OrderService) { }

  ngOnInit() {
    this.loadOrders();
  }

  loadOrders(): void {
    const username = localStorage.getItem('username');

    if (username) {
      this.orderService.getOrdersByUser(username).subscribe({
        next: (data: Order[]) => {
          this.orders = data.sort(
            (a, b) => new Date(b.orderDate).getTime() - new Date(a.orderDate).getTime()
          );
        },
        error: err => {
          console.error('Failed to fetch orders', err);
          alert('Unable to load your orders right now.');
        }
      });
    } else {
      alert('Please login to view your orders.');
    }
  }

  cancelOrder(orderId: number): void {
    if (confirm('Are you sure you want to cancel this order?')) {
      this.orderService.cancelOrder(orderId).subscribe({
        next: (res: any) => {
          alert(res.message || 'Order cancelled successfully.');

          // Find the order locally and update its status immediately
          const orderToUpdate = this.orders.find(o => o.id === orderId);
          if (orderToUpdate) {
            orderToUpdate.status = 'Cancelled';
          }
        },
        error: (err) => {
          console.error('Order cancellation failed:', err);
          alert('Failed to cancel order. Please try again later.');
        }
      });

    }
  }

  getTotal(items: { price: number; quantity: number }[]): number {
    return items.reduce((sum, item) => sum + item.price * item.quantity, 0);
  }
}
