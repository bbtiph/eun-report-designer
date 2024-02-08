import { IReferenceTableSettings, NewReferenceTableSettings } from './reference-table-settings.model';

export const sampleWithRequiredData: IReferenceTableSettings = {
  id: 74130,
};

export const sampleWithPartialData: IReferenceTableSettings = {
  id: 37726,
  refTable: 'disintermediate Namibia backing',
  columns: 'synergies Expressway',
  columnsOfTemplate: 'payment Colorado target',
  path: 'Savings utilize circuit',
  isActive: true,
  file: '../fake-data/blob/hipster.png',
  fileContentType: 'unknown',
};

export const sampleWithFullData: IReferenceTableSettings = {
  id: 73333,
  refTable: 'Corporate online',
  displayName: 'RSS cultivate SAS',
  columns: 'Account',
  columnsOfTemplate: 'Cheese',
  path: 'open-source Pants',
  isActive: true,
  file: '../fake-data/blob/hipster.png',
  fileContentType: 'unknown',
};

export const sampleWithNewData: NewReferenceTableSettings = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
