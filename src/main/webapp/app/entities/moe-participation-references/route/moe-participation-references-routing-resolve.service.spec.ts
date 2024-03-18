import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IMOEParticipationReferences } from '../moe-participation-references.model';
import { MOEParticipationReferencesService } from '../service/moe-participation-references.service';

import { MOEParticipationReferencesRoutingResolveService } from './moe-participation-references-routing-resolve.service';

describe('MOEParticipationReferences routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: MOEParticipationReferencesRoutingResolveService;
  let service: MOEParticipationReferencesService;
  let resultMOEParticipationReferences: IMOEParticipationReferences | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(MOEParticipationReferencesRoutingResolveService);
    service = TestBed.inject(MOEParticipationReferencesService);
    resultMOEParticipationReferences = undefined;
  });

  describe('resolve', () => {
    it('should return IMOEParticipationReferences returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMOEParticipationReferences = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultMOEParticipationReferences).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMOEParticipationReferences = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultMOEParticipationReferences).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IMOEParticipationReferences>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMOEParticipationReferences = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultMOEParticipationReferences).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
