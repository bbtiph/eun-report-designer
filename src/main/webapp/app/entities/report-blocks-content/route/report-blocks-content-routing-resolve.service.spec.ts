import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IReportBlocksContent } from '../report-blocks-content.model';
import { ReportBlocksContentService } from '../service/report-blocks-content.service';

import { ReportBlocksContentRoutingResolveService } from './report-blocks-content-routing-resolve.service';

describe('ReportBlocksContent routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ReportBlocksContentRoutingResolveService;
  let service: ReportBlocksContentService;
  let resultReportBlocksContent: IReportBlocksContent | null | undefined;

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
    routingResolveService = TestBed.inject(ReportBlocksContentRoutingResolveService);
    service = TestBed.inject(ReportBlocksContentService);
    resultReportBlocksContent = undefined;
  });

  describe('resolve', () => {
    it('should return IReportBlocksContent returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultReportBlocksContent = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultReportBlocksContent).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultReportBlocksContent = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultReportBlocksContent).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IReportBlocksContent>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultReportBlocksContent = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultReportBlocksContent).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
