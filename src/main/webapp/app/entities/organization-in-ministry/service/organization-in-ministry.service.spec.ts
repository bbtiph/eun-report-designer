import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IOrganizationInMinistry } from '../organization-in-ministry.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../organization-in-ministry.test-samples';

import { OrganizationInMinistryService } from './organization-in-ministry.service';

const requireRestSample: IOrganizationInMinistry = {
  ...sampleWithRequiredData,
};

describe('OrganizationInMinistry Service', () => {
  let service: OrganizationInMinistryService;
  let httpMock: HttpTestingController;
  let expectedResult: IOrganizationInMinistry | IOrganizationInMinistry[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(OrganizationInMinistryService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a OrganizationInMinistry', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const organizationInMinistry = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(organizationInMinistry).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a OrganizationInMinistry', () => {
      const organizationInMinistry = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(organizationInMinistry).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a OrganizationInMinistry', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of OrganizationInMinistry', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a OrganizationInMinistry', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addOrganizationInMinistryToCollectionIfMissing', () => {
      it('should add a OrganizationInMinistry to an empty array', () => {
        const organizationInMinistry: IOrganizationInMinistry = sampleWithRequiredData;
        expectedResult = service.addOrganizationInMinistryToCollectionIfMissing([], organizationInMinistry);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(organizationInMinistry);
      });

      it('should not add a OrganizationInMinistry to an array that contains it', () => {
        const organizationInMinistry: IOrganizationInMinistry = sampleWithRequiredData;
        const organizationInMinistryCollection: IOrganizationInMinistry[] = [
          {
            ...organizationInMinistry,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addOrganizationInMinistryToCollectionIfMissing(organizationInMinistryCollection, organizationInMinistry);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a OrganizationInMinistry to an array that doesn't contain it", () => {
        const organizationInMinistry: IOrganizationInMinistry = sampleWithRequiredData;
        const organizationInMinistryCollection: IOrganizationInMinistry[] = [sampleWithPartialData];
        expectedResult = service.addOrganizationInMinistryToCollectionIfMissing(organizationInMinistryCollection, organizationInMinistry);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(organizationInMinistry);
      });

      it('should add only unique OrganizationInMinistry to an array', () => {
        const organizationInMinistryArray: IOrganizationInMinistry[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const organizationInMinistryCollection: IOrganizationInMinistry[] = [sampleWithRequiredData];
        expectedResult = service.addOrganizationInMinistryToCollectionIfMissing(
          organizationInMinistryCollection,
          ...organizationInMinistryArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const organizationInMinistry: IOrganizationInMinistry = sampleWithRequiredData;
        const organizationInMinistry2: IOrganizationInMinistry = sampleWithPartialData;
        expectedResult = service.addOrganizationInMinistryToCollectionIfMissing([], organizationInMinistry, organizationInMinistry2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(organizationInMinistry);
        expect(expectedResult).toContain(organizationInMinistry2);
      });

      it('should accept null and undefined values', () => {
        const organizationInMinistry: IOrganizationInMinistry = sampleWithRequiredData;
        expectedResult = service.addOrganizationInMinistryToCollectionIfMissing([], null, organizationInMinistry, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(organizationInMinistry);
      });

      it('should return initial array if no OrganizationInMinistry is added', () => {
        const organizationInMinistryCollection: IOrganizationInMinistry[] = [sampleWithRequiredData];
        expectedResult = service.addOrganizationInMinistryToCollectionIfMissing(organizationInMinistryCollection, undefined, null);
        expect(expectedResult).toEqual(organizationInMinistryCollection);
      });
    });

    describe('compareOrganizationInMinistry', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareOrganizationInMinistry(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareOrganizationInMinistry(entity1, entity2);
        const compareResult2 = service.compareOrganizationInMinistry(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareOrganizationInMinistry(entity1, entity2);
        const compareResult2 = service.compareOrganizationInMinistry(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareOrganizationInMinistry(entity1, entity2);
        const compareResult2 = service.compareOrganizationInMinistry(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
