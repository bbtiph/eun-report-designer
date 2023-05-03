import { IEunTeamMember, NewEunTeamMember } from './eun-team-member.model';

export const sampleWithRequiredData: IEunTeamMember = {
  id: 4864,
};

export const sampleWithPartialData: IEunTeamMember = {
  id: 79138,
  role: 'haptic',
  status: 'wireless Connecticut transmit',
};

export const sampleWithFullData: IEunTeamMember = {
  id: 43360,
  role: 'GB deposit 24/365',
  status: 'Washington',
};

export const sampleWithNewData: NewEunTeamMember = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
