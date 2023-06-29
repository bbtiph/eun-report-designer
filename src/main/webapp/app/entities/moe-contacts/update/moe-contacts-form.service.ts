import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IMoeContacts, NewMoeContacts } from '../moe-contacts.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMoeContacts for edit and NewMoeContactsFormGroupInput for create.
 */
type MoeContactsFormGroupInput = IMoeContacts | PartialWithRequiredKeyOf<NewMoeContacts>;

type MoeContactsFormDefaults = Pick<NewMoeContacts, 'id' | 'isActive'>;

type MoeContactsFormGroupContent = {
  id: FormControl<IMoeContacts['id'] | NewMoeContacts['id']>;
  countryCode: FormControl<IMoeContacts['countryCode']>;
  countryName: FormControl<IMoeContacts['countryName']>;
  isActive: FormControl<IMoeContacts['isActive']>;
  ministryName: FormControl<IMoeContacts['ministryName']>;
  ministryEnglishName: FormControl<IMoeContacts['ministryEnglishName']>;
  postalAddress: FormControl<IMoeContacts['postalAddress']>;
  invoicingAddress: FormControl<IMoeContacts['invoicingAddress']>;
  shippingAddress: FormControl<IMoeContacts['shippingAddress']>;
  contactEunFirstName: FormControl<IMoeContacts['contactEunFirstName']>;
  contactEunLastName: FormControl<IMoeContacts['contactEunLastName']>;
};

export type MoeContactsFormGroup = FormGroup<MoeContactsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MoeContactsFormService {
  createMoeContactsFormGroup(moeContacts: MoeContactsFormGroupInput = { id: null }): MoeContactsFormGroup {
    const moeContactsRawValue = {
      ...this.getFormDefaults(),
      ...moeContacts,
    };
    return new FormGroup<MoeContactsFormGroupContent>({
      id: new FormControl(
        { value: moeContactsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      countryCode: new FormControl(moeContactsRawValue.countryCode),
      countryName: new FormControl(moeContactsRawValue.countryName),
      isActive: new FormControl(moeContactsRawValue.isActive),
      ministryName: new FormControl(moeContactsRawValue.ministryName),
      ministryEnglishName: new FormControl(moeContactsRawValue.ministryEnglishName),
      postalAddress: new FormControl(moeContactsRawValue.postalAddress),
      invoicingAddress: new FormControl(moeContactsRawValue.invoicingAddress),
      shippingAddress: new FormControl(moeContactsRawValue.shippingAddress),
      contactEunFirstName: new FormControl(moeContactsRawValue.contactEunFirstName),
      contactEunLastName: new FormControl(moeContactsRawValue.contactEunLastName),
    });
  }

  getMoeContacts(form: MoeContactsFormGroup): IMoeContacts | NewMoeContacts {
    return form.getRawValue() as IMoeContacts | NewMoeContacts;
  }

  resetForm(form: MoeContactsFormGroup, moeContacts: MoeContactsFormGroupInput): void {
    const moeContactsRawValue = { ...this.getFormDefaults(), ...moeContacts };
    form.reset(
      {
        ...moeContactsRawValue,
        id: { value: moeContactsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): MoeContactsFormDefaults {
    return {
      id: null,
      isActive: false,
    };
  }
}
