import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class OrderService {
  private apiUrl = 'http://localhost:8080/api/orders';

  constructor(private http: HttpClient) { }

  placeOrder(username: string, order: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/${username}/place`, order);
  }

  getOrdersByUser(username: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/${username}`);
  }

  getAllOrders(): Observable<any> {
    return this.http.get(`${this.apiUrl}/all`);
  }
  cancelOrder(orderId: number): Observable<any> {
  return this.http.put(`${this.apiUrl}/${orderId}/cancel`, {});
}

}
