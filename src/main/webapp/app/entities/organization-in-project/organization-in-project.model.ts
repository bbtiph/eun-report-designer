import dayjs from 'dayjs/esm';

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
}

export type NewOrganizationInProject = Omit<IOrganizationInProject, 'id'> & { id: null };
