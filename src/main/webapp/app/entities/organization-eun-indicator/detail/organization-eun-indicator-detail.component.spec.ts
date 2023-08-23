import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OrganizationEunIndicatorDetailComponent } from './organization-eun-indicator-detail.component';

describe('OrganizationEunIndicator Management Detail Component', () => {
  let comp: OrganizationEunIndicatorDetailComponent;
  let fixture: ComponentFixture<OrganizationEunIndicatorDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OrganizationEunIndicatorDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ organizationEunIndicator: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(OrganizationEunIndicatorDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(OrganizationEunIndicatorDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load organizationEunIndicator on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.organizationEunIndicator).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
