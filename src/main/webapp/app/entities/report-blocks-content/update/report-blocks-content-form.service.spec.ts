import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../report-blocks-content.test-samples';

import { ReportBlocksContentFormService } from './report-blocks-content-form.service';

describe('ReportBlocksContent Form Service', () => {
  let service: ReportBlocksContentFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReportBlocksContentFormService);
  });

  describe('Service methods', () => {
    describe('createReportBlocksContentFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createReportBlocksContentFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            type: expect.any(Object),
            priorityNumber: expect.any(Object),
            template: expect.any(Object),
            isActive: expect.any(Object),
            reportBlocks: expect.any(Object),
          })
        );
      });

      it('passing IReportBlocksContent should create a new form with FormGroup', () => {
        const formGroup = service.createReportBlocksContentFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            type: expect.any(Object),
            priorityNumber: expect.any(Object),
            template: expect.any(Object),
            isActive: expect.any(Object),
            reportBlocks: expect.any(Object),
          })
        );
      });
    });

    describe('getReportBlocksContent', () => {
      it('should return NewReportBlocksContent for default ReportBlocksContent initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createReportBlocksContentFormGroup(sampleWithNewData);

        const reportBlocksContent = service.getReportBlocksContent(formGroup) as any;

        expect(reportBlocksContent).toMatchObject(sampleWithNewData);
      });

      it('should return NewReportBlocksContent for empty ReportBlocksContent initial value', () => {
        const formGroup = service.createReportBlocksContentFormGroup();

        const reportBlocksContent = service.getReportBlocksContent(formGroup) as any;

        expect(reportBlocksContent).toMatchObject({});
      });

      it('should return IReportBlocksContent', () => {
        const formGroup = service.createReportBlocksContentFormGroup(sampleWithRequiredData);

        const reportBlocksContent = service.getReportBlocksContent(formGroup) as any;

        expect(reportBlocksContent).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IReportBlocksContent should not enable id FormControl', () => {
        const formGroup = service.createReportBlocksContentFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewReportBlocksContent should disable id FormControl', () => {
        const formGroup = service.createReportBlocksContentFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
