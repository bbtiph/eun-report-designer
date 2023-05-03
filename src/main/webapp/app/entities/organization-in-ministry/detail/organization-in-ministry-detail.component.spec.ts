import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OrganizationInMinistryDetailComponent } from './organization-in-ministry-detail.component';

describe('OrganizationInMinistry Management Detail Component', () => {
  let comp: OrganizationInMinistryDetailComponent;
  let fixture: ComponentFixture<OrganizationInMinistryDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OrganizationInMinistryDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ organizationInMinistry: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(OrganizationInMinistryDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(OrganizationInMinistryDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load organizationInMinistry on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.organizationInMinistry).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
