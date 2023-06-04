import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IReportBlocksContent, NewReportBlocksContent } from '../report-blocks-content.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IReportBlocksContent for edit and NewReportBlocksContentFormGroupInput for create.
 */
type ReportBlocksContentFormGroupInput = IReportBlocksContent | PartialWithRequiredKeyOf<NewReportBlocksContent>;

type ReportBlocksContentFormDefaults = Pick<NewReportBlocksContent, 'id' | 'isActive'>;

type ReportBlocksContentFormGroupContent = {
  id: FormControl<IReportBlocksContent['id'] | NewReportBlocksContent['id']>;
  type: FormControl<IReportBlocksContent['type']>;
  priorityNumber: FormControl<IReportBlocksContent['priorityNumber']>;
  template: FormControl<IReportBlocksContent['template']>;
  isActive: FormControl<IReportBlocksContent['isActive']>;
  reportBlocks: FormControl<IReportBlocksContent['reportBlocks']>;
};

export type ReportBlocksContentFormGroup = FormGroup<ReportBlocksContentFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ReportBlocksContentFormService {
  createReportBlocksContentFormGroup(reportBlocksContent: ReportBlocksContentFormGroupInput = { id: null }): ReportBlocksContentFormGroup {
    const reportBlocksContentRawValue = {
      ...this.getFormDefaults(),
      ...reportBlocksContent,
    };
    return new FormGroup<ReportBlocksContentFormGroupContent>({
      id: new FormControl(
        { value: reportBlocksContentRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      type: new FormControl(reportBlocksContentRawValue.type),
      priorityNumber: new FormControl(reportBlocksContentRawValue.priorityNumber),
      template: new FormControl(reportBlocksContentRawValue.template),
      isActive: new FormControl(reportBlocksContentRawValue.isActive),
      reportBlocks: new FormControl(reportBlocksContentRawValue.reportBlocks),
    });
  }

  getReportBlocksContent(form: ReportBlocksContentFormGroup): IReportBlocksContent | NewReportBlocksContent {
    return form.getRawValue() as IReportBlocksContent | NewReportBlocksContent;
  }

  resetForm(form: ReportBlocksContentFormGroup, reportBlocksContent: ReportBlocksContentFormGroupInput): void {
    const reportBlocksContentRawValue = { ...this.getFormDefaults(), ...reportBlocksContent };
    form.reset(
      {
        ...reportBlocksContentRawValue,
        id: { value: reportBlocksContentRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ReportBlocksContentFormDefaults {
    return {
      id: null,
      isActive: false,
    };
  }
}
