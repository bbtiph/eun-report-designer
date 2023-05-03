import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EventInOrganizationDetailComponent } from './event-in-organization-detail.component';

describe('EventInOrganization Management Detail Component', () => {
  let comp: EventInOrganizationDetailComponent;
  let fixture: ComponentFixture<EventInOrganizationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EventInOrganizationDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ eventInOrganization: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EventInOrganizationDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EventInOrganizationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load eventInOrganization on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.eventInOrganization).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
