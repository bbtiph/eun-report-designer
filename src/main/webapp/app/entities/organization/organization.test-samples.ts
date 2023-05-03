import { OrgStatus } from 'app/entities/enumerations/org-status.model';

import { IOrganization, NewOrganization } from './organization.model';

export const sampleWithRequiredData: IOrganization = {
  id: 12784,
  status: OrgStatus['BLOCKED'],
  officialName: 'Chips',
};

export const sampleWithPartialData: IOrganization = {
  id: 37338,
  eunDbId: 39738,
  status: OrgStatus['BLOCKED'],
  officialName: 'payment Chips payment',
  description: 'TCP Enhanced payment',
  address: 'salmon',
  latitude: 28603,
  minAge: 42614,
  area: 41796,
  hardwareUsed: true,
  ictUsed: true,
  telephone: '390-266-4663',
  fax: 'Practical Frozen Phased',
  email: 'Jace15@hotmail.com',
  organisationNumber: 'user',
};

export const sampleWithFullData: IOrganization = {
  id: 90931,
  eunDbId: 89618,
  status: OrgStatus['INACTIVE'],
  officialName: 'programming',
  description: 'turn-key Accountability',
  type: 'national bus',
  address: 'Azerbaijan Practical plug-and-play',
  latitude: 69068,
  longitude: 29471,
  maxAge: 15307,
  minAge: 99131,
  area: 65966,
  specialization: 'Bacon South deliverables',
  numberOfStudents: 'Specialist Plastic',
  hardwareUsed: true,
  ictUsed: true,
  website: 'Salad',
  image: '../fake-data/blob/hipster.png',
  imageContentType: 'unknown',
  telephone: '503-205-9529 x9431',
  fax: 'Future website Granite',
  email: 'Calista_Schamberger@gmail.com',
  organisationNumber: 'Brand Rue',
};

export const sampleWithNewData: NewOrganization = {
  status: OrgStatus['MERGED'],
  officialName: 'Fantastic',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
