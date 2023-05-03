import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../operational-body.test-samples';

import { OperationalBodyFormService } from './operational-body-form.service';

describe('OperationalBody Form Service', () => {
  let service: OperationalBodyFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OperationalBodyFormService);
  });

  describe('Service methods', () => {
    describe('createOperationalBodyFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createOperationalBodyFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            acronym: expect.any(Object),
            description: expect.any(Object),
            type: expect.any(Object),
            status: expect.any(Object),
          })
        );
      });

      it('passing IOperationalBody should create a new form with FormGroup', () => {
        const formGroup = service.createOperationalBodyFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            acronym: expect.any(Object),
            description: expect.any(Object),
            type: expect.any(Object),
            status: expect.any(Object),
          })
        );
      });
    });

    describe('getOperationalBody', () => {
      it('should return NewOperationalBody for default OperationalBody initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createOperationalBodyFormGroup(sampleWithNewData);

        const operationalBody = service.getOperationalBody(formGroup) as any;

        expect(operationalBody).toMatchObject(sampleWithNewData);
      });

      it('should return NewOperationalBody for empty OperationalBody initial value', () => {
        const formGroup = service.createOperationalBodyFormGroup();

        const operationalBody = service.getOperationalBody(formGroup) as any;

        expect(operationalBody).toMatchObject({});
      });

      it('should return IOperationalBody', () => {
        const formGroup = service.createOperationalBodyFormGroup(sampleWithRequiredData);

        const operationalBody = service.getOperationalBody(formGroup) as any;

        expect(operationalBody).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IOperationalBody should not enable id FormControl', () => {
        const formGroup = service.createOperationalBodyFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewOperationalBody should disable id FormControl', () => {
        const formGroup = service.createOperationalBodyFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
