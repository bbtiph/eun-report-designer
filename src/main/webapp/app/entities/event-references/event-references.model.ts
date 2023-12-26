import { ICountries } from 'app/entities/countries/countries.model';

export interface IEventReferences {
  id: number;
  name?: string | null;
  type?: string | null;
  countries?: Pick<ICountries, 'id'>[] | null;
}

export type NewEventReferences = Omit<IEventReferences, 'id'> & { id: null };
