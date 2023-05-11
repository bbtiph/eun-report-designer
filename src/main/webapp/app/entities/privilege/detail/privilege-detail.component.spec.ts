import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PrivilegeDetailComponent } from './privilege-detail.component';

describe('Privilege Management Detail Component', () => {
  let comp: PrivilegeDetailComponent;
  let fixture: ComponentFixture<PrivilegeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PrivilegeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ privilege: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PrivilegeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PrivilegeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load privilege on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.privilege).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
