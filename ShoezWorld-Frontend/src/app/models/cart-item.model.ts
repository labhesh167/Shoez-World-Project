import { Product } from './product.model';
import { User } from './user.model';

export interface CartItem {
  id: number;
  productId: number;
  productName: string;
  description: string;
  image: string;
  price: number;
  quantity: number;
}
