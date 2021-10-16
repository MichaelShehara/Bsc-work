import { User } from './user';
import { Epic } from './epic';

export class Story {
  id?: number;
  identifier?: string;
  summary?: string;
  description?: string;
  status?: number;
  type?: string;
  priority?: string;
  assignee?: User;
  epic?: Epic;
  createdAt?: number;
  createdBy?: number;
  updatedAt?: number;
  updatedBy?: number;
  enabled?: boolean;
  voided?: boolean

}
