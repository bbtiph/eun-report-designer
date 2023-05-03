import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EunTeamDetailComponent } from './eun-team-detail.component';

describe('EunTeam Management Detail Component', () => {
  let comp: EunTeamDetailComponent;
  let fixture: ComponentFixture<EunTeamDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EunTeamDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ eunTeam: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EunTeamDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EunTeamDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load eunTeam on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.eunTeam).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
