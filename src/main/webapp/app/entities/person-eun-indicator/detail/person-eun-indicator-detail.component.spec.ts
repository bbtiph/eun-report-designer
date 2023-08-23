import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PersonEunIndicatorDetailComponent } from './person-eun-indicator-detail.component';

describe('PersonEunIndicator Management Detail Component', () => {
  let comp: PersonEunIndicatorDetailComponent;
  let fixture: ComponentFixture<PersonEunIndicatorDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PersonEunIndicatorDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ personEunIndicator: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PersonEunIndicatorDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PersonEunIndicatorDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load personEunIndicator on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.personEunIndicator).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
