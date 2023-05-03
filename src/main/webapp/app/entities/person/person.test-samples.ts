import dayjs from 'dayjs/esm';

import { GdprStatus } from 'app/entities/enumerations/gdpr-status.model';

import { IPerson, NewPerson } from './person.model';

export const sampleWithRequiredData: IPerson = {
  id: 31094,
};

export const sampleWithPartialData: IPerson = {
  id: 36555,
  eunDbId: 41180,
  firstname: 'withdrawal concept',
  salutation: 7079,
  extraContractEmail: 'violet capacitor Sleek',
  languageMotherTongue: 'Designer Pants Universal',
  socialNetworkContacts: 'white',
};

export const sampleWithFullData: IPerson = {
  id: 64719,
  eunDbId: 76355,
  firstname: 'success Naira deposit',
  lastname: 'modular bricks-and-clicks Rue',
  salutation: 88454,
  mainContractEmail: 'eyeballs Assistant',
  extraContractEmail: 'Grocery Handcrafted distributed',
  languageMotherTongue: 'Bike copy Generic',
  languageOther: 'XML',
  description: 'Sleek',
  website: 'Philippines',
  mobile: 'projection SSL Integration',
  phone: '567-938-4642 x0098',
  socialNetworkContacts: 'Executive',
  status: 'Auto Associate Sausages',
  gdprStatus: GdprStatus['SYS_FLAGGED_INACTIVE'],
  lastLoginDate: dayjs('2023-04-27'),
};

export const sampleWithNewData: NewPerson = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
