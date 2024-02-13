import { IReferenceTableSettings, NewReferenceTableSettings } from './reference-table-settings.model';

export const sampleWithRequiredData: IReferenceTableSettings = {
  id: 74130,
};

export const sampleWithPartialData: IReferenceTableSettings = {
  id: 73637,
  refTable: 'reintermediate Coordinator',
  columns: 'salmon',
  columnsOfTemplate: 'Practical',
  actions: 'Dynamic multi-byte',
  path: 'target',
  isActive: true,
};

export const sampleWithFullData: IReferenceTableSettings = {
  id: 62510,
  refTable: 'maroon',
  displayName: 'circuit',
  columns: 'sensor Computer',
  columnsOfTemplate: 'Human Rapid',
  actions: 'Trail compress Auto',
  path: 'Rubber',
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
