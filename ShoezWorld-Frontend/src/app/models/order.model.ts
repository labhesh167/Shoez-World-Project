import { User } from './user.model';

export interface Order {
  id: number;
  user: User;
  totalAmount: number;
  items: any[];
  orderDate: string;
  status: string;
  
}
