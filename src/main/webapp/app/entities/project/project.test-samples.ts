import dayjs from 'dayjs/esm';

import { ProjectStatus } from 'app/entities/enumerations/project-status.model';

import { IProject, NewProject } from './project.model';

export const sampleWithRequiredData: IProject = {
  id: 55962,
  status: ProjectStatus['CLOSED'],
  name: 'Pennsylvania Innovative Investor',
  description: 'Slovenia',
};

export const sampleWithPartialData: IProject = {
  id: 47649,
  status: ProjectStatus['REVIEWED'],
  supportedCountryIds: 'Niger (Slovak mindshare',
  supportedLanguageIds: 'Bike Timor-Leste',
  name: 'Computers deposit productize',
  acronym: 'state USB capability',
  period: 'Canyon syndicate',
  description: 'fresh-thinking Granite EXE',
  contactEmail: 'leading Developer',
  contactName: 'open-source Auto',
  fundingValue: 39852,
  percentageOfFunding: 18314,
  percentageOfIndirectCosts: 81357,
  programme: 'Operative',
  euDg: 'Lead Communications',
  summary: 'lime',
  mainTasks: 'IB',
  expectedOutcomes: 'Wooden SCSI',
  euCallReference: 'Cheese',
  euProjectReference: 'Direct',
  euCallDeadline: 'services',
  projectManager: 'Pizza',
  sysCreatTimestamp: dayjs('2023-04-27T01:25'),
  sysCreatIpAddress: 'ADP product International',
  sysModifIpAddress: 'benchmark Hat',
};

export const sampleWithFullData: IProject = {
  id: 4054,
  status: ProjectStatus['REVIEWED'],
  supportedCountryIds: 'Table Facilitator',
  supportedLanguageIds: 'Developer Clothing',
  name: 'Fish',
  acronym: 'Officer',
  period: 'Cambridgeshire',
  description: 'navigating 1080p',
  contactEmail: 'generating deposit',
  contactName: 'CSS withdrawal red',
  totalBudget: 74451,
  totalFunding: 47176,
  totalBudgetForEun: 46778,
  totalFundingForEun: 95073,
  fundingValue: 39906,
  percentageOfFunding: 94297,
  percentageOfIndirectCosts: 26866,
  percentageOfFundingForEun: 3098,
  percentageOfIndirectCostsForEun: 39497,
  fundingExtraComment: 'wireless Euro North',
  programme: 'Security',
  euDg: 'hacking Account',
  roleOfEun: 'transmit program',
  summary: 'multi-byte',
  mainTasks: 'Soap',
  expectedOutcomes: 'Australian Berkshire',
  euCallReference: 'wireless',
  euProjectReference: 'transmit',
  euCallDeadline: 'digital Granite harness',
  projectManager: 'User-centric',
  referenceNumberOfProject: 54375,
  eunTeam: 'Plastic',
  sysCreatTimestamp: dayjs('2023-04-27T01:58'),
  sysCreatIpAddress: 'GB',
  sysModifTimestamp: dayjs('2023-04-26T15:03'),
  sysModifIpAddress: 'generating encoding Account',
};

export const sampleWithNewData: NewProject = {
  status: ProjectStatus['REQUESTED'],
  name: 'Beauty Generic Borders',
  description: 'multimedia hack Shirt',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
