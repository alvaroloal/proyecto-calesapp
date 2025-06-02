import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';


interface Comment {
  user: string;
  text: string;
  date: Date;
}

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {

  comments: Comment[] = [
    { user: 'User 1', text: 'Comentario 1', date: new Date() },
    { user: 'User 2', text: 'Comentario 2', date: new Date() }
  ];

}
