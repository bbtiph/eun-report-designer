import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ReferenceTableSettingsDetailComponent } from './reference-table-settings-detail.component';

describe('ReferenceTableSettings Management Detail Component', () => {
  let comp: ReferenceTableSettingsDetailComponent;
  let fixture: ComponentFixture<ReferenceTableSettingsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ReferenceTableSettingsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ referenceTableSettings: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ReferenceTableSettingsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ReferenceTableSettingsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load referenceTableSettings on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.referenceTableSettings).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
