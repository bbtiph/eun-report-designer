import { IEventInOrganization } from 'app/entities/event-in-organization/event-in-organization.model';
import { IOrganizationInMinistry } from 'app/entities/organization-in-ministry/organization-in-ministry.model';
import { IOrganizationInProject } from 'app/entities/organization-in-project/organization-in-project.model';
import { IPersonInOrganization } from 'app/entities/person-in-organization/person-in-organization.model';
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
  eventInOrganization?: Pick<IEventInOrganization, 'id'> | null;
  organizationInMinistry?: Pick<IOrganizationInMinistry, 'id'> | null;
  organizationInProject?: Pick<IOrganizationInProject, 'id'> | null;
  personInOrganization?: Pick<IPersonInOrganization, 'id'> | null;
}

export type NewOrganization = Omit<IOrganization, 'id'> & { id: null };
