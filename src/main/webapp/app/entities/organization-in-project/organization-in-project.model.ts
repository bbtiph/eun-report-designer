import dayjs from 'dayjs/esm';
import { IProject } from 'app/entities/project/project.model';
import { IOrganization } from 'app/entities/organization/organization.model';

export interface IOrganizationInProject {
  id: number;
  status?: string | null;
  joinDate?: dayjs.Dayjs | null;
  fundingForOrganization?: number | null;
  participationToMatchingFunding?: number | null;
  schoolRegistrationPossible?: boolean | null;
  teacherParticipationPossible?: boolean | null;
  ambassadorsPilotTeachersLeadingTeachersIdentified?: boolean | null;
  usersCanRegisterToPortal?: boolean | null;
  project?: Pick<IProject, 'id'> | null;
  organization?: Pick<IOrganization, 'id'> | null;
}

export type NewOrganizationInProject = Omit<IOrganizationInProject, 'id'> & { id: null };
