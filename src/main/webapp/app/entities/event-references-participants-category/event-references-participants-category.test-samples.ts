import {
  IEventReferencesParticipantsCategory,
  NewEventReferencesParticipantsCategory,
} from './event-references-participants-category.model';

export const sampleWithRequiredData: IEventReferencesParticipantsCategory = {
  id: 44399,
};

export const sampleWithPartialData: IEventReferencesParticipantsCategory = {
  id: 96083,
  category: 'Investor',
  participantsCount: 21913,
};

export const sampleWithFullData: IEventReferencesParticipantsCategory = {
  id: 24084,
  category: 'plum copy connect',
  participantsCount: 226,
};

export const sampleWithNewData: NewEventReferencesParticipantsCategory = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
