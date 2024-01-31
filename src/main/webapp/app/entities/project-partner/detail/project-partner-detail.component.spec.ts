import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProjectPartnerDetailComponent } from './project-partner-detail.component';

describe('ProjectPartner Management Detail Component', () => {
  let comp: ProjectPartnerDetailComponent;
  let fixture: ComponentFixture<ProjectPartnerDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProjectPartnerDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ projectPartner: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ProjectPartnerDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProjectPartnerDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load projectPartner on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.projectPartner).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
