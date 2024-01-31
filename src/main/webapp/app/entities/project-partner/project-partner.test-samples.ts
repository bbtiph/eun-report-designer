import dayjs from 'dayjs/esm';

import { IProjectPartner, NewProjectPartner } from './project-partner.model';

export const sampleWithRequiredData: IProjectPartner = {
  id: 60792,
};

export const sampleWithPartialData: IProjectPartner = {
  id: 41014,
  odataEtag: 'Music Dynamic',
  vendorName: 'sticky calculating',
  countryName: 'circuit Louisiana',
  partnerAmount: 96154,
  isActive: true,
  createdBy: 'program Technician',
  lastModifiedDate: dayjs('2024-01-31'),
};

export const sampleWithFullData: IProjectPartner = {
  id: 3239,
  odataEtag: 'reboot',
  no: 23557,
  jobNo: 'auxiliary',
  vendorCode: 'services THX',
  vendorName: 'Enterprise-wide cyan',
  countryCode: 'MD',
  countryName: 'Representative',
  partnerAmount: 99172,
  isActive: false,
  createdBy: 'withdrawal Cheese',
  lastModifiedBy: 'azure Technician microchip',
  createdDate: dayjs('2024-01-30'),
  lastModifiedDate: dayjs('2024-01-30'),
};

export const sampleWithNewData: NewProjectPartner = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
