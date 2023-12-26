import { IEventReferences, NewEventReferences } from './event-references.model';

export const sampleWithRequiredData: IEventReferences = {
  id: 23485,
};

export const sampleWithPartialData: IEventReferences = {
  id: 81021,
  isActive: true,
};

export const sampleWithFullData: IEventReferences = {
  id: 65982,
  name: 'Avon Designer moderator',
  type: 'Agent Future Rubber',
  isActive: false,
};

export const sampleWithNewData: NewEventReferences = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
