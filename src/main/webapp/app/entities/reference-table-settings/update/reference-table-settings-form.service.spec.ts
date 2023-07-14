import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../reference-table-settings.test-samples';

import { ReferenceTableSettingsFormService } from './reference-table-settings-form.service';

describe('ReferenceTableSettings Form Service', () => {
  let service: ReferenceTableSettingsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReferenceTableSettingsFormService);
  });

  describe('Service methods', () => {
    describe('createReferenceTableSettingsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createReferenceTableSettingsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            refTable: expect.any(Object),
            columns: expect.any(Object),
            path: expect.any(Object),
            isActive: expect.any(Object),
          })
        );
      });

      it('passing IReferenceTableSettings should create a new form with FormGroup', () => {
        const formGroup = service.createReferenceTableSettingsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            refTable: expect.any(Object),
            columns: expect.any(Object),
            path: expect.any(Object),
            isActive: expect.any(Object),
          })
        );
      });
    });

    describe('getReferenceTableSettings', () => {
      it('should return NewReferenceTableSettings for default ReferenceTableSettings initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createReferenceTableSettingsFormGroup(sampleWithNewData);

        const referenceTableSettings = service.getReferenceTableSettings(formGroup) as any;

        expect(referenceTableSettings).toMatchObject(sampleWithNewData);
      });

      it('should return NewReferenceTableSettings for empty ReferenceTableSettings initial value', () => {
        const formGroup = service.createReferenceTableSettingsFormGroup();

        const referenceTableSettings = service.getReferenceTableSettings(formGroup) as any;

        expect(referenceTableSettings).toMatchObject({});
      });

      it('should return IReferenceTableSettings', () => {
        const formGroup = service.createReferenceTableSettingsFormGroup(sampleWithRequiredData);

        const referenceTableSettings = service.getReferenceTableSettings(formGroup) as any;

        expect(referenceTableSettings).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IReferenceTableSettings should not enable id FormControl', () => {
        const formGroup = service.createReferenceTableSettingsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewReferenceTableSettings should disable id FormControl', () => {
        const formGroup = service.createReferenceTableSettingsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
