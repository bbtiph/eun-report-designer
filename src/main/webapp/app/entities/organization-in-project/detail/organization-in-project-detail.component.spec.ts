import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OrganizationInProjectDetailComponent } from './organization-in-project-detail.component';

describe('OrganizationInProject Management Detail Component', () => {
  let comp: OrganizationInProjectDetailComponent;
  let fixture: ComponentFixture<OrganizationInProjectDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OrganizationInProjectDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ organizationInProject: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(OrganizationInProjectDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(OrganizationInProjectDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load organizationInProject on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.organizationInProject).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
