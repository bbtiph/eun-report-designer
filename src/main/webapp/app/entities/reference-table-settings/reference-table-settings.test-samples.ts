import { IReferenceTableSettings, NewReferenceTableSettings } from './reference-table-settings.model';

export const sampleWithRequiredData: IReferenceTableSettings = {
  id: 74130,
};

export const sampleWithPartialData: IReferenceTableSettings = {
  id: 60665,
  refTable: 'Maine disintermediate Namibia',
  path: 'Electronics synergies Expressway',
  isActive: true,
};

export const sampleWithFullData: IReferenceTableSettings = {
  id: 66265,
  refTable: 'multi-byte Fantastic Quality',
  columns: 'maroon',
  path: 'circuit',
  isActive: true,
};

export const sampleWithNewData: NewReferenceTableSettings = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
