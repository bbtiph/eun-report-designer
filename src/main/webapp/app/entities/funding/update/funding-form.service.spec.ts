import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../funding.test-samples';

import { FundingFormService } from './funding-form.service';

describe('Funding Form Service', () => {
  let service: FundingFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FundingFormService);
  });

  describe('Service methods', () => {
    describe('createFundingFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFundingFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            type: expect.any(Object),
            parentId: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });

      it('passing IFunding should create a new form with FormGroup', () => {
        const formGroup = service.createFundingFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            type: expect.any(Object),
            parentId: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });
    });

    describe('getFunding', () => {
      it('should return NewFunding for default Funding initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createFundingFormGroup(sampleWithNewData);

        const funding = service.getFunding(formGroup) as any;

        expect(funding).toMatchObject(sampleWithNewData);
      });

      it('should return NewFunding for empty Funding initial value', () => {
        const formGroup = service.createFundingFormGroup();

        const funding = service.getFunding(formGroup) as any;

        expect(funding).toMatchObject({});
      });

      it('should return IFunding', () => {
        const formGroup = service.createFundingFormGroup(sampleWithRequiredData);

        const funding = service.getFunding(formGroup) as any;

        expect(funding).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFunding should not enable id FormControl', () => {
        const formGroup = service.createFundingFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFunding should disable id FormControl', () => {
        const formGroup = service.createFundingFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
