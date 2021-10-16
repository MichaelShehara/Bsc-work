import { Role } from "./role";

export class Profile {

  id: number;
  username: string;
  fullName: string;
  password: string;
  updatedBy: number;
  createdBy: number;
  roles: any;
  projects?: any;
}
