import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IReportBlocksContentData, NewReportBlocksContentData } from '../report-blocks-content-data.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IReportBlocksContentData for edit and NewReportBlocksContentDataFormGroupInput for create.
 */
type ReportBlocksContentDataFormGroupInput = IReportBlocksContentData | PartialWithRequiredKeyOf<NewReportBlocksContentData>;

type ReportBlocksContentDataFormDefaults = Pick<NewReportBlocksContentData, 'id'>;

type ReportBlocksContentDataFormGroupContent = {
  id: FormControl<IReportBlocksContentData['id'] | NewReportBlocksContentData['id']>;
  data: FormControl<IReportBlocksContentData['data']>;
  reportBlocksContent: FormControl<IReportBlocksContentData['reportBlocksContent']>;
  country: FormControl<IReportBlocksContentData['country']>;
};

export type ReportBlocksContentDataFormGroup = FormGroup<ReportBlocksContentDataFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ReportBlocksContentDataFormService {
  createReportBlocksContentDataFormGroup(
    reportBlocksContentData: ReportBlocksContentDataFormGroupInput = { id: null }
  ): ReportBlocksContentDataFormGroup {
    const reportBlocksContentDataRawValue = {
      ...this.getFormDefaults(),
      ...reportBlocksContentData,
    };
    return new FormGroup<ReportBlocksContentDataFormGroupContent>({
      id: new FormControl(
        { value: reportBlocksContentDataRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      data: new FormControl(reportBlocksContentDataRawValue.data),
      reportBlocksContent: new FormControl(reportBlocksContentDataRawValue.reportBlocksContent),
      country: new FormControl(reportBlocksContentDataRawValue.country),
    });
  }

  getReportBlocksContentData(form: ReportBlocksContentDataFormGroup): IReportBlocksContentData | NewReportBlocksContentData {
    return form.getRawValue() as IReportBlocksContentData | NewReportBlocksContentData;
  }

  resetForm(form: ReportBlocksContentDataFormGroup, reportBlocksContentData: ReportBlocksContentDataFormGroupInput): void {
    const reportBlocksContentDataRawValue = { ...this.getFormDefaults(), ...reportBlocksContentData };
    form.reset(
      {
        ...reportBlocksContentDataRawValue,
        id: { value: reportBlocksContentDataRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ReportBlocksContentDataFormDefaults {
    return {
      id: null,
    };
  }
}
