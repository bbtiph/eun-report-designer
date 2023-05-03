import { IEventInOrganization, NewEventInOrganization } from './event-in-organization.model';

export const sampleWithRequiredData: IEventInOrganization = {
  id: 49860,
};

export const sampleWithPartialData: IEventInOrganization = {
  id: 99997,
};

export const sampleWithFullData: IEventInOrganization = {
  id: 34828,
};

export const sampleWithNewData: NewEventInOrganization = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
