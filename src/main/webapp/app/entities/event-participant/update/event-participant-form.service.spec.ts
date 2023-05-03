import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../event-participant.test-samples';

import { EventParticipantFormService } from './event-participant-form.service';

describe('EventParticipant Form Service', () => {
  let service: EventParticipantFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EventParticipantFormService);
  });

  describe('Service methods', () => {
    describe('createEventParticipantFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEventParticipantFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            type: expect.any(Object),
          })
        );
      });

      it('passing IEventParticipant should create a new form with FormGroup', () => {
        const formGroup = service.createEventParticipantFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            type: expect.any(Object),
          })
        );
      });
    });

    describe('getEventParticipant', () => {
      it('should return NewEventParticipant for default EventParticipant initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createEventParticipantFormGroup(sampleWithNewData);

        const eventParticipant = service.getEventParticipant(formGroup) as any;

        expect(eventParticipant).toMatchObject(sampleWithNewData);
      });

      it('should return NewEventParticipant for empty EventParticipant initial value', () => {
        const formGroup = service.createEventParticipantFormGroup();

        const eventParticipant = service.getEventParticipant(formGroup) as any;

        expect(eventParticipant).toMatchObject({});
      });

      it('should return IEventParticipant', () => {
        const formGroup = service.createEventParticipantFormGroup(sampleWithRequiredData);

        const eventParticipant = service.getEventParticipant(formGroup) as any;

        expect(eventParticipant).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEventParticipant should not enable id FormControl', () => {
        const formGroup = service.createEventParticipantFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEventParticipant should disable id FormControl', () => {
        const formGroup = service.createEventParticipantFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
