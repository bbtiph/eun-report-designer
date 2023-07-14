import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IReferenceTableSettings } from '../reference-table-settings.model';
import { ReferenceTableSettingsService } from '../service/reference-table-settings.service';

import { ReferenceTableSettingsRoutingResolveService } from './reference-table-settings-routing-resolve.service';

describe('ReferenceTableSettings routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ReferenceTableSettingsRoutingResolveService;
  let service: ReferenceTableSettingsService;
  let resultReferenceTableSettings: IReferenceTableSettings | null | undefined;

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
    routingResolveService = TestBed.inject(ReferenceTableSettingsRoutingResolveService);
    service = TestBed.inject(ReferenceTableSettingsService);
    resultReferenceTableSettings = undefined;
  });

  describe('resolve', () => {
    it('should return IReferenceTableSettings returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultReferenceTableSettings = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultReferenceTableSettings).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultReferenceTableSettings = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultReferenceTableSettings).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IReferenceTableSettings>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultReferenceTableSettings = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultReferenceTableSettings).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
