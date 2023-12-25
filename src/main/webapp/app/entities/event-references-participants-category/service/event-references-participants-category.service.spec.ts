import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEventReferencesParticipantsCategory } from '../event-references-participants-category.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../event-references-participants-category.test-samples';

import { EventReferencesParticipantsCategoryService } from './event-references-participants-category.service';

const requireRestSample: IEventReferencesParticipantsCategory = {
  ...sampleWithRequiredData,
};

describe('EventReferencesParticipantsCategory Service', () => {
  let service: EventReferencesParticipantsCategoryService;
  let httpMock: HttpTestingController;
  let expectedResult: IEventReferencesParticipantsCategory | IEventReferencesParticipantsCategory[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EventReferencesParticipantsCategoryService);
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

    it('should create a EventReferencesParticipantsCategory', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const eventReferencesParticipantsCategory = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(eventReferencesParticipantsCategory).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EventReferencesParticipantsCategory', () => {
      const eventReferencesParticipantsCategory = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(eventReferencesParticipantsCategory).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EventReferencesParticipantsCategory', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EventReferencesParticipantsCategory', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a EventReferencesParticipantsCategory', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEventReferencesParticipantsCategoryToCollectionIfMissing', () => {
      it('should add a EventReferencesParticipantsCategory to an empty array', () => {
        const eventReferencesParticipantsCategory: IEventReferencesParticipantsCategory = sampleWithRequiredData;
        expectedResult = service.addEventReferencesParticipantsCategoryToCollectionIfMissing([], eventReferencesParticipantsCategory);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(eventReferencesParticipantsCategory);
      });

      it('should not add a EventReferencesParticipantsCategory to an array that contains it', () => {
        const eventReferencesParticipantsCategory: IEventReferencesParticipantsCategory = sampleWithRequiredData;
        const eventReferencesParticipantsCategoryCollection: IEventReferencesParticipantsCategory[] = [
          {
            ...eventReferencesParticipantsCategory,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEventReferencesParticipantsCategoryToCollectionIfMissing(
          eventReferencesParticipantsCategoryCollection,
          eventReferencesParticipantsCategory
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EventReferencesParticipantsCategory to an array that doesn't contain it", () => {
        const eventReferencesParticipantsCategory: IEventReferencesParticipantsCategory = sampleWithRequiredData;
        const eventReferencesParticipantsCategoryCollection: IEventReferencesParticipantsCategory[] = [sampleWithPartialData];
        expectedResult = service.addEventReferencesParticipantsCategoryToCollectionIfMissing(
          eventReferencesParticipantsCategoryCollection,
          eventReferencesParticipantsCategory
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(eventReferencesParticipantsCategory);
      });

      it('should add only unique EventReferencesParticipantsCategory to an array', () => {
        const eventReferencesParticipantsCategoryArray: IEventReferencesParticipantsCategory[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const eventReferencesParticipantsCategoryCollection: IEventReferencesParticipantsCategory[] = [sampleWithRequiredData];
        expectedResult = service.addEventReferencesParticipantsCategoryToCollectionIfMissing(
          eventReferencesParticipantsCategoryCollection,
          ...eventReferencesParticipantsCategoryArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const eventReferencesParticipantsCategory: IEventReferencesParticipantsCategory = sampleWithRequiredData;
        const eventReferencesParticipantsCategory2: IEventReferencesParticipantsCategory = sampleWithPartialData;
        expectedResult = service.addEventReferencesParticipantsCategoryToCollectionIfMissing(
          [],
          eventReferencesParticipantsCategory,
          eventReferencesParticipantsCategory2
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(eventReferencesParticipantsCategory);
        expect(expectedResult).toContain(eventReferencesParticipantsCategory2);
      });

      it('should accept null and undefined values', () => {
        const eventReferencesParticipantsCategory: IEventReferencesParticipantsCategory = sampleWithRequiredData;
        expectedResult = service.addEventReferencesParticipantsCategoryToCollectionIfMissing(
          [],
          null,
          eventReferencesParticipantsCategory,
          undefined
        );
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(eventReferencesParticipantsCategory);
      });

      it('should return initial array if no EventReferencesParticipantsCategory is added', () => {
        const eventReferencesParticipantsCategoryCollection: IEventReferencesParticipantsCategory[] = [sampleWithRequiredData];
        expectedResult = service.addEventReferencesParticipantsCategoryToCollectionIfMissing(
          eventReferencesParticipantsCategoryCollection,
          undefined,
          null
        );
        expect(expectedResult).toEqual(eventReferencesParticipantsCategoryCollection);
      });
    });

    describe('compareEventReferencesParticipantsCategory', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEventReferencesParticipantsCategory(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEventReferencesParticipantsCategory(entity1, entity2);
        const compareResult2 = service.compareEventReferencesParticipantsCategory(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEventReferencesParticipantsCategory(entity1, entity2);
        const compareResult2 = service.compareEventReferencesParticipantsCategory(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEventReferencesParticipantsCategory(entity1, entity2);
        const compareResult2 = service.compareEventReferencesParticipantsCategory(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
