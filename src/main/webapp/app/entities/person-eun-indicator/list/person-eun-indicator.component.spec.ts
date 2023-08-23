import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PersonEunIndicatorService } from '../service/person-eun-indicator.service';

import { PersonEunIndicatorComponent } from './person-eun-indicator.component';

describe('PersonEunIndicator Management Component', () => {
  let comp: PersonEunIndicatorComponent;
  let fixture: ComponentFixture<PersonEunIndicatorComponent>;
  let service: PersonEunIndicatorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'person-eun-indicator', component: PersonEunIndicatorComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [PersonEunIndicatorComponent],
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
      .overrideTemplate(PersonEunIndicatorComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PersonEunIndicatorComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PersonEunIndicatorService);

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
    expect(comp.personEunIndicators?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to personEunIndicatorService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getPersonEunIndicatorIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getPersonEunIndicatorIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
