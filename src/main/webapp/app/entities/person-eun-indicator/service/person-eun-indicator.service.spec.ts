import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IPersonEunIndicator } from '../person-eun-indicator.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../person-eun-indicator.test-samples';

import { PersonEunIndicatorService, RestPersonEunIndicator } from './person-eun-indicator.service';

const requireRestSample: RestPersonEunIndicator = {
  ...sampleWithRequiredData,
  createdDate: sampleWithRequiredData.createdDate?.format(DATE_FORMAT),
  lastModifiedDate: sampleWithRequiredData.lastModifiedDate?.format(DATE_FORMAT),
};

describe('PersonEunIndicator Service', () => {
  let service: PersonEunIndicatorService;
  let httpMock: HttpTestingController;
  let expectedResult: IPersonEunIndicator | IPersonEunIndicator[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PersonEunIndicatorService);
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

    it('should create a PersonEunIndicator', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const personEunIndicator = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(personEunIndicator).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PersonEunIndicator', () => {
      const personEunIndicator = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(personEunIndicator).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PersonEunIndicator', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PersonEunIndicator', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PersonEunIndicator', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPersonEunIndicatorToCollectionIfMissing', () => {
      it('should add a PersonEunIndicator to an empty array', () => {
        const personEunIndicator: IPersonEunIndicator = sampleWithRequiredData;
        expectedResult = service.addPersonEunIndicatorToCollectionIfMissing([], personEunIndicator);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(personEunIndicator);
      });

      it('should not add a PersonEunIndicator to an array that contains it', () => {
        const personEunIndicator: IPersonEunIndicator = sampleWithRequiredData;
        const personEunIndicatorCollection: IPersonEunIndicator[] = [
          {
            ...personEunIndicator,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPersonEunIndicatorToCollectionIfMissing(personEunIndicatorCollection, personEunIndicator);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PersonEunIndicator to an array that doesn't contain it", () => {
        const personEunIndicator: IPersonEunIndicator = sampleWithRequiredData;
        const personEunIndicatorCollection: IPersonEunIndicator[] = [sampleWithPartialData];
        expectedResult = service.addPersonEunIndicatorToCollectionIfMissing(personEunIndicatorCollection, personEunIndicator);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(personEunIndicator);
      });

      it('should add only unique PersonEunIndicator to an array', () => {
        const personEunIndicatorArray: IPersonEunIndicator[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const personEunIndicatorCollection: IPersonEunIndicator[] = [sampleWithRequiredData];
        expectedResult = service.addPersonEunIndicatorToCollectionIfMissing(personEunIndicatorCollection, ...personEunIndicatorArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const personEunIndicator: IPersonEunIndicator = sampleWithRequiredData;
        const personEunIndicator2: IPersonEunIndicator = sampleWithPartialData;
        expectedResult = service.addPersonEunIndicatorToCollectionIfMissing([], personEunIndicator, personEunIndicator2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(personEunIndicator);
        expect(expectedResult).toContain(personEunIndicator2);
      });

      it('should accept null and undefined values', () => {
        const personEunIndicator: IPersonEunIndicator = sampleWithRequiredData;
        expectedResult = service.addPersonEunIndicatorToCollectionIfMissing([], null, personEunIndicator, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(personEunIndicator);
      });

      it('should return initial array if no PersonEunIndicator is added', () => {
        const personEunIndicatorCollection: IPersonEunIndicator[] = [sampleWithRequiredData];
        expectedResult = service.addPersonEunIndicatorToCollectionIfMissing(personEunIndicatorCollection, undefined, null);
        expect(expectedResult).toEqual(personEunIndicatorCollection);
      });
    });

    describe('comparePersonEunIndicator', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePersonEunIndicator(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePersonEunIndicator(entity1, entity2);
        const compareResult2 = service.comparePersonEunIndicator(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePersonEunIndicator(entity1, entity2);
        const compareResult2 = service.comparePersonEunIndicator(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePersonEunIndicator(entity1, entity2);
        const compareResult2 = service.comparePersonEunIndicator(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
