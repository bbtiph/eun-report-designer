import { ICountries } from 'app/entities/countries/countries.model';
import { OrgStatus } from 'app/entities/enumerations/org-status.model';

export interface IOrganization {
  id: number;
  eunDbId?: number | null;
  status?: OrgStatus | null;
  officialName?: string | null;
  description?: string | null;
  type?: string | null;
  address?: string | null;
  latitude?: number | null;
  longitude?: number | null;
  maxAge?: number | null;
  minAge?: number | null;
  area?: number | null;
  specialization?: string | null;
  numberOfStudents?: string | null;
  hardwareUsed?: boolean | null;
  ictUsed?: boolean | null;
  website?: string | null;
  image?: string | null;
  imageContentType?: string | null;
  telephone?: string | null;
  fax?: string | null;
  email?: string | null;
  organisationNumber?: string | null;
  country?: Pick<ICountries, 'id'> | null;
}

export type NewOrganization = Omit<IOrganization, 'id'> & { id: null };
