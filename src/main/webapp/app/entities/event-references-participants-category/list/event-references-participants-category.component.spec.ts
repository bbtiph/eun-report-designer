import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { EventReferencesParticipantsCategoryService } from '../service/event-references-participants-category.service';

import { EventReferencesParticipantsCategoryComponent } from './event-references-participants-category.component';

describe('EventReferencesParticipantsCategory Management Component', () => {
  let comp: EventReferencesParticipantsCategoryComponent;
  let fixture: ComponentFixture<EventReferencesParticipantsCategoryComponent>;
  let service: EventReferencesParticipantsCategoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([
          { path: 'event-references-participants-category', component: EventReferencesParticipantsCategoryComponent },
        ]),
        HttpClientTestingModule,
      ],
      declarations: [EventReferencesParticipantsCategoryComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(EventReferencesParticipantsCategoryComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EventReferencesParticipantsCategoryComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(EventReferencesParticipantsCategoryService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.eventReferencesParticipantsCategories?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to eventReferencesParticipantsCategoryService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getEventReferencesParticipantsCategoryIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getEventReferencesParticipantsCategoryIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
