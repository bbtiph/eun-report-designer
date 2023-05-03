import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../operational-body-member.test-samples';

import { OperationalBodyMemberFormService } from './operational-body-member-form.service';

describe('OperationalBodyMember Form Service', () => {
  let service: OperationalBodyMemberFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OperationalBodyMemberFormService);
  });

  describe('Service methods', () => {
    describe('createOperationalBodyMemberFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createOperationalBodyMemberFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            personId: expect.any(Object),
            position: expect.any(Object),
            startDate: expect.any(Object),
            endDate: expect.any(Object),
            department: expect.any(Object),
            eunContactFirstname: expect.any(Object),
            eunContactLastname: expect.any(Object),
            cooperationField: expect.any(Object),
            status: expect.any(Object),
            country: expect.any(Object),
          })
        );
      });

      it('passing IOperationalBodyMember should create a new form with FormGroup', () => {
        const formGroup = service.createOperationalBodyMemberFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            personId: expect.any(Object),
            position: expect.any(Object),
            startDate: expect.any(Object),
            endDate: expect.any(Object),
            department: expect.any(Object),
            eunContactFirstname: expect.any(Object),
            eunContactLastname: expect.any(Object),
            cooperationField: expect.any(Object),
            status: expect.any(Object),
            country: expect.any(Object),
          })
        );
      });
    });

    describe('getOperationalBodyMember', () => {
      it('should return NewOperationalBodyMember for default OperationalBodyMember initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createOperationalBodyMemberFormGroup(sampleWithNewData);

        const operationalBodyMember = service.getOperationalBodyMember(formGroup) as any;

        expect(operationalBodyMember).toMatchObject(sampleWithNewData);
      });

      it('should return NewOperationalBodyMember for empty OperationalBodyMember initial value', () => {
        const formGroup = service.createOperationalBodyMemberFormGroup();

        const operationalBodyMember = service.getOperationalBodyMember(formGroup) as any;

        expect(operationalBodyMember).toMatchObject({});
      });

      it('should return IOperationalBodyMember', () => {
        const formGroup = service.createOperationalBodyMemberFormGroup(sampleWithRequiredData);

        const operationalBodyMember = service.getOperationalBodyMember(formGroup) as any;

        expect(operationalBodyMember).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IOperationalBodyMember should not enable id FormControl', () => {
        const formGroup = service.createOperationalBodyMemberFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewOperationalBodyMember should disable id FormControl', () => {
        const formGroup = service.createOperationalBodyMemberFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
