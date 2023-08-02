import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IWorkingGroupReferences, NewWorkingGroupReferences } from '../working-group-references.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IWorkingGroupReferences for edit and NewWorkingGroupReferencesFormGroupInput for create.
 */
type WorkingGroupReferencesFormGroupInput = IWorkingGroupReferences | PartialWithRequiredKeyOf<NewWorkingGroupReferences>;

type WorkingGroupReferencesFormDefaults = Pick<NewWorkingGroupReferences, 'id' | 'isActive'>;

type WorkingGroupReferencesFormGroupContent = {
  id: FormControl<IWorkingGroupReferences['id'] | NewWorkingGroupReferences['id']>;
  countryCode: FormControl<IWorkingGroupReferences['countryCode']>;
  countryName: FormControl<IWorkingGroupReferences['countryName']>;
  countryRepresentativeFirstName: FormControl<IWorkingGroupReferences['countryRepresentativeFirstName']>;
  countryRepresentativeLastName: FormControl<IWorkingGroupReferences['countryRepresentativeLastName']>;
  countryRepresentativeMail: FormControl<IWorkingGroupReferences['countryRepresentativeMail']>;
  countryRepresentativePosition: FormControl<IWorkingGroupReferences['countryRepresentativePosition']>;
  countryRepresentativeStartDate: FormControl<IWorkingGroupReferences['countryRepresentativeStartDate']>;
  countryRepresentativeEndDate: FormControl<IWorkingGroupReferences['countryRepresentativeEndDate']>;
  countryRepresentativeMinistry: FormControl<IWorkingGroupReferences['countryRepresentativeMinistry']>;
  countryRepresentativeDepartment: FormControl<IWorkingGroupReferences['countryRepresentativeDepartment']>;
  contactEunFirstName: FormControl<IWorkingGroupReferences['contactEunFirstName']>;
  contactEunLastName: FormControl<IWorkingGroupReferences['contactEunLastName']>;
  type: FormControl<IWorkingGroupReferences['type']>;
  isActive: FormControl<IWorkingGroupReferences['isActive']>;
  createdBy: FormControl<IWorkingGroupReferences['createdBy']>;
  lastModifiedBy: FormControl<IWorkingGroupReferences['lastModifiedBy']>;
  createdDate: FormControl<IWorkingGroupReferences['createdDate']>;
  lastModifiedDate: FormControl<IWorkingGroupReferences['lastModifiedDate']>;
};

export type WorkingGroupReferencesFormGroup = FormGroup<WorkingGroupReferencesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class WorkingGroupReferencesFormService {
  createWorkingGroupReferencesFormGroup(
    workingGroupReferences: WorkingGroupReferencesFormGroupInput = { id: null }
  ): WorkingGroupReferencesFormGroup {
    const workingGroupReferencesRawValue = {
      ...this.getFormDefaults(),
      ...workingGroupReferences,
    };
    return new FormGroup<WorkingGroupReferencesFormGroupContent>({
      id: new FormControl(
        { value: workingGroupReferencesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      countryCode: new FormControl(workingGroupReferencesRawValue.countryCode),
      countryName: new FormControl(workingGroupReferencesRawValue.countryName),
      countryRepresentativeFirstName: new FormControl(workingGroupReferencesRawValue.countryRepresentativeFirstName),
      countryRepresentativeLastName: new FormControl(workingGroupReferencesRawValue.countryRepresentativeLastName),
      countryRepresentativeMail: new FormControl(workingGroupReferencesRawValue.countryRepresentativeMail),
      countryRepresentativePosition: new FormControl(workingGroupReferencesRawValue.countryRepresentativePosition),
      countryRepresentativeStartDate: new FormControl(workingGroupReferencesRawValue.countryRepresentativeStartDate),
      countryRepresentativeEndDate: new FormControl(workingGroupReferencesRawValue.countryRepresentativeEndDate),
      countryRepresentativeMinistry: new FormControl(workingGroupReferencesRawValue.countryRepresentativeMinistry),
      countryRepresentativeDepartment: new FormControl(workingGroupReferencesRawValue.countryRepresentativeDepartment),
      contactEunFirstName: new FormControl(workingGroupReferencesRawValue.contactEunFirstName),
      contactEunLastName: new FormControl(workingGroupReferencesRawValue.contactEunLastName),
      type: new FormControl(workingGroupReferencesRawValue.type),
      isActive: new FormControl(workingGroupReferencesRawValue.isActive),
      createdBy: new FormControl(workingGroupReferencesRawValue.createdBy),
      lastModifiedBy: new FormControl(workingGroupReferencesRawValue.lastModifiedBy),
      createdDate: new FormControl(workingGroupReferencesRawValue.createdDate),
      lastModifiedDate: new FormControl(workingGroupReferencesRawValue.lastModifiedDate),
    });
  }

  getWorkingGroupReferences(form: WorkingGroupReferencesFormGroup): IWorkingGroupReferences | NewWorkingGroupReferences {
    return form.getRawValue() as IWorkingGroupReferences | NewWorkingGroupReferences;
  }

  resetForm(form: WorkingGroupReferencesFormGroup, workingGroupReferences: WorkingGroupReferencesFormGroupInput): void {
    const workingGroupReferencesRawValue = { ...this.getFormDefaults(), ...workingGroupReferences };
    form.reset(
      {
        ...workingGroupReferencesRawValue,
        id: { value: workingGroupReferencesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): WorkingGroupReferencesFormDefaults {
    return {
      id: null,
      isActive: false,
    };
  }
}
