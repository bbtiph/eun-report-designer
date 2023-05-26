import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MoeContactsDetailComponent } from './moe-contacts-detail.component';

describe('MoeContacts Management Detail Component', () => {
  let comp: MoeContactsDetailComponent;
  let fixture: ComponentFixture<MoeContactsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MoeContactsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ moeContacts: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(MoeContactsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MoeContactsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load moeContacts on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.moeContacts).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
