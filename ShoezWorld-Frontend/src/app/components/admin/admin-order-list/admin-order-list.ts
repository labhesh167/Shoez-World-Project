import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrderService } from '../../../services/order';


@Component({
  selector: 'app-admin-order-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-order-list.html',
  styleUrl: './admin-order-list.css'
})
export class AdminOrderList implements OnInit {
  orders: any[] = [];

  constructor(private orderService: OrderService) {}

  ngOnInit() {
    // fetch all orders on component load
    this.orderService.getAllOrders().subscribe(data => {
      this.orders = data;
    });
  }
}
