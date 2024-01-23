import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JobInfoDetailComponent } from './job-info-detail.component';

describe('JobInfo Management Detail Component', () => {
  let comp: JobInfoDetailComponent;
  let fixture: ComponentFixture<JobInfoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [JobInfoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ jobInfo: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(JobInfoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(JobInfoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load jobInfo on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.jobInfo).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
