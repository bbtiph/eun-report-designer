import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { MOEParticipationReferencesService } from '../service/moe-participation-references.service';

import { MOEParticipationReferencesComponent } from './moe-participation-references.component';

describe('MOEParticipationReferences Management Component', () => {
  let comp: MOEParticipationReferencesComponent;
  let fixture: ComponentFixture<MOEParticipationReferencesComponent>;
  let service: MOEParticipationReferencesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'moe-participation-references', component: MOEParticipationReferencesComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [MOEParticipationReferencesComponent],
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
      .overrideTemplate(MOEParticipationReferencesComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MOEParticipationReferencesComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(MOEParticipationReferencesService);

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
    expect(comp.mOEParticipationReferences?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to mOEParticipationReferencesService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getMOEParticipationReferencesIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getMOEParticipationReferencesIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
