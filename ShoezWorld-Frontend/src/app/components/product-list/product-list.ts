import { Component, OnInit } from '@angular/core';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { Product } from '../../models/product.model';
import { ProductService } from '../../services/product';
import { ActivatedRoute, Router } from '@angular/router';
import { CartService } from '../../services/cart';
import { AuthService } from '../../services/auth';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule, CurrencyPipe],
  templateUrl: './product-list.html',
  styleUrl: './product-list.css'
})
export class ProductList implements OnInit {
  products: Product[] = [];
  categoryId: number | null = null;
  routeSub!: Subscription;

  constructor(
    private productService: ProductService,
    private route: ActivatedRoute,
    private cartService: CartService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    this.routeSub = this.route.paramMap.subscribe(params => {
      const categoryId = Number(params.get('id'));
      if (categoryId) {
        this.productService.getProductsByCategory(categoryId).subscribe(data => {
          this.products = data;
        });
      } else {
        this.productService.getAllProducts().subscribe(data => {
          this.products = data;
        });
      }
    });
  }

  addToCart(product: Product) {
    if (!this.authService.isLoggedIn()) {
      alert(' Please log in to add items to your cart.');
      this.router.navigate(['/login']);
      return;
    }

    const username = this.authService.getLoggedInUsername();
    if (!username) {
      alert(' User session invalid — please log in again.');
      this.authService.logout();
      this.router.navigate(['/login']);
      return;
    }

    if (product.id !== undefined) {
      this.cartService.addToCart(username, product.id, 1).subscribe({
        next: () => alert(`${product.name} added to cart!`),
        error: err => {
          console.error('Add to cart failed', err);
          if (err.status === 401) {
            alert('Session expired — please login again.');
            this.authService.logout();
            this.router.navigate(['/login']);
          } else {
            alert('Something went wrong adding to cart.');
          }
        }
      });
    } else {
      alert('Invalid product — cannot add to cart.');
    }
  }
}
