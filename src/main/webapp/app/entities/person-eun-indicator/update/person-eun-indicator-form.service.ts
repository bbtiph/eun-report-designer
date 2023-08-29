import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPersonEunIndicator, NewPersonEunIndicator } from '../person-eun-indicator.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPersonEunIndicator for edit and NewPersonEunIndicatorFormGroupInput for create.
 */
type PersonEunIndicatorFormGroupInput = IPersonEunIndicator | PartialWithRequiredKeyOf<NewPersonEunIndicator>;

type PersonEunIndicatorFormDefaults = Pick<NewPersonEunIndicator, 'id'>;

type PersonEunIndicatorFormGroupContent = {
  id: FormControl<IPersonEunIndicator['id'] | NewPersonEunIndicator['id']>;
  period: FormControl<IPersonEunIndicator['period']>;
  nCount: FormControl<IPersonEunIndicator['nCount']>;
  countryId: FormControl<IPersonEunIndicator['countryId']>;
  projectId: FormControl<IPersonEunIndicator['projectId']>;
  projectUrl: FormControl<IPersonEunIndicator['projectUrl']>;
  countryName: FormControl<IPersonEunIndicator['countryName']>;
  projectName: FormControl<IPersonEunIndicator['projectName']>;
  reportsProjectId: FormControl<IPersonEunIndicator['reportsProjectId']>;
  createdBy: FormControl<IPersonEunIndicator['createdBy']>;
  lastModifiedBy: FormControl<IPersonEunIndicator['lastModifiedBy']>;
  createdDate: FormControl<IPersonEunIndicator['createdDate']>;
  lastModifiedDate: FormControl<IPersonEunIndicator['lastModifiedDate']>;
};

export type PersonEunIndicatorFormGroup = FormGroup<PersonEunIndicatorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PersonEunIndicatorFormService {
  createPersonEunIndicatorFormGroup(personEunIndicator: PersonEunIndicatorFormGroupInput = { id: null }): PersonEunIndicatorFormGroup {
    const personEunIndicatorRawValue = {
      ...this.getFormDefaults(),
      ...personEunIndicator,
    };
    return new FormGroup<PersonEunIndicatorFormGroupContent>({
      id: new FormControl(
        { value: personEunIndicatorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      period: new FormControl(personEunIndicatorRawValue.period),
      nCount: new FormControl(personEunIndicatorRawValue.nCount),
      countryId: new FormControl(personEunIndicatorRawValue.countryId),
      projectId: new FormControl(personEunIndicatorRawValue.projectId),
      projectUrl: new FormControl(personEunIndicatorRawValue.projectUrl),
      countryName: new FormControl(personEunIndicatorRawValue.countryName),
      projectName: new FormControl(personEunIndicatorRawValue.projectName),
      reportsProjectId: new FormControl(personEunIndicatorRawValue.reportsProjectId),
      createdBy: new FormControl(personEunIndicatorRawValue.createdBy),
      lastModifiedBy: new FormControl(personEunIndicatorRawValue.lastModifiedBy),
      createdDate: new FormControl(personEunIndicatorRawValue.createdDate),
      lastModifiedDate: new FormControl(personEunIndicatorRawValue.lastModifiedDate),
    });
  }

  getPersonEunIndicator(form: PersonEunIndicatorFormGroup): IPersonEunIndicator | NewPersonEunIndicator {
    return form.getRawValue() as IPersonEunIndicator | NewPersonEunIndicator;
  }

  resetForm(form: PersonEunIndicatorFormGroup, personEunIndicator: PersonEunIndicatorFormGroupInput): void {
    const personEunIndicatorRawValue = { ...this.getFormDefaults(), ...personEunIndicator };
    form.reset(
      {
        ...personEunIndicatorRawValue,
        id: { value: personEunIndicatorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PersonEunIndicatorFormDefaults {
    return {
      id: null,
    };
  }
}
