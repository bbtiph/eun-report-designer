import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EunTeamMemberDetailComponent } from './eun-team-member-detail.component';

describe('EunTeamMember Management Detail Component', () => {
  let comp: EunTeamMemberDetailComponent;
  let fixture: ComponentFixture<EunTeamMemberDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EunTeamMemberDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ eunTeamMember: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EunTeamMemberDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EunTeamMemberDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load eunTeamMember on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.eunTeamMember).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
