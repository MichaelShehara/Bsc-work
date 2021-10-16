import { Project } from './project';
import { User } from './user';

export class Sprint {
  id?: number;
  name?: string;
  status?: string;
  startAt?: Date;
  endAt?: Date;
  comment?: string;
  goals?: string;
  project?: Project;
  updatedBy?: number;
  createdBy?: User;
  createdAt?: number;
  updatedAt?: number;
  enabled?: boolean;
  voided?: boolean;
}
