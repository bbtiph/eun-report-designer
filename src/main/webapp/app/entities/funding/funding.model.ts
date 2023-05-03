import { IProject } from 'app/entities/project/project.model';

export interface IFunding {
  id: number;
  name?: string | null;
  type?: string | null;
  parentId?: number | null;
  description?: string | null;
  project?: Pick<IProject, 'id'> | null;
}

export type NewFunding = Omit<IFunding, 'id'> & { id: null };
