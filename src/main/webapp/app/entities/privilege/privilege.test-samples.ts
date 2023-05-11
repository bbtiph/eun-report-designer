import { IPrivilege, NewPrivilege } from './privilege.model';

export const sampleWithRequiredData: IPrivilege = {
  id: 13326,
};

export const sampleWithPartialData: IPrivilege = {
  id: 40041,
  name: 'Car',
};

export const sampleWithFullData: IPrivilege = {
  id: 86087,
  name: 'Shoes Nigeria',
};

export const sampleWithNewData: NewPrivilege = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
