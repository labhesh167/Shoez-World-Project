import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Product } from '../../../models/product.model';
import { ProductService } from '../../../services/product';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-admin-add-product',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './admin-add-product.html',
  styleUrl: './admin-add-product.css'
})
export class AdminAddProduct {

  product: Product = {
    name: '',
    description: '',
    price: 0,
    image: '',
    category: { id: 0, name: '' }
  };

  constructor(
    private productService: ProductService,
    private router: Router
  ) {}

  addProduct() {
    this.productService.createProduct(this.product).subscribe({
      next: () => {
        alert('Product added successfully.');
        this.router.navigate(['/admin/products']);
      },
      error: (err) => {
        console.error('Failed to add product:', err);
        alert('Failed to add product.');
      }
    });
  }
}
