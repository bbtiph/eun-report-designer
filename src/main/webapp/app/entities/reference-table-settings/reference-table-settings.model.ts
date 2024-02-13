export interface IReferenceTableSettings {
  id: number;
  refTable?: string | null;
  displayName?: string | null;
  columns?: string | null;
  columnsOfTemplate?: string | null;
  actions?: string | null;
  path?: string | null;
  isActive?: boolean | null;
  file?: string | null;
  fileContentType?: string | null;
}

export type NewReferenceTableSettings = Omit<IReferenceTableSettings, 'id'> & { id: null };
