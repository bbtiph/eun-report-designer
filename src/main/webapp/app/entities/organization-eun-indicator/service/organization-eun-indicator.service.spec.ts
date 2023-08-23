import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IOrganizationEunIndicator } from '../organization-eun-indicator.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../organization-eun-indicator.test-samples';

import { OrganizationEunIndicatorService, RestOrganizationEunIndicator } from './organization-eun-indicator.service';

const requireRestSample: RestOrganizationEunIndicator = {
  ...sampleWithRequiredData,
  createdDate: sampleWithRequiredData.createdDate?.format(DATE_FORMAT),
  lastModifiedDate: sampleWithRequiredData.lastModifiedDate?.format(DATE_FORMAT),
};

describe('OrganizationEunIndicator Service', () => {
  let service: OrganizationEunIndicatorService;
  let httpMock: HttpTestingController;
  let expectedResult: IOrganizationEunIndicator | IOrganizationEunIndicator[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(OrganizationEunIndicatorService);
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

    it('should create a OrganizationEunIndicator', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const organizationEunIndicator = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(organizationEunIndicator).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a OrganizationEunIndicator', () => {
      const organizationEunIndicator = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(organizationEunIndicator).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a OrganizationEunIndicator', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of OrganizationEunIndicator', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a OrganizationEunIndicator', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addOrganizationEunIndicatorToCollectionIfMissing', () => {
      it('should add a OrganizationEunIndicator to an empty array', () => {
        const organizationEunIndicator: IOrganizationEunIndicator = sampleWithRequiredData;
        expectedResult = service.addOrganizationEunIndicatorToCollectionIfMissing([], organizationEunIndicator);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(organizationEunIndicator);
      });

      it('should not add a OrganizationEunIndicator to an array that contains it', () => {
        const organizationEunIndicator: IOrganizationEunIndicator = sampleWithRequiredData;
        const organizationEunIndicatorCollection: IOrganizationEunIndicator[] = [
          {
            ...organizationEunIndicator,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addOrganizationEunIndicatorToCollectionIfMissing(
          organizationEunIndicatorCollection,
          organizationEunIndicator
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a OrganizationEunIndicator to an array that doesn't contain it", () => {
        const organizationEunIndicator: IOrganizationEunIndicator = sampleWithRequiredData;
        const organizationEunIndicatorCollection: IOrganizationEunIndicator[] = [sampleWithPartialData];
        expectedResult = service.addOrganizationEunIndicatorToCollectionIfMissing(
          organizationEunIndicatorCollection,
          organizationEunIndicator
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(organizationEunIndicator);
      });

      it('should add only unique OrganizationEunIndicator to an array', () => {
        const organizationEunIndicatorArray: IOrganizationEunIndicator[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const organizationEunIndicatorCollection: IOrganizationEunIndicator[] = [sampleWithRequiredData];
        expectedResult = service.addOrganizationEunIndicatorToCollectionIfMissing(
          organizationEunIndicatorCollection,
          ...organizationEunIndicatorArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const organizationEunIndicator: IOrganizationEunIndicator = sampleWithRequiredData;
        const organizationEunIndicator2: IOrganizationEunIndicator = sampleWithPartialData;
        expectedResult = service.addOrganizationEunIndicatorToCollectionIfMissing([], organizationEunIndicator, organizationEunIndicator2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(organizationEunIndicator);
        expect(expectedResult).toContain(organizationEunIndicator2);
      });

      it('should accept null and undefined values', () => {
        const organizationEunIndicator: IOrganizationEunIndicator = sampleWithRequiredData;
        expectedResult = service.addOrganizationEunIndicatorToCollectionIfMissing([], null, organizationEunIndicator, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(organizationEunIndicator);
      });

      it('should return initial array if no OrganizationEunIndicator is added', () => {
        const organizationEunIndicatorCollection: IOrganizationEunIndicator[] = [sampleWithRequiredData];
        expectedResult = service.addOrganizationEunIndicatorToCollectionIfMissing(organizationEunIndicatorCollection, undefined, null);
        expect(expectedResult).toEqual(organizationEunIndicatorCollection);
      });
    });

    describe('compareOrganizationEunIndicator', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareOrganizationEunIndicator(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareOrganizationEunIndicator(entity1, entity2);
        const compareResult2 = service.compareOrganizationEunIndicator(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareOrganizationEunIndicator(entity1, entity2);
        const compareResult2 = service.compareOrganizationEunIndicator(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareOrganizationEunIndicator(entity1, entity2);
        const compareResult2 = service.compareOrganizationEunIndicator(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
