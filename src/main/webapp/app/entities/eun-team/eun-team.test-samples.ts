import { IEunTeam, NewEunTeam } from './eun-team.model';

export const sampleWithRequiredData: IEunTeam = {
  id: 83238,
};

export const sampleWithPartialData: IEunTeam = {
  id: 81172,
};

export const sampleWithFullData: IEunTeam = {
  id: 94444,
  name: 'Home maroon',
  description: 'orchid redundant',
};

export const sampleWithNewData: NewEunTeam = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
