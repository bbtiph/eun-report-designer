import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IOrganizationEunIndicator } from '../organization-eun-indicator.model';
import { OrganizationEunIndicatorService } from '../service/organization-eun-indicator.service';

import { OrganizationEunIndicatorRoutingResolveService } from './organization-eun-indicator-routing-resolve.service';

describe('OrganizationEunIndicator routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: OrganizationEunIndicatorRoutingResolveService;
  let service: OrganizationEunIndicatorService;
  let resultOrganizationEunIndicator: IOrganizationEunIndicator | null | undefined;

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
    routingResolveService = TestBed.inject(OrganizationEunIndicatorRoutingResolveService);
    service = TestBed.inject(OrganizationEunIndicatorService);
    resultOrganizationEunIndicator = undefined;
  });

  describe('resolve', () => {
    it('should return IOrganizationEunIndicator returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultOrganizationEunIndicator = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultOrganizationEunIndicator).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultOrganizationEunIndicator = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultOrganizationEunIndicator).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IOrganizationEunIndicator>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultOrganizationEunIndicator = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultOrganizationEunIndicator).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
