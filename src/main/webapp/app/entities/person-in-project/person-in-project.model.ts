import { IPerson } from 'app/entities/person/person.model';
import { IProject } from 'app/entities/project/project.model';

export interface IPersonInProject {
  id: number;
  roleInProject?: string | null;
  person?: Pick<IPerson, 'id'> | null;
  project?: Pick<IProject, 'id'> | null;
}

export type NewPersonInProject = Omit<IPersonInProject, 'id'> & { id: null };
