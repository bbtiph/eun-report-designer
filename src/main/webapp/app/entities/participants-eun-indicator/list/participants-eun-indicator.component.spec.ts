import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ParticipantsEunIndicatorService } from '../service/participants-eun-indicator.service';

import { ParticipantsEunIndicatorComponent } from './participants-eun-indicator.component';

describe('ParticipantsEunIndicator Management Component', () => {
  let comp: ParticipantsEunIndicatorComponent;
  let fixture: ComponentFixture<ParticipantsEunIndicatorComponent>;
  let service: ParticipantsEunIndicatorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'participants-eun-indicator', component: ParticipantsEunIndicatorComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [ParticipantsEunIndicatorComponent],
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
      .overrideTemplate(ParticipantsEunIndicatorComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ParticipantsEunIndicatorComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ParticipantsEunIndicatorService);

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
    expect(comp.participantsEunIndicators?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to participantsEunIndicatorService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getParticipantsEunIndicatorIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getParticipantsEunIndicatorIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
