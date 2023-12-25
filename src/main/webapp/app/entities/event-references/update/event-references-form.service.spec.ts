import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../event-references.test-samples';

import { EventReferencesFormService } from './event-references-form.service';

describe('EventReferences Form Service', () => {
  let service: EventReferencesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EventReferencesFormService);
  });

  describe('Service methods', () => {
    describe('createEventReferencesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEventReferencesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            type: expect.any(Object),
          })
        );
      });

      it('passing IEventReferences should create a new form with FormGroup', () => {
        const formGroup = service.createEventReferencesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            type: expect.any(Object),
          })
        );
      });
    });

    describe('getEventReferences', () => {
      it('should return NewEventReferences for default EventReferences initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createEventReferencesFormGroup(sampleWithNewData);

        const eventReferences = service.getEventReferences(formGroup) as any;

        expect(eventReferences).toMatchObject(sampleWithNewData);
      });

      it('should return NewEventReferences for empty EventReferences initial value', () => {
        const formGroup = service.createEventReferencesFormGroup();

        const eventReferences = service.getEventReferences(formGroup) as any;

        expect(eventReferences).toMatchObject({});
      });

      it('should return IEventReferences', () => {
        const formGroup = service.createEventReferencesFormGroup(sampleWithRequiredData);

        const eventReferences = service.getEventReferences(formGroup) as any;

        expect(eventReferences).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEventReferences should not enable id FormControl', () => {
        const formGroup = service.createEventReferencesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEventReferences should disable id FormControl', () => {
        const formGroup = service.createEventReferencesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
