import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../generated-report.test-samples';

import { GeneratedReportFormService } from './generated-report-form.service';

describe('GeneratedReport Form Service', () => {
  let service: GeneratedReportFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GeneratedReportFormService);
  });

  describe('Service methods', () => {
    describe('createGeneratedReportFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createGeneratedReportFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            description: expect.any(Object),
            requestBody: expect.any(Object),
            isActive: expect.any(Object),
            file: expect.any(Object),
            createdBy: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdDate: expect.any(Object),
            lastModifiedDate: expect.any(Object),
          })
        );
      });

      it('passing IGeneratedReport should create a new form with FormGroup', () => {
        const formGroup = service.createGeneratedReportFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            description: expect.any(Object),
            requestBody: expect.any(Object),
            isActive: expect.any(Object),
            file: expect.any(Object),
            createdBy: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdDate: expect.any(Object),
            lastModifiedDate: expect.any(Object),
          })
        );
      });
    });

    describe('getGeneratedReport', () => {
      it('should return NewGeneratedReport for default GeneratedReport initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createGeneratedReportFormGroup(sampleWithNewData);

        const generatedReport = service.getGeneratedReport(formGroup) as any;

        expect(generatedReport).toMatchObject(sampleWithNewData);
      });

      it('should return NewGeneratedReport for empty GeneratedReport initial value', () => {
        const formGroup = service.createGeneratedReportFormGroup();

        const generatedReport = service.getGeneratedReport(formGroup) as any;

        expect(generatedReport).toMatchObject({});
      });

      it('should return IGeneratedReport', () => {
        const formGroup = service.createGeneratedReportFormGroup(sampleWithRequiredData);

        const generatedReport = service.getGeneratedReport(formGroup) as any;

        expect(generatedReport).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IGeneratedReport should not enable id FormControl', () => {
        const formGroup = service.createGeneratedReportFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewGeneratedReport should disable id FormControl', () => {
        const formGroup = service.createGeneratedReportFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
