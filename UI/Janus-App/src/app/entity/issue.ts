import { ProjectRelease } from './project_release';
import { Sprint } from './sprint';
import { User } from './user';

export class Issue {
  id?: number;
  identifier?: string;
  summary?: string;
  description?: string;
  priority?: string;
  severity?: string;
  impactedArea?: string;
  assignee?: User;
  fixVersion?: ProjectRelease;
  affectVersion?: ProjectRelease;
  comment?: string;
  storyPoint?: number;
  // reference?: string;
  status?: any;
  createdBy?: User;
  resolvedBy?: User;
  verifiedBy?: User;
  sprint?: Sprint;
  createdAt?: number;
  updatedAt?: number;
  enabled?: boolean;
  voided?: boolean
}
