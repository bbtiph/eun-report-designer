import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OperationalBodyDetailComponent } from './operational-body-detail.component';

describe('OperationalBody Management Detail Component', () => {
  let comp: OperationalBodyDetailComponent;
  let fixture: ComponentFixture<OperationalBodyDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OperationalBodyDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ operationalBody: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(OperationalBodyDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(OperationalBodyDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load operationalBody on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.operationalBody).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
