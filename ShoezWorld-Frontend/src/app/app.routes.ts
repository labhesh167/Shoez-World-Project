import { Routes } from '@angular/router';

// Import your user-facing components
import { Home } from './components/home/home';
import { ProductList } from './components/product-list/product-list';
import { Cart } from './components/cart/cart';
import { OrderHistory } from './components/order-history/order-history';
import { Login } from './components/login/login';
import { Register } from './components/register/register';
import { CategoryProduct } from './components/category-product/category-product';
import { Checkout } from './components/checkout/checkout';
// Import your admin components
import { AdminProductList } from './components/admin/admin-product-list/admin-product-list';
import { AdminOrderList } from './components/admin/admin-order-list/admin-order-list';
import { AdminUserList } from './components/admin/admin-user-list/admin-user-list';
import { AdminAddProduct } from './components/admin/admin-add-product/admin-add-product';
import { AdminEditProduct } from './components/admin/admin-edit-product/admin-edit-product';
import { AdminDashboard } from './components/admin/admin-dashboard/admin-dashboard'; 

export const routes: Routes = [
  { path: '', component: Home },
  { path: 'products', component: ProductList },
  { path: 'products/category/:id', component: ProductList, runGuardsAndResolvers: 'paramsChange' },
  { path: 'cart', component: Cart },
  { path: 'orders', component: OrderHistory },
  { path: 'category/:id', component: CategoryProduct, runGuardsAndResolvers: 'paramsChange' },
  { path: 'category/:id/products', component: ProductList, runGuardsAndResolvers: 'paramsChange' },
  { path: 'login', component: Login },
  { path: 'register', component: Register },
  { path: 'checkout', component: Checkout },

  //Admin routes
  { path: 'admin/products', component: AdminProductList },
  { path: 'admin/products/add', component: AdminAddProduct },
  { path: 'admin/orders', component: AdminOrderList },
  { path: 'admin/users', component: AdminUserList },
  { path: 'admin/products/edit/:id', component: AdminEditProduct },
  { path: 'admin/dashboard', component: AdminDashboard }, //added dashboard route

  // fallback
  { path: '**', redirectTo: '' }
];
