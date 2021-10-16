import { User } from './user';
import { Project } from './project';

export class Epic {
  id?: number;
  identifier?: string;
  name: string;
  summary: string;
  description: string;
  status: string;
  priority: string;
  assignee?: User;
  updatedBy?: number;
  createdBy?: User;
  createdAt?: number;
  updatedAt?: number;
  enabled?: boolean;
  voided?: boolean;
  proId?: number;
  project?: Project;


}
