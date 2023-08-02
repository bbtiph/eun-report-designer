import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IWorkingGroupReferences } from '../working-group-references.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../working-group-references.test-samples';

import { WorkingGroupReferencesService, RestWorkingGroupReferences } from './working-group-references.service';

const requireRestSample: RestWorkingGroupReferences = {
  ...sampleWithRequiredData,
  countryRepresentativeStartDate: sampleWithRequiredData.countryRepresentativeStartDate?.format(DATE_FORMAT),
  countryRepresentativeEndDate: sampleWithRequiredData.countryRepresentativeEndDate?.format(DATE_FORMAT),
  createdDate: sampleWithRequiredData.createdDate?.format(DATE_FORMAT),
  lastModifiedDate: sampleWithRequiredData.lastModifiedDate?.format(DATE_FORMAT),
};

describe('WorkingGroupReferences Service', () => {
  let service: WorkingGroupReferencesService;
  let httpMock: HttpTestingController;
  let expectedResult: IWorkingGroupReferences | IWorkingGroupReferences[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(WorkingGroupReferencesService);
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

    it('should create a WorkingGroupReferences', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const workingGroupReferences = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(workingGroupReferences).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a WorkingGroupReferences', () => {
      const workingGroupReferences = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(workingGroupReferences).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a WorkingGroupReferences', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of WorkingGroupReferences', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a WorkingGroupReferences', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addWorkingGroupReferencesToCollectionIfMissing', () => {
      it('should add a WorkingGroupReferences to an empty array', () => {
        const workingGroupReferences: IWorkingGroupReferences = sampleWithRequiredData;
        expectedResult = service.addWorkingGroupReferencesToCollectionIfMissing([], workingGroupReferences);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(workingGroupReferences);
      });

      it('should not add a WorkingGroupReferences to an array that contains it', () => {
        const workingGroupReferences: IWorkingGroupReferences = sampleWithRequiredData;
        const workingGroupReferencesCollection: IWorkingGroupReferences[] = [
          {
            ...workingGroupReferences,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addWorkingGroupReferencesToCollectionIfMissing(workingGroupReferencesCollection, workingGroupReferences);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a WorkingGroupReferences to an array that doesn't contain it", () => {
        const workingGroupReferences: IWorkingGroupReferences = sampleWithRequiredData;
        const workingGroupReferencesCollection: IWorkingGroupReferences[] = [sampleWithPartialData];
        expectedResult = service.addWorkingGroupReferencesToCollectionIfMissing(workingGroupReferencesCollection, workingGroupReferences);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(workingGroupReferences);
      });

      it('should add only unique WorkingGroupReferences to an array', () => {
        const workingGroupReferencesArray: IWorkingGroupReferences[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const workingGroupReferencesCollection: IWorkingGroupReferences[] = [sampleWithRequiredData];
        expectedResult = service.addWorkingGroupReferencesToCollectionIfMissing(
          workingGroupReferencesCollection,
          ...workingGroupReferencesArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const workingGroupReferences: IWorkingGroupReferences = sampleWithRequiredData;
        const workingGroupReferences2: IWorkingGroupReferences = sampleWithPartialData;
        expectedResult = service.addWorkingGroupReferencesToCollectionIfMissing([], workingGroupReferences, workingGroupReferences2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(workingGroupReferences);
        expect(expectedResult).toContain(workingGroupReferences2);
      });

      it('should accept null and undefined values', () => {
        const workingGroupReferences: IWorkingGroupReferences = sampleWithRequiredData;
        expectedResult = service.addWorkingGroupReferencesToCollectionIfMissing([], null, workingGroupReferences, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(workingGroupReferences);
      });

      it('should return initial array if no WorkingGroupReferences is added', () => {
        const workingGroupReferencesCollection: IWorkingGroupReferences[] = [sampleWithRequiredData];
        expectedResult = service.addWorkingGroupReferencesToCollectionIfMissing(workingGroupReferencesCollection, undefined, null);
        expect(expectedResult).toEqual(workingGroupReferencesCollection);
      });
    });

    describe('compareWorkingGroupReferences', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareWorkingGroupReferences(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareWorkingGroupReferences(entity1, entity2);
        const compareResult2 = service.compareWorkingGroupReferences(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareWorkingGroupReferences(entity1, entity2);
        const compareResult2 = service.compareWorkingGroupReferences(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareWorkingGroupReferences(entity1, entity2);
        const compareResult2 = service.compareWorkingGroupReferences(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
