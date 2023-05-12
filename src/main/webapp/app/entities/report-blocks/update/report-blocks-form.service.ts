import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IReportBlocks, NewReportBlocks } from '../report-blocks.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IReportBlocks for edit and NewReportBlocksFormGroupInput for create.
 */
type ReportBlocksFormGroupInput = IReportBlocks | PartialWithRequiredKeyOf<NewReportBlocks>;

type ReportBlocksFormDefaults = Pick<NewReportBlocks, 'id' | 'isActive' | 'countryIds' | 'reportIds'>;

type ReportBlocksFormGroupContent = {
  id: FormControl<IReportBlocks['id'] | NewReportBlocks['id']>;
  countryName: FormControl<IReportBlocks['countryName']>;
  priorityNumber: FormControl<IReportBlocks['priorityNumber']>;
  content: FormControl<IReportBlocks['content']>;
  isActive: FormControl<IReportBlocks['isActive']>;
  type: FormControl<IReportBlocks['type']>;
  sqlScript: FormControl<IReportBlocks['sqlScript']>;
  countryIds: FormControl<IReportBlocks['countryIds']>;
  reportIds: FormControl<IReportBlocks['reportIds']>;
};

export type ReportBlocksFormGroup = FormGroup<ReportBlocksFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ReportBlocksFormService {
  createReportBlocksFormGroup(reportBlocks: ReportBlocksFormGroupInput = { id: null }): ReportBlocksFormGroup {
    const reportBlocksRawValue = {
      ...this.getFormDefaults(),
      ...reportBlocks,
    };
    return new FormGroup<ReportBlocksFormGroupContent>({
      id: new FormControl(
        { value: reportBlocksRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      countryName: new FormControl(reportBlocksRawValue.countryName),
      priorityNumber: new FormControl(reportBlocksRawValue.priorityNumber),
      content: new FormControl(reportBlocksRawValue.content),
      isActive: new FormControl(reportBlocksRawValue.isActive),
      type: new FormControl(reportBlocksRawValue.type),
      sqlScript: new FormControl(reportBlocksRawValue.sqlScript),
      countryIds: new FormControl(reportBlocksRawValue.countryIds ?? []),
      reportIds: new FormControl(reportBlocksRawValue.reportIds ?? []),
    });
  }

  getReportBlocks(form: ReportBlocksFormGroup): IReportBlocks | NewReportBlocks {
    return form.getRawValue() as IReportBlocks | NewReportBlocks;
  }

  resetForm(form: ReportBlocksFormGroup, reportBlocks: ReportBlocksFormGroupInput): void {
    const reportBlocksRawValue = { ...this.getFormDefaults(), ...reportBlocks };
    form.reset(
      {
        ...reportBlocksRawValue,
        id: { value: reportBlocksRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ReportBlocksFormDefaults {
    return {
      id: null,
      isActive: false,
      countryIds: [],
      reportIds: [],
    };
  }
}
