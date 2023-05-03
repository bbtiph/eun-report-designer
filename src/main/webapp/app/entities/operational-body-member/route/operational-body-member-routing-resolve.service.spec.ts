import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IOperationalBodyMember } from '../operational-body-member.model';
import { OperationalBodyMemberService } from '../service/operational-body-member.service';

import { OperationalBodyMemberRoutingResolveService } from './operational-body-member-routing-resolve.service';

describe('OperationalBodyMember routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: OperationalBodyMemberRoutingResolveService;
  let service: OperationalBodyMemberService;
  let resultOperationalBodyMember: IOperationalBodyMember | null | undefined;

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
    routingResolveService = TestBed.inject(OperationalBodyMemberRoutingResolveService);
    service = TestBed.inject(OperationalBodyMemberService);
    resultOperationalBodyMember = undefined;
  });

  describe('resolve', () => {
    it('should return IOperationalBodyMember returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultOperationalBodyMember = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultOperationalBodyMember).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultOperationalBodyMember = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultOperationalBodyMember).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IOperationalBodyMember>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultOperationalBodyMember = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultOperationalBodyMember).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
