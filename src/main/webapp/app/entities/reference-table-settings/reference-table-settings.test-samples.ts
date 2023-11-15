import { IReferenceTableSettings, NewReferenceTableSettings } from './reference-table-settings.model';

export const sampleWithRequiredData: IReferenceTableSettings = {
  id: 74130,
};

export const sampleWithPartialData: IReferenceTableSettings = {
  id: 58099,
  refTable: 'array payment',
  columns: 'backing Michigan',
  path: 'Expressway',
  isActive: true,
  file: '../fake-data/blob/hipster.png',
  fileContentType: 'unknown',
};

export const sampleWithFullData: IReferenceTableSettings = {
  id: 66265,
  refTable: 'multi-byte Fantastic Quality',
  displayName: 'maroon',
  columns: 'circuit',
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
