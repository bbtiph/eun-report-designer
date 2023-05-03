import { IPersonInProject, NewPersonInProject } from './person-in-project.model';

export const sampleWithRequiredData: IPersonInProject = {
  id: 91162,
};

export const sampleWithPartialData: IPersonInProject = {
  id: 98696,
};

export const sampleWithFullData: IPersonInProject = {
  id: 39383,
  roleInProject: 'payment',
};

export const sampleWithNewData: NewPersonInProject = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
