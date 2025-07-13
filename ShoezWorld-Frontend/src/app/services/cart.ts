import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CartItem } from '../models/cart-item.model';

@Injectable({ 
  providedIn: 'root'
 })
export class CartService {

  private apiUrl = 'http://localhost:8080/api/cart';

  constructor(private http: HttpClient) {}

  getCartItems(username: string): Observable<CartItem[]> {
    return this.http.get<CartItem[]>(`${this.apiUrl}/${username}`);
  }

  
  addToCart(username: string, productId: number, quantity: number = 1): Observable<any> {
    const cartItemDTO = {
    productId: productId,
    quantity: quantity
  };
   return this.http.post(`${this.apiUrl}/${username}/add`, cartItemDTO);
  }

  removeFromCart(username: string, productId: number): Observable<string> {
  return this.http.delete(`${this.apiUrl}/${username}/remove/${productId}`, { responseType: 'text' });
}


  clearCart(username: string): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${username}/clear`);
  
  }
}
