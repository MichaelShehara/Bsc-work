import {User} from './user';

export class Project {
  id?: number;
  name?: string;
  code?: string;
  workWeek?: string;
  status?: string;
  leaderName?: string;
  users?: any;
  updatedBy? : number;
  createdBy? : User;
  createdAt? : number;
  updatedAt? : number;
  enabled?: boolean;
  voided?: boolean;
}

