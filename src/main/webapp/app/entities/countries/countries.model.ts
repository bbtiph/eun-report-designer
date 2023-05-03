export interface ICountries {
  id: number;
  countryName?: string | null;
}

export type NewCountries = Omit<ICountries, 'id'> & { id: null };
