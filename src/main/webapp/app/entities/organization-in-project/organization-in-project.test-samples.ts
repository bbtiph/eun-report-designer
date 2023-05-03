import dayjs from 'dayjs/esm';

import { IOrganizationInProject, NewOrganizationInProject } from './organization-in-project.model';

export const sampleWithRequiredData: IOrganizationInProject = {
  id: 60988,
};

export const sampleWithPartialData: IOrganizationInProject = {
  id: 82230,
  fundingForOrganization: 71932,
  participationToMatchingFunding: 273,
  schoolRegistrationPossible: true,
};

export const sampleWithFullData: IOrganizationInProject = {
  id: 38275,
  status: 'Avon',
  joinDate: dayjs('2023-04-28'),
  fundingForOrganization: 88812,
  participationToMatchingFunding: 6132,
  schoolRegistrationPossible: true,
  teacherParticipationPossible: false,
  ambassadorsPilotTeachersLeadingTeachersIdentified: false,
  usersCanRegisterToPortal: false,
};

export const sampleWithNewData: NewOrganizationInProject = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
