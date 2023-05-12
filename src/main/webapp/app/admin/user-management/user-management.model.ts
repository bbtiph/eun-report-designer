import { IRole } from '../../entities/role/role.model';

export interface IUser {
  id: number | null;
  login?: string;
  firstName?: string | null;
  lastName?: string | null;
  email?: string;
  activated?: boolean;
  langKey?: string;
  roles?: IRole[];
  createdBy?: string;
  createdDate?: Date;
  lastModifiedBy?: string;
  lastModifiedDate?: Date;
}

export class User implements IUser {
  constructor(
    public id: number | null,
    public login?: string,
    public firstName?: string | null,
    public lastName?: string | null,
    public email?: string,
    public activated?: boolean,
    public langKey?: string,
    public roles?: IRole[],
    public createdBy?: string,
    public createdDate?: Date,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Date
  ) {}
}
