export interface IMoeContacts {
  id: number;
  countryCode?: string | null;
  countryName?: string | null;
  isActive?: boolean | null;
  ministryName?: string | null;
  ministryEnglishName?: string | null;
  postalAddress?: string | null;
  invoicingAddress?: string | null;
  shippingAddress?: string | null;
  contactEunFirstName?: string | null;
  contactEunLastName?: string | null;
}

export type NewMoeContacts = Omit<IMoeContacts, 'id'> & { id: null };
