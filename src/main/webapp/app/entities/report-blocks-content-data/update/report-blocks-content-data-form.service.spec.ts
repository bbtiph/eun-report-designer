import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../report-blocks-content-data.test-samples';

import { ReportBlocksContentDataFormService } from './report-blocks-content-data-form.service';

describe('ReportBlocksContentData Form Service', () => {
  let service: ReportBlocksContentDataFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReportBlocksContentDataFormService);
  });

  describe('Service methods', () => {
    describe('createReportBlocksContentDataFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createReportBlocksContentDataFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            data: expect.any(Object),
            reportBlocksContent: expect.any(Object),
            country: expect.any(Object),
          })
        );
      });

      it('passing IReportBlocksContentData should create a new form with FormGroup', () => {
        const formGroup = service.createReportBlocksContentDataFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            data: expect.any(Object),
            reportBlocksContent: expect.any(Object),
            country: expect.any(Object),
          })
        );
      });
    });

    describe('getReportBlocksContentData', () => {
      it('should return NewReportBlocksContentData for default ReportBlocksContentData initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createReportBlocksContentDataFormGroup(sampleWithNewData);

        const reportBlocksContentData = service.getReportBlocksContentData(formGroup) as any;

        expect(reportBlocksContentData).toMatchObject(sampleWithNewData);
      });

      it('should return NewReportBlocksContentData for empty ReportBlocksContentData initial value', () => {
        const formGroup = service.createReportBlocksContentDataFormGroup();

        const reportBlocksContentData = service.getReportBlocksContentData(formGroup) as any;

        expect(reportBlocksContentData).toMatchObject({});
      });

      it('should return IReportBlocksContentData', () => {
        const formGroup = service.createReportBlocksContentDataFormGroup(sampleWithRequiredData);

        const reportBlocksContentData = service.getReportBlocksContentData(formGroup) as any;

        expect(reportBlocksContentData).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IReportBlocksContentData should not enable id FormControl', () => {
        const formGroup = service.createReportBlocksContentDataFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewReportBlocksContentData should disable id FormControl', () => {
        const formGroup = service.createReportBlocksContentDataFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
