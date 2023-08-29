import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IParticipantsEunIndicator, NewParticipantsEunIndicator } from '../participants-eun-indicator.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IParticipantsEunIndicator for edit and NewParticipantsEunIndicatorFormGroupInput for create.
 */
type ParticipantsEunIndicatorFormGroupInput = IParticipantsEunIndicator | PartialWithRequiredKeyOf<NewParticipantsEunIndicator>;

type ParticipantsEunIndicatorFormDefaults = Pick<NewParticipantsEunIndicator, 'id'>;

type ParticipantsEunIndicatorFormGroupContent = {
  id: FormControl<IParticipantsEunIndicator['id'] | NewParticipantsEunIndicator['id']>;
  period: FormControl<IParticipantsEunIndicator['period']>;
  nCount: FormControl<IParticipantsEunIndicator['nCount']>;
  courseId: FormControl<IParticipantsEunIndicator['courseId']>;
  courseName: FormControl<IParticipantsEunIndicator['courseName']>;
  countryCode: FormControl<IParticipantsEunIndicator['countryCode']>;
  createdBy: FormControl<IParticipantsEunIndicator['createdBy']>;
  lastModifiedBy: FormControl<IParticipantsEunIndicator['lastModifiedBy']>;
  createdDate: FormControl<IParticipantsEunIndicator['createdDate']>;
  lastModifiedDate: FormControl<IParticipantsEunIndicator['lastModifiedDate']>;
};

export type ParticipantsEunIndicatorFormGroup = FormGroup<ParticipantsEunIndicatorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ParticipantsEunIndicatorFormService {
  createParticipantsEunIndicatorFormGroup(
    participantsEunIndicator: ParticipantsEunIndicatorFormGroupInput = { id: null }
  ): ParticipantsEunIndicatorFormGroup {
    const participantsEunIndicatorRawValue = {
      ...this.getFormDefaults(),
      ...participantsEunIndicator,
    };
    return new FormGroup<ParticipantsEunIndicatorFormGroupContent>({
      id: new FormControl(
        { value: participantsEunIndicatorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      period: new FormControl(participantsEunIndicatorRawValue.period),
      nCount: new FormControl(participantsEunIndicatorRawValue.nCount),
      courseId: new FormControl(participantsEunIndicatorRawValue.courseId),
      courseName: new FormControl(participantsEunIndicatorRawValue.courseName),
      countryCode: new FormControl(participantsEunIndicatorRawValue.countryCode),
      createdBy: new FormControl(participantsEunIndicatorRawValue.createdBy),
      lastModifiedBy: new FormControl(participantsEunIndicatorRawValue.lastModifiedBy),
      createdDate: new FormControl(participantsEunIndicatorRawValue.createdDate),
      lastModifiedDate: new FormControl(participantsEunIndicatorRawValue.lastModifiedDate),
    });
  }

  getParticipantsEunIndicator(form: ParticipantsEunIndicatorFormGroup): IParticipantsEunIndicator | NewParticipantsEunIndicator {
    return form.getRawValue() as IParticipantsEunIndicator | NewParticipantsEunIndicator;
  }

  resetForm(form: ParticipantsEunIndicatorFormGroup, participantsEunIndicator: ParticipantsEunIndicatorFormGroupInput): void {
    const participantsEunIndicatorRawValue = { ...this.getFormDefaults(), ...participantsEunIndicator };
    form.reset(
      {
        ...participantsEunIndicatorRawValue,
        id: { value: participantsEunIndicatorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ParticipantsEunIndicatorFormDefaults {
    return {
      id: null,
    };
  }
}
