import { ICountries, NewCountries } from './countries.model';

export const sampleWithRequiredData: ICountries = {
  id: 74119,
};

export const sampleWithPartialData: ICountries = {
  id: 49448,
  countryName: 'circuit auxiliary',
};

export const sampleWithFullData: ICountries = {
  id: 14554,
  countryName: 'Platinum',
};

export const sampleWithNewData: NewCountries = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
