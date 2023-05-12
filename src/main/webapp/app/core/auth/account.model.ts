import { IRole } from '../../entities/role/role.model';

export class Account {
  constructor(
    public activated: boolean,
    public roles: IRole[],
    public email: string,
    public firstName: string | null,
    public langKey: string,
    public lastName: string | null,
    public login: string,
    public imageUrl: string | null
  ) {}
}
