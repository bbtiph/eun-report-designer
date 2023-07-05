import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WorkingGroupReferencesDetailComponent } from './working-group-references-detail.component';

describe('WorkingGroupReferences Management Detail Component', () => {
  let comp: WorkingGroupReferencesDetailComponent;
  let fixture: ComponentFixture<WorkingGroupReferencesDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [WorkingGroupReferencesDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ workingGroupReferences: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(WorkingGroupReferencesDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(WorkingGroupReferencesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load workingGroupReferences on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.workingGroupReferences).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
