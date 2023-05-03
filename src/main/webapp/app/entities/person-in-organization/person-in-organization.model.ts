import { IPerson } from 'app/entities/person/person.model';
import { IOrganization } from 'app/entities/organization/organization.model';

export interface IPersonInOrganization {
  id: number;
  roleInOrganization?: string | null;
  person?: Pick<IPerson, 'id'> | null;
  organization?: Pick<IOrganization, 'id'> | null;
}

export type NewPersonInOrganization = Omit<IPersonInOrganization, 'id'> & { id: null };
