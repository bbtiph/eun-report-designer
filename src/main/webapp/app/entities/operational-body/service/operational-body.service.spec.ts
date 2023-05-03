import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IOperationalBody } from '../operational-body.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../operational-body.test-samples';

import { OperationalBodyService } from './operational-body.service';

const requireRestSample: IOperationalBody = {
  ...sampleWithRequiredData,
};

describe('OperationalBody Service', () => {
  let service: OperationalBodyService;
  let httpMock: HttpTestingController;
  let expectedResult: IOperationalBody | IOperationalBody[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(OperationalBodyService);
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

    it('should create a OperationalBody', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const operationalBody = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(operationalBody).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a OperationalBody', () => {
      const operationalBody = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(operationalBody).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a OperationalBody', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of OperationalBody', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a OperationalBody', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addOperationalBodyToCollectionIfMissing', () => {
      it('should add a OperationalBody to an empty array', () => {
        const operationalBody: IOperationalBody = sampleWithRequiredData;
        expectedResult = service.addOperationalBodyToCollectionIfMissing([], operationalBody);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(operationalBody);
      });

      it('should not add a OperationalBody to an array that contains it', () => {
        const operationalBody: IOperationalBody = sampleWithRequiredData;
        const operationalBodyCollection: IOperationalBody[] = [
          {
            ...operationalBody,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addOperationalBodyToCollectionIfMissing(operationalBodyCollection, operationalBody);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a OperationalBody to an array that doesn't contain it", () => {
        const operationalBody: IOperationalBody = sampleWithRequiredData;
        const operationalBodyCollection: IOperationalBody[] = [sampleWithPartialData];
        expectedResult = service.addOperationalBodyToCollectionIfMissing(operationalBodyCollection, operationalBody);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(operationalBody);
      });

      it('should add only unique OperationalBody to an array', () => {
        const operationalBodyArray: IOperationalBody[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const operationalBodyCollection: IOperationalBody[] = [sampleWithRequiredData];
        expectedResult = service.addOperationalBodyToCollectionIfMissing(operationalBodyCollection, ...operationalBodyArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const operationalBody: IOperationalBody = sampleWithRequiredData;
        const operationalBody2: IOperationalBody = sampleWithPartialData;
        expectedResult = service.addOperationalBodyToCollectionIfMissing([], operationalBody, operationalBody2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(operationalBody);
        expect(expectedResult).toContain(operationalBody2);
      });

      it('should accept null and undefined values', () => {
        const operationalBody: IOperationalBody = sampleWithRequiredData;
        expectedResult = service.addOperationalBodyToCollectionIfMissing([], null, operationalBody, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(operationalBody);
      });

      it('should return initial array if no OperationalBody is added', () => {
        const operationalBodyCollection: IOperationalBody[] = [sampleWithRequiredData];
        expectedResult = service.addOperationalBodyToCollectionIfMissing(operationalBodyCollection, undefined, null);
        expect(expectedResult).toEqual(operationalBodyCollection);
      });
    });

    describe('compareOperationalBody', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareOperationalBody(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareOperationalBody(entity1, entity2);
        const compareResult2 = service.compareOperationalBody(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareOperationalBody(entity1, entity2);
        const compareResult2 = service.compareOperationalBody(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareOperationalBody(entity1, entity2);
        const compareResult2 = service.compareOperationalBody(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
