import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMoeContacts } from '../moe-contacts.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../moe-contacts.test-samples';

import { MoeContactsService } from './moe-contacts.service';

const requireRestSample: IMoeContacts = {
  ...sampleWithRequiredData,
};

describe('MoeContacts Service', () => {
  let service: MoeContactsService;
  let httpMock: HttpTestingController;
  let expectedResult: IMoeContacts | IMoeContacts[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MoeContactsService);
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

    it('should create a MoeContacts', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const moeContacts = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(moeContacts).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MoeContacts', () => {
      const moeContacts = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(moeContacts).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MoeContacts', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MoeContacts', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a MoeContacts', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMoeContactsToCollectionIfMissing', () => {
      it('should add a MoeContacts to an empty array', () => {
        const moeContacts: IMoeContacts = sampleWithRequiredData;
        expectedResult = service.addMoeContactsToCollectionIfMissing([], moeContacts);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(moeContacts);
      });

      it('should not add a MoeContacts to an array that contains it', () => {
        const moeContacts: IMoeContacts = sampleWithRequiredData;
        const moeContactsCollection: IMoeContacts[] = [
          {
            ...moeContacts,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMoeContactsToCollectionIfMissing(moeContactsCollection, moeContacts);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MoeContacts to an array that doesn't contain it", () => {
        const moeContacts: IMoeContacts = sampleWithRequiredData;
        const moeContactsCollection: IMoeContacts[] = [sampleWithPartialData];
        expectedResult = service.addMoeContactsToCollectionIfMissing(moeContactsCollection, moeContacts);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(moeContacts);
      });

      it('should add only unique MoeContacts to an array', () => {
        const moeContactsArray: IMoeContacts[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const moeContactsCollection: IMoeContacts[] = [sampleWithRequiredData];
        expectedResult = service.addMoeContactsToCollectionIfMissing(moeContactsCollection, ...moeContactsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const moeContacts: IMoeContacts = sampleWithRequiredData;
        const moeContacts2: IMoeContacts = sampleWithPartialData;
        expectedResult = service.addMoeContactsToCollectionIfMissing([], moeContacts, moeContacts2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(moeContacts);
        expect(expectedResult).toContain(moeContacts2);
      });

      it('should accept null and undefined values', () => {
        const moeContacts: IMoeContacts = sampleWithRequiredData;
        expectedResult = service.addMoeContactsToCollectionIfMissing([], null, moeContacts, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(moeContacts);
      });

      it('should return initial array if no MoeContacts is added', () => {
        const moeContactsCollection: IMoeContacts[] = [sampleWithRequiredData];
        expectedResult = service.addMoeContactsToCollectionIfMissing(moeContactsCollection, undefined, null);
        expect(expectedResult).toEqual(moeContactsCollection);
      });
    });

    describe('compareMoeContacts', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMoeContacts(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareMoeContacts(entity1, entity2);
        const compareResult2 = service.compareMoeContacts(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareMoeContacts(entity1, entity2);
        const compareResult2 = service.compareMoeContacts(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareMoeContacts(entity1, entity2);
        const compareResult2 = service.compareMoeContacts(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
