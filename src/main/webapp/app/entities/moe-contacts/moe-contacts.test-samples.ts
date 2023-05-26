import { IMoeContacts, NewMoeContacts } from './moe-contacts.model';

export const sampleWithRequiredData: IMoeContacts = {
  id: 21282,
};

export const sampleWithPartialData: IMoeContacts = {
  id: 38515,
  postalAddress: 'green cutting-edge Colombian',
  invoicingAddress: 'Intuitive',
  shippingAddress: 'Virginia',
  contactEunFirstName: 'revolutionize',
};

export const sampleWithFullData: IMoeContacts = {
  id: 14609,
  countryCode: 'FM',
  countryName: 'scalable',
  priorityNumber: 96720,
  ministryName: 'neural Clothing',
  ministryEnglishName: 'Games Functionality COM',
  postalAddress: 'Buckinghamshire calculating',
  invoicingAddress: 'Wooden',
  shippingAddress: 'Infrastructure Investor Route',
  contactEunFirstName: 'mobile',
  contactEunLastName: 'Stream black',
};

export const sampleWithNewData: NewMoeContacts = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
