export interface IReferenceTableSettings {
  id: number;
  refTable?: string | null;
  columns?: string | null;
  path?: string | null;
  isActive?: boolean | null;
}

export type NewReferenceTableSettings = Omit<IReferenceTableSettings, 'id'> & { id: null };
