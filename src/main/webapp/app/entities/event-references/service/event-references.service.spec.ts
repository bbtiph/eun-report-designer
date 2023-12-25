import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEventReferences } from '../event-references.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../event-references.test-samples';

import { EventReferencesService } from './event-references.service';

const requireRestSample: IEventReferences = {
  ...sampleWithRequiredData,
};

describe('EventReferences Service', () => {
  let service: EventReferencesService;
  let httpMock: HttpTestingController;
  let expectedResult: IEventReferences | IEventReferences[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EventReferencesService);
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

    it('should create a EventReferences', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const eventReferences = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(eventReferences).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EventReferences', () => {
      const eventReferences = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(eventReferences).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EventReferences', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EventReferences', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a EventReferences', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEventReferencesToCollectionIfMissing', () => {
      it('should add a EventReferences to an empty array', () => {
        const eventReferences: IEventReferences = sampleWithRequiredData;
        expectedResult = service.addEventReferencesToCollectionIfMissing([], eventReferences);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(eventReferences);
      });

      it('should not add a EventReferences to an array that contains it', () => {
        const eventReferences: IEventReferences = sampleWithRequiredData;
        const eventReferencesCollection: IEventReferences[] = [
          {
            ...eventReferences,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEventReferencesToCollectionIfMissing(eventReferencesCollection, eventReferences);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EventReferences to an array that doesn't contain it", () => {
        const eventReferences: IEventReferences = sampleWithRequiredData;
        const eventReferencesCollection: IEventReferences[] = [sampleWithPartialData];
        expectedResult = service.addEventReferencesToCollectionIfMissing(eventReferencesCollection, eventReferences);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(eventReferences);
      });

      it('should add only unique EventReferences to an array', () => {
        const eventReferencesArray: IEventReferences[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const eventReferencesCollection: IEventReferences[] = [sampleWithRequiredData];
        expectedResult = service.addEventReferencesToCollectionIfMissing(eventReferencesCollection, ...eventReferencesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const eventReferences: IEventReferences = sampleWithRequiredData;
        const eventReferences2: IEventReferences = sampleWithPartialData;
        expectedResult = service.addEventReferencesToCollectionIfMissing([], eventReferences, eventReferences2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(eventReferences);
        expect(expectedResult).toContain(eventReferences2);
      });

      it('should accept null and undefined values', () => {
        const eventReferences: IEventReferences = sampleWithRequiredData;
        expectedResult = service.addEventReferencesToCollectionIfMissing([], null, eventReferences, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(eventReferences);
      });

      it('should return initial array if no EventReferences is added', () => {
        const eventReferencesCollection: IEventReferences[] = [sampleWithRequiredData];
        expectedResult = service.addEventReferencesToCollectionIfMissing(eventReferencesCollection, undefined, null);
        expect(expectedResult).toEqual(eventReferencesCollection);
      });
    });

    describe('compareEventReferences', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEventReferences(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEventReferences(entity1, entity2);
        const compareResult2 = service.compareEventReferences(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEventReferences(entity1, entity2);
        const compareResult2 = service.compareEventReferences(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEventReferences(entity1, entity2);
        const compareResult2 = service.compareEventReferences(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
