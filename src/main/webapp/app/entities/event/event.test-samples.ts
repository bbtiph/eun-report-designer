import dayjs from 'dayjs/esm';

import { IEvent, NewEvent } from './event.model';

export const sampleWithRequiredData: IEvent = {
  id: 63022,
};

export const sampleWithPartialData: IEvent = {
  id: 11383,
  title: 'one-to-one wireless',
  startDate: dayjs('2023-04-27'),
  url: 'http://shanna.net',
  engagementRate: 60277,
  completionRate: 96946,
};

export const sampleWithFullData: IEvent = {
  id: 16062,
  type: 'Expanded Barbuda',
  location: 'bus',
  title: 'withdrawal hacking auxiliary',
  description: 'Iraqi',
  startDate: dayjs('2023-04-28'),
  endDate: dayjs('2023-04-28'),
  url: 'http://issac.net',
  eunContact: 'connect indexing alarm',
  courseModules: 'Optimization',
  courseDuration: 8938,
  courseType: 'JBOD Future-proofed',
  modules: 83911,
  status: 'indexing Strategist',
  engagementRate: 34866,
  completionRate: 3033,
  name: 'Tuna Intelligent Republic)',
};

export const sampleWithNewData: NewEvent = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
