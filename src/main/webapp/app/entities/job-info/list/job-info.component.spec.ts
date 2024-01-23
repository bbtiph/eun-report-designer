import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { JobInfoService } from '../service/job-info.service';

import { JobInfoComponent } from './job-info.component';

describe('JobInfo Management Component', () => {
  let comp: JobInfoComponent;
  let fixture: ComponentFixture<JobInfoComponent>;
  let service: JobInfoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'job-info', component: JobInfoComponent }]), HttpClientTestingModule],
      declarations: [JobInfoComponent],
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
      .overrideTemplate(JobInfoComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(JobInfoComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(JobInfoService);

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
    expect(comp.jobInfos?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to jobInfoService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getJobInfoIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getJobInfoIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
