import { IMinistry } from 'app/entities/ministry/ministry.model';
import { IOrganization } from 'app/entities/organization/organization.model';

export interface IOrganizationInMinistry {
  id: number;
  status?: string | null;
  ministry?: Pick<IMinistry, 'id'> | null;
  organization?: Pick<IOrganization, 'id'> | null;
}

export type NewOrganizationInMinistry = Omit<IOrganizationInMinistry, 'id'> & { id: null };
