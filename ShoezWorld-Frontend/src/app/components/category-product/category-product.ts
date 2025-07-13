import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductService } from '../../services/product';
import { CartService } from '../../services/cart';
import { CommonModule } from '@angular/common';
import { Product } from '../../models/product.model';

@Component({
  selector: 'app-category-product',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './category-product.html',
  styleUrl: './category-product.css'
})
export class CategoryProduct implements OnInit {
  categoryId!: number;
  products: Product[] = [];

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private cartService: CartService,
    private cdr: ChangeDetectorRef  // added
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const idParam = params.get('id');
      if (idParam) {
        this.categoryId = Number(idParam);
        this.loadProducts();
      }
    });
  }

  loadProducts() {
    this.productService.getProductsByCategory(this.categoryId).subscribe({
      next: data => {
        this.products = data;
        this.cdr.markForCheck();  // force view refresh after data update
      },
      error: err => {
        console.error('Failed to load products for category:', this.categoryId, err);
        this.products = [];
        this.cdr.markForCheck();  //force refresh on error too
      }
    });
  }

  addToCart(product: Product) {
    const username = localStorage.getItem('username');
    if (username && product.id !== undefined) {
      this.cartService.addToCart(username, product.id, 1).subscribe({
        next: () => alert(`${product.name} added to cart!`),
        error: err => console.error('Add to cart failed', err)
      });
    } else {
      alert('Please login to add items to cart.');
    }
  }
}
