import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../report-blocks.test-samples';

import { ReportBlocksFormService } from './report-blocks-form.service';

describe('ReportBlocks Form Service', () => {
  let service: ReportBlocksFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReportBlocksFormService);
  });

  describe('Service methods', () => {
    describe('createReportBlocksFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createReportBlocksFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            priorityNumber: expect.any(Object),
            isActive: expect.any(Object),
            config: expect.any(Object),
            countryIds: expect.any(Object),
            report: expect.any(Object),
          })
        );
      });

      it('passing IReportBlocks should create a new form with FormGroup', () => {
        const formGroup = service.createReportBlocksFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            priorityNumber: expect.any(Object),
            isActive: expect.any(Object),
            config: expect.any(Object),
            countryIds: expect.any(Object),
            report: expect.any(Object),
          })
        );
      });
    });

    describe('getReportBlocks', () => {
      it('should return NewReportBlocks for default ReportBlocks initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createReportBlocksFormGroup(sampleWithNewData);

        const reportBlocks = service.getReportBlocks(formGroup) as any;

        expect(reportBlocks).toMatchObject(sampleWithNewData);
      });

      it('should return NewReportBlocks for empty ReportBlocks initial value', () => {
        const formGroup = service.createReportBlocksFormGroup();

        const reportBlocks = service.getReportBlocks(formGroup) as any;

        expect(reportBlocks).toMatchObject({});
      });

      it('should return IReportBlocks', () => {
        const formGroup = service.createReportBlocksFormGroup(sampleWithRequiredData);

        const reportBlocks = service.getReportBlocks(formGroup) as any;

        expect(reportBlocks).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IReportBlocks should not enable id FormControl', () => {
        const formGroup = service.createReportBlocksFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewReportBlocks should disable id FormControl', () => {
        const formGroup = service.createReportBlocksFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
