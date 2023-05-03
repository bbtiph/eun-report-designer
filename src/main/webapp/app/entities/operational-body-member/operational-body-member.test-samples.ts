import dayjs from 'dayjs/esm';

import { IOperationalBodyMember, NewOperationalBodyMember } from './operational-body-member.model';

export const sampleWithRequiredData: IOperationalBodyMember = {
  id: 19095,
};

export const sampleWithPartialData: IOperationalBodyMember = {
  id: 19733,
  endDate: dayjs('2023-04-28'),
  eunContactFirstname: 'plug-and-play Toys Buckinghamshire',
};

export const sampleWithFullData: IOperationalBodyMember = {
  id: 22596,
  personId: 89192,
  position: 'Balanced open-source optical',
  startDate: dayjs('2023-04-28'),
  endDate: dayjs('2023-04-28'),
  department: 'Loan',
  eunContactFirstname: 'transform strategic',
  eunContactLastname: 'Brand',
  cooperationField: 'orchestration',
  status: 'Global SDD',
};

export const sampleWithNewData: NewOperationalBodyMember = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
