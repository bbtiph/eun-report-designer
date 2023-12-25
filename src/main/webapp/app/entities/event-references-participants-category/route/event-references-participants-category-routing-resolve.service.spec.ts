import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IEventReferencesParticipantsCategory } from '../event-references-participants-category.model';
import { EventReferencesParticipantsCategoryService } from '../service/event-references-participants-category.service';

import { EventReferencesParticipantsCategoryRoutingResolveService } from './event-references-participants-category-routing-resolve.service';

describe('EventReferencesParticipantsCategory routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: EventReferencesParticipantsCategoryRoutingResolveService;
  let service: EventReferencesParticipantsCategoryService;
  let resultEventReferencesParticipantsCategory: IEventReferencesParticipantsCategory | null | undefined;

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
    routingResolveService = TestBed.inject(EventReferencesParticipantsCategoryRoutingResolveService);
    service = TestBed.inject(EventReferencesParticipantsCategoryService);
    resultEventReferencesParticipantsCategory = undefined;
  });

  describe('resolve', () => {
    it('should return IEventReferencesParticipantsCategory returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEventReferencesParticipantsCategory = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEventReferencesParticipantsCategory).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEventReferencesParticipantsCategory = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultEventReferencesParticipantsCategory).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IEventReferencesParticipantsCategory>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEventReferencesParticipantsCategory = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEventReferencesParticipantsCategory).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
