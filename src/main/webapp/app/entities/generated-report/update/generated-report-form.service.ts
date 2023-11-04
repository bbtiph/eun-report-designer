import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IGeneratedReport, NewGeneratedReport } from '../generated-report.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IGeneratedReport for edit and NewGeneratedReportFormGroupInput for create.
 */
type GeneratedReportFormGroupInput = IGeneratedReport | PartialWithRequiredKeyOf<NewGeneratedReport>;

type GeneratedReportFormDefaults = Pick<NewGeneratedReport, 'id' | 'isActive'>;

type GeneratedReportFormGroupContent = {
  id: FormControl<IGeneratedReport['id'] | NewGeneratedReport['id']>;
  name: FormControl<IGeneratedReport['name']>;
  description: FormControl<IGeneratedReport['description']>;
  requestBody: FormControl<IGeneratedReport['requestBody']>;
  isActive: FormControl<IGeneratedReport['isActive']>;
  file: FormControl<IGeneratedReport['file']>;
  fileContentType: FormControl<IGeneratedReport['fileContentType']>;
  createdBy: FormControl<IGeneratedReport['createdBy']>;
  lastModifiedBy: FormControl<IGeneratedReport['lastModifiedBy']>;
  createdDate: FormControl<IGeneratedReport['createdDate']>;
  lastModifiedDate: FormControl<IGeneratedReport['lastModifiedDate']>;
};

export type GeneratedReportFormGroup = FormGroup<GeneratedReportFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class GeneratedReportFormService {
  createGeneratedReportFormGroup(generatedReport: GeneratedReportFormGroupInput = { id: null }): GeneratedReportFormGroup {
    const generatedReportRawValue = {
      ...this.getFormDefaults(),
      ...generatedReport,
    };
    return new FormGroup<GeneratedReportFormGroupContent>({
      id: new FormControl(
        { value: generatedReportRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(generatedReportRawValue.name),
      description: new FormControl(generatedReportRawValue.description),
      requestBody: new FormControl(generatedReportRawValue.requestBody),
      isActive: new FormControl(generatedReportRawValue.isActive),
      file: new FormControl(generatedReportRawValue.file),
      fileContentType: new FormControl(generatedReportRawValue.fileContentType),
      createdBy: new FormControl(generatedReportRawValue.createdBy),
      lastModifiedBy: new FormControl(generatedReportRawValue.lastModifiedBy),
      createdDate: new FormControl(generatedReportRawValue.createdDate),
      lastModifiedDate: new FormControl(generatedReportRawValue.lastModifiedDate),
    });
  }

  getGeneratedReport(form: GeneratedReportFormGroup): IGeneratedReport | NewGeneratedReport {
    return form.getRawValue() as IGeneratedReport | NewGeneratedReport;
  }

  resetForm(form: GeneratedReportFormGroup, generatedReport: GeneratedReportFormGroupInput): void {
    const generatedReportRawValue = { ...this.getFormDefaults(), ...generatedReport };
    form.reset(
      {
        ...generatedReportRawValue,
        id: { value: generatedReportRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): GeneratedReportFormDefaults {
    return {
      id: null,
      isActive: false,
    };
  }
}
