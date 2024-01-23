import dayjs from 'dayjs/esm';

import { IJobInfo, NewJobInfo } from './job-info.model';

export const sampleWithRequiredData: IJobInfo = {
  id: 75551,
};

export const sampleWithPartialData: IJobInfo = {
  id: 53324,
  externalId: 'methodology purple monitor',
  billToCountryRegionCode: 'Kentucky Industrial',
  sellToContact: 'distributed',
  yourReference: 'Spain Loan',
  contractNo: 'lime payment',
  statusProposal: 'Jewelery',
  startingDate: dayjs('2024-01-18'),
  durationInMonths: 8184,
  projectManager: 'superstructure',
  projectManagerMail: 'primary New Analyst',
  eunRole: 'Concrete',
  visaNumber: 'portal Human',
  jobProgram: 'Sharable Clothing architect',
  programManager: 'Pizza',
  fundingEUN: 21870,
  fundingConsortium: 40765,
};

export const sampleWithFullData: IJobInfo = {
  id: 84523,
  odataEtag: 'Wisconsin payment',
  externalId: 'panel',
  jobNumber: 'knowledge RAM',
  description: 'parse',
  description2: 'Security Harbors Accounts',
  billToCustomerNo: 'Computers',
  billToName: 'orchid navigating impactful',
  billToCountryRegionCode: 'morph',
  sellToContact: 'Buckinghamshire Shoes',
  yourReference: 'Carolina haptic',
  contractNo: 'Networked olive',
  statusProposal: 'Configuration',
  startingDate: dayjs('2024-01-18'),
  endingDate: dayjs('2024-01-17'),
  durationInMonths: 93152,
  projectManager: 'Metal Walk parse',
  projectManagerMail: 'Venezuela invoice',
  eunRole: 'rich web-readiness Auto',
  visaNumber: 'Unbranded invoice',
  jobType: 'Producer',
  jobTypeText: 'copying',
  jobProgram: 'algorithm',
  programManager: 'Plains transmit leverage',
  budgetEUN: 25963,
  fundingEUN: 16743,
  fundingRate: 34245,
  budgetConsortium: 51172,
  fundingConsortium: 3284,
  overheadPerc: 38990,
};

export const sampleWithNewData: NewJobInfo = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
