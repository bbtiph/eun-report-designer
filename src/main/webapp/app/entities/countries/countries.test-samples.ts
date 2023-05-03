import { ICountries, NewCountries } from './countries.model';

export const sampleWithRequiredData: ICountries = {
  id: 74119,
  countryName: 'Berkshire Pizza Outdoors',
};

export const sampleWithPartialData: ICountries = {
  id: 269,
  countryName: 'Identity withdrawal parsing',
};

export const sampleWithFullData: ICountries = {
  id: 13219,
  countryName: 'Zealand haptic',
};

export const sampleWithNewData: NewCountries = {
  countryName: 'Handmade e-business grey',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
