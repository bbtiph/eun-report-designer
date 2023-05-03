import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPersonInProject } from '../person-in-project.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../person-in-project.test-samples';

import { PersonInProjectService } from './person-in-project.service';

const requireRestSample: IPersonInProject = {
  ...sampleWithRequiredData,
};

describe('PersonInProject Service', () => {
  let service: PersonInProjectService;
  let httpMock: HttpTestingController;
  let expectedResult: IPersonInProject | IPersonInProject[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PersonInProjectService);
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

    it('should create a PersonInProject', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const personInProject = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(personInProject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PersonInProject', () => {
      const personInProject = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(personInProject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PersonInProject', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PersonInProject', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PersonInProject', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPersonInProjectToCollectionIfMissing', () => {
      it('should add a PersonInProject to an empty array', () => {
        const personInProject: IPersonInProject = sampleWithRequiredData;
        expectedResult = service.addPersonInProjectToCollectionIfMissing([], personInProject);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(personInProject);
      });

      it('should not add a PersonInProject to an array that contains it', () => {
        const personInProject: IPersonInProject = sampleWithRequiredData;
        const personInProjectCollection: IPersonInProject[] = [
          {
            ...personInProject,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPersonInProjectToCollectionIfMissing(personInProjectCollection, personInProject);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PersonInProject to an array that doesn't contain it", () => {
        const personInProject: IPersonInProject = sampleWithRequiredData;
        const personInProjectCollection: IPersonInProject[] = [sampleWithPartialData];
        expectedResult = service.addPersonInProjectToCollectionIfMissing(personInProjectCollection, personInProject);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(personInProject);
      });

      it('should add only unique PersonInProject to an array', () => {
        const personInProjectArray: IPersonInProject[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const personInProjectCollection: IPersonInProject[] = [sampleWithRequiredData];
        expectedResult = service.addPersonInProjectToCollectionIfMissing(personInProjectCollection, ...personInProjectArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const personInProject: IPersonInProject = sampleWithRequiredData;
        const personInProject2: IPersonInProject = sampleWithPartialData;
        expectedResult = service.addPersonInProjectToCollectionIfMissing([], personInProject, personInProject2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(personInProject);
        expect(expectedResult).toContain(personInProject2);
      });

      it('should accept null and undefined values', () => {
        const personInProject: IPersonInProject = sampleWithRequiredData;
        expectedResult = service.addPersonInProjectToCollectionIfMissing([], null, personInProject, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(personInProject);
      });

      it('should return initial array if no PersonInProject is added', () => {
        const personInProjectCollection: IPersonInProject[] = [sampleWithRequiredData];
        expectedResult = service.addPersonInProjectToCollectionIfMissing(personInProjectCollection, undefined, null);
        expect(expectedResult).toEqual(personInProjectCollection);
      });
    });

    describe('comparePersonInProject', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePersonInProject(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePersonInProject(entity1, entity2);
        const compareResult2 = service.comparePersonInProject(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePersonInProject(entity1, entity2);
        const compareResult2 = service.comparePersonInProject(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePersonInProject(entity1, entity2);
        const compareResult2 = service.comparePersonInProject(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
