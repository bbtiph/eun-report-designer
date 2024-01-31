import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ProjectPartnerService } from '../service/project-partner.service';

import { ProjectPartnerComponent } from './project-partner.component';

describe('ProjectPartner Management Component', () => {
  let comp: ProjectPartnerComponent;
  let fixture: ComponentFixture<ProjectPartnerComponent>;
  let service: ProjectPartnerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'project-partner', component: ProjectPartnerComponent }]), HttpClientTestingModule],
      declarations: [ProjectPartnerComponent],
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
      .overrideTemplate(ProjectPartnerComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProjectPartnerComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ProjectPartnerService);

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
    expect(comp.projectPartners?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to projectPartnerService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getProjectPartnerIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getProjectPartnerIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
