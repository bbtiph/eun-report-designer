import { IEventReferences, NewEventReferences } from './event-references.model';

export const sampleWithRequiredData: IEventReferences = {
  id: 23485,
};

export const sampleWithPartialData: IEventReferences = {
  id: 76498,
};

export const sampleWithFullData: IEventReferences = {
  id: 81021,
  name: 'invoice Wooden Seychelles',
  type: 'Sports SMS',
};

export const sampleWithNewData: NewEventReferences = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
