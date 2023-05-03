import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFunding } from '../funding.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../funding.test-samples';

import { FundingService } from './funding.service';

const requireRestSample: IFunding = {
  ...sampleWithRequiredData,
};

describe('Funding Service', () => {
  let service: FundingService;
  let httpMock: HttpTestingController;
  let expectedResult: IFunding | IFunding[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FundingService);
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

    it('should create a Funding', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const funding = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(funding).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Funding', () => {
      const funding = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(funding).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Funding', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Funding', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Funding', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFundingToCollectionIfMissing', () => {
      it('should add a Funding to an empty array', () => {
        const funding: IFunding = sampleWithRequiredData;
        expectedResult = service.addFundingToCollectionIfMissing([], funding);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(funding);
      });

      it('should not add a Funding to an array that contains it', () => {
        const funding: IFunding = sampleWithRequiredData;
        const fundingCollection: IFunding[] = [
          {
            ...funding,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFundingToCollectionIfMissing(fundingCollection, funding);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Funding to an array that doesn't contain it", () => {
        const funding: IFunding = sampleWithRequiredData;
        const fundingCollection: IFunding[] = [sampleWithPartialData];
        expectedResult = service.addFundingToCollectionIfMissing(fundingCollection, funding);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(funding);
      });

      it('should add only unique Funding to an array', () => {
        const fundingArray: IFunding[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const fundingCollection: IFunding[] = [sampleWithRequiredData];
        expectedResult = service.addFundingToCollectionIfMissing(fundingCollection, ...fundingArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const funding: IFunding = sampleWithRequiredData;
        const funding2: IFunding = sampleWithPartialData;
        expectedResult = service.addFundingToCollectionIfMissing([], funding, funding2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(funding);
        expect(expectedResult).toContain(funding2);
      });

      it('should accept null and undefined values', () => {
        const funding: IFunding = sampleWithRequiredData;
        expectedResult = service.addFundingToCollectionIfMissing([], null, funding, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(funding);
      });

      it('should return initial array if no Funding is added', () => {
        const fundingCollection: IFunding[] = [sampleWithRequiredData];
        expectedResult = service.addFundingToCollectionIfMissing(fundingCollection, undefined, null);
        expect(expectedResult).toEqual(fundingCollection);
      });
    });

    describe('compareFunding', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFunding(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFunding(entity1, entity2);
        const compareResult2 = service.compareFunding(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFunding(entity1, entity2);
        const compareResult2 = service.compareFunding(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFunding(entity1, entity2);
        const compareResult2 = service.compareFunding(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
