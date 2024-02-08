import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IReport, NewReport } from '../report.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IReport for edit and NewReportFormGroupInput for create.
 */
type ReportFormGroupInput = IReport | PartialWithRequiredKeyOf<NewReport>;

type ReportFormDefaults = Pick<NewReport, 'id' | 'reportBlockIds'>;

type ReportFormGroupContent = {
  id: FormControl<IReport['id'] | NewReport['id']>;
  reportName: FormControl<IReport['reportName']>;
  acronym: FormControl<IReport['acronym']>;
  description: FormControl<IReport['description']>;
  type: FormControl<IReport['type']>;
  isActive: FormControl<IReport['isActive']>;
  isMinistry: FormControl<IReport['isMinistry']>;
  parentId: FormControl<IReport['parentId']>;
  file: FormControl<IReport['file']>;
  fileContentType: FormControl<IReport['fileContentType']>;
  reportBlockIds: FormControl<IReport['reportBlockIds']>;
  reportTemplate: FormControl<IReport['reportTemplate']>;
};

export type ReportFormGroup = FormGroup<ReportFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ReportFormService {
  createReportFormGroup(report: ReportFormGroupInput = { id: null }): ReportFormGroup {
    const reportRawValue = {
      ...this.getFormDefaults(),
      ...report,
    };
    return new FormGroup<ReportFormGroupContent>({
      id: new FormControl(
        { value: reportRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      reportName: new FormControl(reportRawValue.reportName, {
        validators: [Validators.required],
      }),
      acronym: new FormControl(reportRawValue.acronym),
      description: new FormControl(reportRawValue.description),
      type: new FormControl(reportRawValue.type),
      isActive: new FormControl(reportRawValue.isActive ?? true),
      isMinistry: new FormControl(reportRawValue.isMinistry),
      parentId: new FormControl(reportRawValue.parentId),
      file: new FormControl(reportRawValue.file),
      fileContentType: new FormControl(reportRawValue.fileContentType),
      reportBlockIds: new FormControl(reportRawValue.reportBlockIds ?? []),
      reportTemplate: new FormControl(reportRawValue.reportTemplate),
    });
  }

  getReport(form: ReportFormGroup): IReport | NewReport {
    return form.getRawValue() as IReport | NewReport;
  }

  resetForm(form: ReportFormGroup, report: ReportFormGroupInput): void {
    const reportRawValue = { ...this.getFormDefaults(), ...report };
    form.reset(
      {
        ...reportRawValue,
        id: { value: reportRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ReportFormDefaults {
    return {
      id: null,
      reportBlockIds: [],
    };
  }
}
