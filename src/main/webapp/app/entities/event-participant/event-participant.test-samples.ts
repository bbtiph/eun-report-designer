import { IEventParticipant, NewEventParticipant } from './event-participant.model';

export const sampleWithRequiredData: IEventParticipant = {
  id: 31773,
};

export const sampleWithPartialData: IEventParticipant = {
  id: 12721,
  type: 'deposit',
};

export const sampleWithFullData: IEventParticipant = {
  id: 23081,
  type: 'convergence maroon',
};

export const sampleWithNewData: NewEventParticipant = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
