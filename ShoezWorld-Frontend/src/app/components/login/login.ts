import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {
  username: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  loginUser() {
    const user = {
      username: this.username,
      password: this.password
    };

    this.authService.login(user).subscribe({
      next: (res) => {
        localStorage.setItem('jwtToken', res.token);
        alert('Login successful!');
        this.router.navigate(['/']);
        localStorage.setItem('username', this.username);
      },
      error: (err) => {
        console.error('Login error:', err);
        this.errorMessage = 'Invalid credentials.';
      }
    });
  }
}
