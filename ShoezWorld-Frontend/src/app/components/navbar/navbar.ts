import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../../services/category';
import { AuthService } from '../../services/auth';
import { Category } from '../../models/category.model';
import { Router, RouterModule, RouterLink, NavigationEnd } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule, RouterLink],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css'
})
export class Navbar implements OnInit {
  categories: Category[] = [];
  categoriesLoaded: boolean = false;
  isLoggedIn = false;

  //track if user is admin
  isAdmin = false;

  constructor(
    private categoryService: CategoryService,
    private authService: AuthService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {
    // Update login status on every route change
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.checkLoginStatus();
      }
    });
  }

  ngOnInit() {
    this.categoryService.getCategories().subscribe(data => {
      this.categories = data;
      this.categoriesLoaded = true;
      this.cdr.detectChanges();
    });
    this.checkLoginStatus();
  }

  checkLoginStatus() {
    this.isLoggedIn = this.authService.isLoggedIn();
    this.isAdmin = this.authService.isAdmin();  //check admin status too
  }

  logout() {
    this.authService.logout();
    this.checkLoginStatus();
    this.router.navigate(['/login']);
  }
}
