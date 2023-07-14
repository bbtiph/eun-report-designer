import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ReferenceTableSettingsService } from '../service/reference-table-settings.service';

import { ReferenceTableSettingsComponent } from './reference-table-settings.component';

describe('ReferenceTableSettings Management Component', () => {
  let comp: ReferenceTableSettingsComponent;
  let fixture: ComponentFixture<ReferenceTableSettingsComponent>;
  let service: ReferenceTableSettingsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'reference-table-settings', component: ReferenceTableSettingsComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [ReferenceTableSettingsComponent],
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
      .overrideTemplate(ReferenceTableSettingsComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ReferenceTableSettingsComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ReferenceTableSettingsService);

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
    expect(comp.referenceTableSettings?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to referenceTableSettingsService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getReferenceTableSettingsIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getReferenceTableSettingsIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
