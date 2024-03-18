import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IMOEParticipationReferences, NewMOEParticipationReferences } from '../moe-participation-references.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMOEParticipationReferences for edit and NewMOEParticipationReferencesFormGroupInput for create.
 */
type MOEParticipationReferencesFormGroupInput = IMOEParticipationReferences | PartialWithRequiredKeyOf<NewMOEParticipationReferences>;

type MOEParticipationReferencesFormDefaults = Pick<NewMOEParticipationReferences, 'id' | 'isActive' | 'countries'>;

type MOEParticipationReferencesFormGroupContent = {
  id: FormControl<IMOEParticipationReferences['id'] | NewMOEParticipationReferences['id']>;
  name: FormControl<IMOEParticipationReferences['name']>;
  type: FormControl<IMOEParticipationReferences['type']>;
  startDate: FormControl<IMOEParticipationReferences['startDate']>;
  endDate: FormControl<IMOEParticipationReferences['endDate']>;
  isActive: FormControl<IMOEParticipationReferences['isActive']>;
  createdBy: FormControl<IMOEParticipationReferences['createdBy']>;
  lastModifiedBy: FormControl<IMOEParticipationReferences['lastModifiedBy']>;
  createdDate: FormControl<IMOEParticipationReferences['createdDate']>;
  lastModifiedDate: FormControl<IMOEParticipationReferences['lastModifiedDate']>;
  countries: FormControl<IMOEParticipationReferences['countries']>;
};

export type MOEParticipationReferencesFormGroup = FormGroup<MOEParticipationReferencesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MOEParticipationReferencesFormService {
  createMOEParticipationReferencesFormGroup(
    mOEParticipationReferences: MOEParticipationReferencesFormGroupInput = { id: null }
  ): MOEParticipationReferencesFormGroup {
    const mOEParticipationReferencesRawValue = {
      ...this.getFormDefaults(),
      ...mOEParticipationReferences,
    };
    return new FormGroup<MOEParticipationReferencesFormGroupContent>({
      id: new FormControl(
        { value: mOEParticipationReferencesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(mOEParticipationReferencesRawValue.name),
      type: new FormControl(mOEParticipationReferencesRawValue.type),
      startDate: new FormControl(mOEParticipationReferencesRawValue.startDate),
      endDate: new FormControl(mOEParticipationReferencesRawValue.endDate),
      isActive: new FormControl(mOEParticipationReferencesRawValue.isActive),
      createdBy: new FormControl(mOEParticipationReferencesRawValue.createdBy),
      lastModifiedBy: new FormControl(mOEParticipationReferencesRawValue.lastModifiedBy),
      createdDate: new FormControl(mOEParticipationReferencesRawValue.createdDate),
      lastModifiedDate: new FormControl(mOEParticipationReferencesRawValue.lastModifiedDate),
      countries: new FormControl(mOEParticipationReferencesRawValue.countries ?? []),
    });
  }

  getMOEParticipationReferences(form: MOEParticipationReferencesFormGroup): IMOEParticipationReferences | NewMOEParticipationReferences {
    return form.getRawValue() as IMOEParticipationReferences | NewMOEParticipationReferences;
  }

  resetForm(form: MOEParticipationReferencesFormGroup, mOEParticipationReferences: MOEParticipationReferencesFormGroupInput): void {
    const mOEParticipationReferencesRawValue = { ...this.getFormDefaults(), ...mOEParticipationReferences };
    form.reset(
      {
        ...mOEParticipationReferencesRawValue,
        id: { value: mOEParticipationReferencesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): MOEParticipationReferencesFormDefaults {
    return {
      id: null,
      isActive: false,
      countries: [],
    };
  }
}
