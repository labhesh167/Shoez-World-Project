import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../services/auth';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-admin-user-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './admin-user-list.html',
  styleUrl: './admin-user-list.css'
})
export class AdminUserList implements OnInit {

  users: any[] = []; // List to hold users

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  // On component init, load all users
  ngOnInit() {
    this.loadUsers();
  }

  // Fetch users via AuthService
  loadUsers() {
    this.authService.getAllUsers().subscribe({
      next: (data) => {
        this.users = data;
      },
      error: (err) => {
        console.error('Failed to fetch users:', err);
      }
    });
  }

  //Delete a user by username
  deleteUser(username: string) {
  if (confirm(`Are you sure you want to delete ${username}?`)) {
    this.authService.deleteUser(username).subscribe({
      next: () => {
        alert(`${username} deleted successfully.`);

        //Remove user from local users array without reloading all users
        this.users = this.users.filter(user => user.username !== username);
      },
      error: (err) => {
        console.error(`Failed to delete ${username}:`, err);
        alert('Failed to delete user.');
      }
    });
  }
}

}
