import { IReferenceTableSettings, NewReferenceTableSettings } from './reference-table-settings.model';

export const sampleWithRequiredData: IReferenceTableSettings = {
  id: 74130,
};

export const sampleWithPartialData: IReferenceTableSettings = {
  id: 78659,
  refTable: 'scale reintermediate',
  path: 'generate salmon Music',
  isActive: false,
  file: '../fake-data/blob/hipster.png',
  fileContentType: 'unknown',
};

export const sampleWithFullData: IReferenceTableSettings = {
  id: 66163,
  refTable: 'payment Colorado target',
  columns: 'Savings utilize circuit',
  path: 'sensor Computer',
  isActive: false,
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
