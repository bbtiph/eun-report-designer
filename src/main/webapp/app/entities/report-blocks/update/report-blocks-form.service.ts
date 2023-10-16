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
  name: FormControl<IReportBlocks['name']>;
  priorityNumber: FormControl<IReportBlocks['priorityNumber']>;
  isActive: FormControl<IReportBlocks['isActive']>;
  config: FormControl<IReportBlocks['config']>;
  countryIds: FormControl<IReportBlocks['countryIds']>;
  reportIds: FormControl<IReportBlocks['reportIds']>;
  reportBlocksContents: FormControl<IReportBlocks['reportBlocksContents']>;
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
      name: new FormControl(reportBlocksRawValue.name),
      priorityNumber: new FormControl(reportBlocksRawValue.priorityNumber),
      isActive: new FormControl(reportBlocksRawValue.isActive),
      config: new FormControl(reportBlocksRawValue.config),
      countryIds: new FormControl(reportBlocksRawValue.countryIds ?? []),
      reportIds: new FormControl(reportBlocksRawValue.reportIds ?? []),
      reportBlocksContents: new FormControl(reportBlocksRawValue.reportBlocksContents ?? []),
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
