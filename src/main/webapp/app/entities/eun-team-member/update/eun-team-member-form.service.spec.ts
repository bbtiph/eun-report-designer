import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../eun-team-member.test-samples';

import { EunTeamMemberFormService } from './eun-team-member-form.service';

describe('EunTeamMember Form Service', () => {
  let service: EunTeamMemberFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EunTeamMemberFormService);
  });

  describe('Service methods', () => {
    describe('createEunTeamMemberFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEunTeamMemberFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            role: expect.any(Object),
            status: expect.any(Object),
          })
        );
      });

      it('passing IEunTeamMember should create a new form with FormGroup', () => {
        const formGroup = service.createEunTeamMemberFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            role: expect.any(Object),
            status: expect.any(Object),
          })
        );
      });
    });

    describe('getEunTeamMember', () => {
      it('should return NewEunTeamMember for default EunTeamMember initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createEunTeamMemberFormGroup(sampleWithNewData);

        const eunTeamMember = service.getEunTeamMember(formGroup) as any;

        expect(eunTeamMember).toMatchObject(sampleWithNewData);
      });

      it('should return NewEunTeamMember for empty EunTeamMember initial value', () => {
        const formGroup = service.createEunTeamMemberFormGroup();

        const eunTeamMember = service.getEunTeamMember(formGroup) as any;

        expect(eunTeamMember).toMatchObject({});
      });

      it('should return IEunTeamMember', () => {
        const formGroup = service.createEunTeamMemberFormGroup(sampleWithRequiredData);

        const eunTeamMember = service.getEunTeamMember(formGroup) as any;

        expect(eunTeamMember).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEunTeamMember should not enable id FormControl', () => {
        const formGroup = service.createEunTeamMemberFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEunTeamMember should disable id FormControl', () => {
        const formGroup = service.createEunTeamMemberFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
