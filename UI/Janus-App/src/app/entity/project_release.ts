import { Project } from './project';
import { User } from "./user";

export class ProjectRelease {
  id?: number;
  name?: string;
  description?: string;
  targetDate?: Date;
  project?: Project;
  updatedBy? : number;
  createdBy? : User;
  createdAt? : number;
  updatedAt? : number;
  enabled?: boolean;
  voided?: boolean;
}

