import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../moe-participation-references.test-samples';

import { MOEParticipationReferencesFormService } from './moe-participation-references-form.service';

describe('MOEParticipationReferences Form Service', () => {
  let service: MOEParticipationReferencesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MOEParticipationReferencesFormService);
  });

  describe('Service methods', () => {
    describe('createMOEParticipationReferencesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMOEParticipationReferencesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            type: expect.any(Object),
            startDate: expect.any(Object),
            endDate: expect.any(Object),
            isActive: expect.any(Object),
            createdBy: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdDate: expect.any(Object),
            lastModifiedDate: expect.any(Object),
          })
        );
      });

      it('passing IMOEParticipationReferences should create a new form with FormGroup', () => {
        const formGroup = service.createMOEParticipationReferencesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            type: expect.any(Object),
            startDate: expect.any(Object),
            endDate: expect.any(Object),
            isActive: expect.any(Object),
            createdBy: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdDate: expect.any(Object),
            lastModifiedDate: expect.any(Object),
          })
        );
      });
    });

    describe('getMOEParticipationReferences', () => {
      it('should return NewMOEParticipationReferences for default MOEParticipationReferences initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createMOEParticipationReferencesFormGroup(sampleWithNewData);

        const mOEParticipationReferences = service.getMOEParticipationReferences(formGroup) as any;

        expect(mOEParticipationReferences).toMatchObject(sampleWithNewData);
      });

      it('should return NewMOEParticipationReferences for empty MOEParticipationReferences initial value', () => {
        const formGroup = service.createMOEParticipationReferencesFormGroup();

        const mOEParticipationReferences = service.getMOEParticipationReferences(formGroup) as any;

        expect(mOEParticipationReferences).toMatchObject({});
      });

      it('should return IMOEParticipationReferences', () => {
        const formGroup = service.createMOEParticipationReferencesFormGroup(sampleWithRequiredData);

        const mOEParticipationReferences = service.getMOEParticipationReferences(formGroup) as any;

        expect(mOEParticipationReferences).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMOEParticipationReferences should not enable id FormControl', () => {
        const formGroup = service.createMOEParticipationReferencesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMOEParticipationReferences should disable id FormControl', () => {
        const formGroup = service.createMOEParticipationReferencesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
