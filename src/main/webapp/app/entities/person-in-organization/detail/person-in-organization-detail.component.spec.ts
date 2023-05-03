import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PersonInOrganizationDetailComponent } from './person-in-organization-detail.component';

describe('PersonInOrganization Management Detail Component', () => {
  let comp: PersonInOrganizationDetailComponent;
  let fixture: ComponentFixture<PersonInOrganizationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PersonInOrganizationDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ personInOrganization: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PersonInOrganizationDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PersonInOrganizationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load personInOrganization on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.personInOrganization).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
