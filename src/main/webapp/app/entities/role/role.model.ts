import { IPrivilege } from 'app/entities/privilege/privilege.model';

export interface IRole {
  id: number;
  name?: string | null;
  privileges?: Pick<IPrivilege, 'id' | 'name'>[] | null;
}

export type NewRole = Omit<IRole, 'id'> & { id: null };
