import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IMinistry, NewMinistry } from '../ministry.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMinistry for edit and NewMinistryFormGroupInput for create.
 */
type MinistryFormGroupInput = IMinistry | PartialWithRequiredKeyOf<NewMinistry>;

type MinistryFormDefaults = Pick<NewMinistry, 'id'>;

type MinistryFormGroupContent = {
  id: FormControl<IMinistry['id'] | NewMinistry['id']>;
  languages: FormControl<IMinistry['languages']>;
  formalName: FormControl<IMinistry['formalName']>;
  englishName: FormControl<IMinistry['englishName']>;
  acronym: FormControl<IMinistry['acronym']>;
  description: FormControl<IMinistry['description']>;
  website: FormControl<IMinistry['website']>;
  steercomMemberName: FormControl<IMinistry['steercomMemberName']>;
  steercomMemberEmail: FormControl<IMinistry['steercomMemberEmail']>;
  postalAddressRegion: FormControl<IMinistry['postalAddressRegion']>;
  postalAddressPostalCode: FormControl<IMinistry['postalAddressPostalCode']>;
  postalAddressCity: FormControl<IMinistry['postalAddressCity']>;
  postalAddressStreet: FormControl<IMinistry['postalAddressStreet']>;
  status: FormControl<IMinistry['status']>;
  eunContactFirstname: FormControl<IMinistry['eunContactFirstname']>;
  eunContactLastname: FormControl<IMinistry['eunContactLastname']>;
  eunContactEmail: FormControl<IMinistry['eunContactEmail']>;
  invoicingAddress: FormControl<IMinistry['invoicingAddress']>;
  financialCorrespondingEmail: FormControl<IMinistry['financialCorrespondingEmail']>;
  country: FormControl<IMinistry['country']>;
};

export type MinistryFormGroup = FormGroup<MinistryFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MinistryFormService {
  createMinistryFormGroup(ministry: MinistryFormGroupInput = { id: null }): MinistryFormGroup {
    const ministryRawValue = {
      ...this.getFormDefaults(),
      ...ministry,
    };
    return new FormGroup<MinistryFormGroupContent>({
      id: new FormControl(
        { value: ministryRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      languages: new FormControl(ministryRawValue.languages),
      formalName: new FormControl(ministryRawValue.formalName),
      englishName: new FormControl(ministryRawValue.englishName),
      acronym: new FormControl(ministryRawValue.acronym),
      description: new FormControl(ministryRawValue.description),
      website: new FormControl(ministryRawValue.website),
      steercomMemberName: new FormControl(ministryRawValue.steercomMemberName),
      steercomMemberEmail: new FormControl(ministryRawValue.steercomMemberEmail),
      postalAddressRegion: new FormControl(ministryRawValue.postalAddressRegion),
      postalAddressPostalCode: new FormControl(ministryRawValue.postalAddressPostalCode),
      postalAddressCity: new FormControl(ministryRawValue.postalAddressCity),
      postalAddressStreet: new FormControl(ministryRawValue.postalAddressStreet),
      status: new FormControl(ministryRawValue.status),
      eunContactFirstname: new FormControl(ministryRawValue.eunContactFirstname),
      eunContactLastname: new FormControl(ministryRawValue.eunContactLastname),
      eunContactEmail: new FormControl(ministryRawValue.eunContactEmail),
      invoicingAddress: new FormControl(ministryRawValue.invoicingAddress),
      financialCorrespondingEmail: new FormControl(ministryRawValue.financialCorrespondingEmail),
      country: new FormControl(ministryRawValue.country),
    });
  }

  getMinistry(form: MinistryFormGroup): IMinistry | NewMinistry {
    return form.getRawValue() as IMinistry | NewMinistry;
  }

  resetForm(form: MinistryFormGroup, ministry: MinistryFormGroupInput): void {
    const ministryRawValue = { ...this.getFormDefaults(), ...ministry };
    form.reset(
      {
        ...ministryRawValue,
        id: { value: ministryRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): MinistryFormDefaults {
    return {
      id: null,
    };
  }
}
