import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IOrganizationInMinistry } from '../organization-in-ministry.model';
import { OrganizationInMinistryService } from '../service/organization-in-ministry.service';

import { OrganizationInMinistryRoutingResolveService } from './organization-in-ministry-routing-resolve.service';

describe('OrganizationInMinistry routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: OrganizationInMinistryRoutingResolveService;
  let service: OrganizationInMinistryService;
  let resultOrganizationInMinistry: IOrganizationInMinistry | null | undefined;

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
    routingResolveService = TestBed.inject(OrganizationInMinistryRoutingResolveService);
    service = TestBed.inject(OrganizationInMinistryService);
    resultOrganizationInMinistry = undefined;
  });

  describe('resolve', () => {
    it('should return IOrganizationInMinistry returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultOrganizationInMinistry = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultOrganizationInMinistry).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultOrganizationInMinistry = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultOrganizationInMinistry).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IOrganizationInMinistry>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultOrganizationInMinistry = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultOrganizationInMinistry).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
