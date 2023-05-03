import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../ministry.test-samples';

import { MinistryFormService } from './ministry-form.service';

describe('Ministry Form Service', () => {
  let service: MinistryFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MinistryFormService);
  });

  describe('Service methods', () => {
    describe('createMinistryFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMinistryFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            languages: expect.any(Object),
            formalName: expect.any(Object),
            englishName: expect.any(Object),
            acronym: expect.any(Object),
            description: expect.any(Object),
            website: expect.any(Object),
            steercomMemberName: expect.any(Object),
            steercomMemberEmail: expect.any(Object),
            postalAddressRegion: expect.any(Object),
            postalAddressPostalCode: expect.any(Object),
            postalAddressCity: expect.any(Object),
            postalAddressStreet: expect.any(Object),
            status: expect.any(Object),
            eunContactFirstname: expect.any(Object),
            eunContactLastname: expect.any(Object),
            eunContactEmail: expect.any(Object),
            invoicingAddress: expect.any(Object),
            financialCorrespondingEmail: expect.any(Object),
            country: expect.any(Object),
          })
        );
      });

      it('passing IMinistry should create a new form with FormGroup', () => {
        const formGroup = service.createMinistryFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            languages: expect.any(Object),
            formalName: expect.any(Object),
            englishName: expect.any(Object),
            acronym: expect.any(Object),
            description: expect.any(Object),
            website: expect.any(Object),
            steercomMemberName: expect.any(Object),
            steercomMemberEmail: expect.any(Object),
            postalAddressRegion: expect.any(Object),
            postalAddressPostalCode: expect.any(Object),
            postalAddressCity: expect.any(Object),
            postalAddressStreet: expect.any(Object),
            status: expect.any(Object),
            eunContactFirstname: expect.any(Object),
            eunContactLastname: expect.any(Object),
            eunContactEmail: expect.any(Object),
            invoicingAddress: expect.any(Object),
            financialCorrespondingEmail: expect.any(Object),
            country: expect.any(Object),
          })
        );
      });
    });

    describe('getMinistry', () => {
      it('should return NewMinistry for default Ministry initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createMinistryFormGroup(sampleWithNewData);

        const ministry = service.getMinistry(formGroup) as any;

        expect(ministry).toMatchObject(sampleWithNewData);
      });

      it('should return NewMinistry for empty Ministry initial value', () => {
        const formGroup = service.createMinistryFormGroup();

        const ministry = service.getMinistry(formGroup) as any;

        expect(ministry).toMatchObject({});
      });

      it('should return IMinistry', () => {
        const formGroup = service.createMinistryFormGroup(sampleWithRequiredData);

        const ministry = service.getMinistry(formGroup) as any;

        expect(ministry).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMinistry should not enable id FormControl', () => {
        const formGroup = service.createMinistryFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMinistry should disable id FormControl', () => {
        const formGroup = service.createMinistryFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
