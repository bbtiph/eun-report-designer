import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IMOEParticipationReferences } from '../moe-participation-references.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../moe-participation-references.test-samples';

import { MOEParticipationReferencesService, RestMOEParticipationReferences } from './moe-participation-references.service';

const requireRestSample: RestMOEParticipationReferences = {
  ...sampleWithRequiredData,
  startDate: sampleWithRequiredData.startDate?.format(DATE_FORMAT),
  endDate: sampleWithRequiredData.endDate?.format(DATE_FORMAT),
  createdDate: sampleWithRequiredData.createdDate?.format(DATE_FORMAT),
  lastModifiedDate: sampleWithRequiredData.lastModifiedDate?.format(DATE_FORMAT),
};

describe('MOEParticipationReferences Service', () => {
  let service: MOEParticipationReferencesService;
  let httpMock: HttpTestingController;
  let expectedResult: IMOEParticipationReferences | IMOEParticipationReferences[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MOEParticipationReferencesService);
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

    it('should create a MOEParticipationReferences', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const mOEParticipationReferences = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(mOEParticipationReferences).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MOEParticipationReferences', () => {
      const mOEParticipationReferences = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(mOEParticipationReferences).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MOEParticipationReferences', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MOEParticipationReferences', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a MOEParticipationReferences', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMOEParticipationReferencesToCollectionIfMissing', () => {
      it('should add a MOEParticipationReferences to an empty array', () => {
        const mOEParticipationReferences: IMOEParticipationReferences = sampleWithRequiredData;
        expectedResult = service.addMOEParticipationReferencesToCollectionIfMissing([], mOEParticipationReferences);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mOEParticipationReferences);
      });

      it('should not add a MOEParticipationReferences to an array that contains it', () => {
        const mOEParticipationReferences: IMOEParticipationReferences = sampleWithRequiredData;
        const mOEParticipationReferencesCollection: IMOEParticipationReferences[] = [
          {
            ...mOEParticipationReferences,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMOEParticipationReferencesToCollectionIfMissing(
          mOEParticipationReferencesCollection,
          mOEParticipationReferences
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MOEParticipationReferences to an array that doesn't contain it", () => {
        const mOEParticipationReferences: IMOEParticipationReferences = sampleWithRequiredData;
        const mOEParticipationReferencesCollection: IMOEParticipationReferences[] = [sampleWithPartialData];
        expectedResult = service.addMOEParticipationReferencesToCollectionIfMissing(
          mOEParticipationReferencesCollection,
          mOEParticipationReferences
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mOEParticipationReferences);
      });

      it('should add only unique MOEParticipationReferences to an array', () => {
        const mOEParticipationReferencesArray: IMOEParticipationReferences[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const mOEParticipationReferencesCollection: IMOEParticipationReferences[] = [sampleWithRequiredData];
        expectedResult = service.addMOEParticipationReferencesToCollectionIfMissing(
          mOEParticipationReferencesCollection,
          ...mOEParticipationReferencesArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const mOEParticipationReferences: IMOEParticipationReferences = sampleWithRequiredData;
        const mOEParticipationReferences2: IMOEParticipationReferences = sampleWithPartialData;
        expectedResult = service.addMOEParticipationReferencesToCollectionIfMissing(
          [],
          mOEParticipationReferences,
          mOEParticipationReferences2
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mOEParticipationReferences);
        expect(expectedResult).toContain(mOEParticipationReferences2);
      });

      it('should accept null and undefined values', () => {
        const mOEParticipationReferences: IMOEParticipationReferences = sampleWithRequiredData;
        expectedResult = service.addMOEParticipationReferencesToCollectionIfMissing([], null, mOEParticipationReferences, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mOEParticipationReferences);
      });

      it('should return initial array if no MOEParticipationReferences is added', () => {
        const mOEParticipationReferencesCollection: IMOEParticipationReferences[] = [sampleWithRequiredData];
        expectedResult = service.addMOEParticipationReferencesToCollectionIfMissing(mOEParticipationReferencesCollection, undefined, null);
        expect(expectedResult).toEqual(mOEParticipationReferencesCollection);
      });
    });

    describe('compareMOEParticipationReferences', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMOEParticipationReferences(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareMOEParticipationReferences(entity1, entity2);
        const compareResult2 = service.compareMOEParticipationReferences(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareMOEParticipationReferences(entity1, entity2);
        const compareResult2 = service.compareMOEParticipationReferences(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareMOEParticipationReferences(entity1, entity2);
        const compareResult2 = service.compareMOEParticipationReferences(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
