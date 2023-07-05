import { ICountries, NewCountries } from './countries.model';

export const sampleWithRequiredData: ICountries = {
  id: 74119,
};

export const sampleWithPartialData: ICountries = {
  id: 34509,
  countryName: 'Pizza',
};

export const sampleWithFullData: ICountries = {
  id: 1492,
  countryName: 'Steel Identity withdrawal',
  countryCode: 'SM',
};

export const sampleWithNewData: NewCountries = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
