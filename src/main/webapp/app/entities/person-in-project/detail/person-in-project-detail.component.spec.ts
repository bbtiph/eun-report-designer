import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PersonInProjectDetailComponent } from './person-in-project-detail.component';

describe('PersonInProject Management Detail Component', () => {
  let comp: PersonInProjectDetailComponent;
  let fixture: ComponentFixture<PersonInProjectDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PersonInProjectDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ personInProject: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PersonInProjectDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PersonInProjectDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load personInProject on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.personInProject).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
