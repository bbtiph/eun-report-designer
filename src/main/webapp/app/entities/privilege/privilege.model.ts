import { IRole } from 'app/entities/role/role.model';

export interface IPrivilege {
  id: number;
  name?: string | null;
  roles?: Pick<IRole, 'id'>[] | null;
}

export type NewPrivilege = Omit<IPrivilege, 'id'> & { id: null };
