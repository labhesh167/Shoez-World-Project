import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Product } from '../../../models/product.model';
import { ProductService } from '../../../services/product';

@Component({
  selector: 'app-admin-edit-product',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './admin-edit-product.html',
  styleUrl: './admin-edit-product.css'
})
export class AdminEditProduct implements OnInit {

  productId!: number;
  product: Product = {
    id: 0,
    name: '',
    description: '',
    price: 0,
    image: '',
    category: { id: 0, name: '' }
  };

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private router: Router
  ) {}

  ngOnInit() {
    this.productId = Number(this.route.snapshot.paramMap.get('id'));
    this.productService.getProductById(this.productId).subscribe({
      next: (data) => this.product = data,
      error: () => alert('Failed to load product details')
    });
  }

  updateProduct() {
    this.productService.updateProduct(this.productId, this.product).subscribe({
      next: () => {
        alert('Product updated successfully.');
        this.router.navigate(['/admin/products']);
      },
      error: (err) => {
        console.error('Update failed:', err);
        alert('Failed to update product.');
      }
    });
  }
}
