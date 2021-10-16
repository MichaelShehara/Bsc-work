
import { Story } from './story';
import { User } from './user';

export class Task {
  id?: number;
  identifier?: string;
  comment?: string;
  summary?: string;
  description?: string;
  type?: string;
  priority?: string;
  severity?: string;
  status?: string;
  storyPoint?: number;
  completedAt?: Date;
  impactedArea?: string;
  story?: Story;
  assignee?: User;

}

