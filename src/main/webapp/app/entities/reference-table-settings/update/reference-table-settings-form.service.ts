import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IReferenceTableSettings, NewReferenceTableSettings } from '../reference-table-settings.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IReferenceTableSettings for edit and NewReferenceTableSettingsFormGroupInput for create.
 */
type ReferenceTableSettingsFormGroupInput = IReferenceTableSettings | PartialWithRequiredKeyOf<NewReferenceTableSettings>;

type ReferenceTableSettingsFormDefaults = Pick<NewReferenceTableSettings, 'id' | 'isActive'>;

type ReferenceTableSettingsFormGroupContent = {
  id: FormControl<IReferenceTableSettings['id'] | NewReferenceTableSettings['id']>;
  refTable: FormControl<IReferenceTableSettings['refTable']>;
  columns: FormControl<IReferenceTableSettings['columns']>;
  path: FormControl<IReferenceTableSettings['path']>;
  isActive: FormControl<IReferenceTableSettings['isActive']>;
  file: FormControl<IReferenceTableSettings['file']>;
  fileContentType: FormControl<IReferenceTableSettings['fileContentType']>;
};

export type ReferenceTableSettingsFormGroup = FormGroup<ReferenceTableSettingsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ReferenceTableSettingsFormService {
  createReferenceTableSettingsFormGroup(
    referenceTableSettings: ReferenceTableSettingsFormGroupInput = { id: null }
  ): ReferenceTableSettingsFormGroup {
    const referenceTableSettingsRawValue = {
      ...this.getFormDefaults(),
      ...referenceTableSettings,
    };
    return new FormGroup<ReferenceTableSettingsFormGroupContent>({
      id: new FormControl(
        { value: referenceTableSettingsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      refTable: new FormControl(referenceTableSettingsRawValue.refTable),
      columns: new FormControl(referenceTableSettingsRawValue.columns),
      path: new FormControl(referenceTableSettingsRawValue.path),
      isActive: new FormControl(referenceTableSettingsRawValue.isActive),
      file: new FormControl(referenceTableSettingsRawValue.file),
      fileContentType: new FormControl(referenceTableSettingsRawValue.fileContentType),
    });
  }

  getReferenceTableSettings(form: ReferenceTableSettingsFormGroup): IReferenceTableSettings | NewReferenceTableSettings {
    return form.getRawValue() as IReferenceTableSettings | NewReferenceTableSettings;
  }

  resetForm(form: ReferenceTableSettingsFormGroup, referenceTableSettings: ReferenceTableSettingsFormGroupInput): void {
    const referenceTableSettingsRawValue = { ...this.getFormDefaults(), ...referenceTableSettings };
    form.reset(
      {
        ...referenceTableSettingsRawValue,
        id: { value: referenceTableSettingsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ReferenceTableSettingsFormDefaults {
    return {
      id: null,
      isActive: false,
    };
  }
}
