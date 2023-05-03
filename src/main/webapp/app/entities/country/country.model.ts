import { IMinistry } from 'app/entities/ministry/ministry.model';
import { IOperationalBodyMember } from 'app/entities/operational-body-member/operational-body-member.model';
import { IOrganization } from 'app/entities/organization/organization.model';
import { IPerson } from 'app/entities/person/person.model';

export interface ICountry {
  id: number;
  countryName?: string | null;
  ministry?: Pick<IMinistry, 'id'> | null;
  operationalBodyMember?: Pick<IOperationalBodyMember, 'id'> | null;
  organization?: Pick<IOrganization, 'id'> | null;
  person?: Pick<IPerson, 'id'> | null;
}

export type NewCountry = Omit<ICountry, 'id'> & { id: null };
