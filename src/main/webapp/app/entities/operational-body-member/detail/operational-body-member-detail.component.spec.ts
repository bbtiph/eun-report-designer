import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OperationalBodyMemberDetailComponent } from './operational-body-member-detail.component';

describe('OperationalBodyMember Management Detail Component', () => {
  let comp: OperationalBodyMemberDetailComponent;
  let fixture: ComponentFixture<OperationalBodyMemberDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OperationalBodyMemberDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ operationalBodyMember: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(OperationalBodyMemberDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(OperationalBodyMemberDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load operationalBodyMember on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.operationalBodyMember).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
