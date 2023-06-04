import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IReportBlocksContentData } from '../report-blocks-content-data.model';
import { ReportBlocksContentDataService } from '../service/report-blocks-content-data.service';

import { ReportBlocksContentDataRoutingResolveService } from './report-blocks-content-data-routing-resolve.service';

describe('ReportBlocksContentData routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ReportBlocksContentDataRoutingResolveService;
  let service: ReportBlocksContentDataService;
  let resultReportBlocksContentData: IReportBlocksContentData | null | undefined;

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
    routingResolveService = TestBed.inject(ReportBlocksContentDataRoutingResolveService);
    service = TestBed.inject(ReportBlocksContentDataService);
    resultReportBlocksContentData = undefined;
  });

  describe('resolve', () => {
    it('should return IReportBlocksContentData returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultReportBlocksContentData = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultReportBlocksContentData).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultReportBlocksContentData = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultReportBlocksContentData).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IReportBlocksContentData>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultReportBlocksContentData = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultReportBlocksContentData).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
