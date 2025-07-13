import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Product } from '../../../models/product.model';
import { ProductService } from '../../../services/product';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-admin-product-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './admin-product-list.html',
  styleUrl: './admin-product-list.css'
})
export class AdminProductList implements OnInit {

  products: Product[] = [];

  constructor(
    private productService: ProductService,
    private router: Router
  ) {}

  ngOnInit() {
    this.loadProducts();
  }

  loadProducts() {
    this.productService.getAllProductsForAdmin().subscribe(data => {
      this.products = data;
    });
  }

  deleteProduct(id: number | undefined) {
    if (id === undefined || id === null) {
      alert('Invalid product — missing ID.');
      return;
    }

    if (!confirm('Are you sure you want to delete this product?')) return;

    this.productService.deleteProduct(id).subscribe({
      next: (res) => {
        console.log('Delete success:', res);
        this.products = this.products.filter(product => product.id !== id);
        alert('Product deleted successfully.');
        this.loadProducts();
      },
      error: (err) => {
        console.error('Delete failed:', err);
        alert('Failed to delete product.');
      }
    });
  }

  editProduct(id: number) {
    this.router.navigate(['/admin/products/edit', id]);
  }

  addProduct() {
    this.router.navigate(['/admin/products/add']);
  }
}
