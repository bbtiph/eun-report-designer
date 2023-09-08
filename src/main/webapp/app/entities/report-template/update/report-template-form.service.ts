import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IReportTemplate, NewReportTemplate } from '../report-template.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IReportTemplate for edit and NewReportTemplateFormGroupInput for create.
 */
type ReportTemplateFormGroupInput = IReportTemplate | PartialWithRequiredKeyOf<NewReportTemplate>;

type ReportTemplateFormDefaults = Pick<NewReportTemplate, 'id' | 'isActive'>;

type ReportTemplateFormGroupContent = {
  id: FormControl<IReportTemplate['id'] | NewReportTemplate['id']>;
  name: FormControl<IReportTemplate['name']>;
  description: FormControl<IReportTemplate['description']>;
  type: FormControl<IReportTemplate['type']>;
  isActive: FormControl<IReportTemplate['isActive']>;
  file: FormControl<IReportTemplate['file']>;
  fileContentType: FormControl<IReportTemplate['fileContentType']>;
  createdBy: FormControl<IReportTemplate['createdBy']>;
  lastModifiedBy: FormControl<IReportTemplate['lastModifiedBy']>;
  createdDate: FormControl<IReportTemplate['createdDate']>;
  lastModifiedDate: FormControl<IReportTemplate['lastModifiedDate']>;
};

export type ReportTemplateFormGroup = FormGroup<ReportTemplateFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ReportTemplateFormService {
  createReportTemplateFormGroup(reportTemplate: ReportTemplateFormGroupInput = { id: null }): ReportTemplateFormGroup {
    const reportTemplateRawValue = {
      ...this.getFormDefaults(),
      ...reportTemplate,
    };
    return new FormGroup<ReportTemplateFormGroupContent>({
      id: new FormControl(
        { value: reportTemplateRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(reportTemplateRawValue.name, {
        validators: [Validators.required],
      }),
      description: new FormControl(reportTemplateRawValue.description, {
        validators: [Validators.required],
      }),
      type: new FormControl(reportTemplateRawValue.type),
      isActive: new FormControl(reportTemplateRawValue.isActive),
      file: new FormControl(reportTemplateRawValue.file, {
        validators: [Validators.required],
      }),
      fileContentType: new FormControl(reportTemplateRawValue.fileContentType),
      createdBy: new FormControl(reportTemplateRawValue.createdBy),
      lastModifiedBy: new FormControl(reportTemplateRawValue.lastModifiedBy),
      createdDate: new FormControl(reportTemplateRawValue.createdDate),
      lastModifiedDate: new FormControl(reportTemplateRawValue.lastModifiedDate),
    });
  }

  getReportTemplate(form: ReportTemplateFormGroup): IReportTemplate | NewReportTemplate {
    return form.getRawValue() as IReportTemplate | NewReportTemplate;
  }

  resetForm(form: ReportTemplateFormGroup, reportTemplate: ReportTemplateFormGroupInput): void {
    const reportTemplateRawValue = { ...this.getFormDefaults(), ...reportTemplate };
    form.reset(
      {
        ...reportTemplateRawValue,
        id: { value: reportTemplateRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ReportTemplateFormDefaults {
    return {
      id: null,
      isActive: false,
    };
  }
}
