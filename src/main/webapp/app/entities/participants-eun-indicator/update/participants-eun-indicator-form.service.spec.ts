import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../participants-eun-indicator.test-samples';

import { ParticipantsEunIndicatorFormService } from './participants-eun-indicator-form.service';

describe('ParticipantsEunIndicator Form Service', () => {
  let service: ParticipantsEunIndicatorFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ParticipantsEunIndicatorFormService);
  });

  describe('Service methods', () => {
    describe('createParticipantsEunIndicatorFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createParticipantsEunIndicatorFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            period: expect.any(Object),
            nCount: expect.any(Object),
            courseId: expect.any(Object),
            courseName: expect.any(Object),
            countryCode: expect.any(Object),
            createdBy: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdDate: expect.any(Object),
            lastModifiedDate: expect.any(Object),
          })
        );
      });

      it('passing IParticipantsEunIndicator should create a new form with FormGroup', () => {
        const formGroup = service.createParticipantsEunIndicatorFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            period: expect.any(Object),
            nCount: expect.any(Object),
            courseId: expect.any(Object),
            courseName: expect.any(Object),
            countryCode: expect.any(Object),
            createdBy: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdDate: expect.any(Object),
            lastModifiedDate: expect.any(Object),
          })
        );
      });
    });

    describe('getParticipantsEunIndicator', () => {
      it('should return NewParticipantsEunIndicator for default ParticipantsEunIndicator initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createParticipantsEunIndicatorFormGroup(sampleWithNewData);

        const participantsEunIndicator = service.getParticipantsEunIndicator(formGroup) as any;

        expect(participantsEunIndicator).toMatchObject(sampleWithNewData);
      });

      it('should return NewParticipantsEunIndicator for empty ParticipantsEunIndicator initial value', () => {
        const formGroup = service.createParticipantsEunIndicatorFormGroup();

        const participantsEunIndicator = service.getParticipantsEunIndicator(formGroup) as any;

        expect(participantsEunIndicator).toMatchObject({});
      });

      it('should return IParticipantsEunIndicator', () => {
        const formGroup = service.createParticipantsEunIndicatorFormGroup(sampleWithRequiredData);

        const participantsEunIndicator = service.getParticipantsEunIndicator(formGroup) as any;

        expect(participantsEunIndicator).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IParticipantsEunIndicator should not enable id FormControl', () => {
        const formGroup = service.createParticipantsEunIndicatorFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewParticipantsEunIndicator should disable id FormControl', () => {
        const formGroup = service.createParticipantsEunIndicatorFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
