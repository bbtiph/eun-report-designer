export interface IPersonInProject {
  id: number;
  roleInProject?: string | null;
}

export type NewPersonInProject = Omit<IPersonInProject, 'id'> & { id: null };
