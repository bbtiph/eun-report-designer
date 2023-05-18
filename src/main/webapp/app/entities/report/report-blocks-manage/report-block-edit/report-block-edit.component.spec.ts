import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportBlockEditComponent } from './report-block-edit.component';

describe('ReportBlockEditComponent', () => {
  let component: ReportBlockEditComponent;
  let fixture: ComponentFixture<ReportBlockEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ReportBlockEditComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ReportBlockEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
