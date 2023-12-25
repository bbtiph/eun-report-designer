import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { EventReferencesService } from '../service/event-references.service';

import { EventReferencesComponent } from './event-references.component';

describe('EventReferences Management Component', () => {
  let comp: EventReferencesComponent;
  let fixture: ComponentFixture<EventReferencesComponent>;
  let service: EventReferencesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'event-references', component: EventReferencesComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [EventReferencesComponent],
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
      .overrideTemplate(EventReferencesComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EventReferencesComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(EventReferencesService);

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
    expect(comp.eventReferences?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to eventReferencesService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getEventReferencesIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getEventReferencesIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
