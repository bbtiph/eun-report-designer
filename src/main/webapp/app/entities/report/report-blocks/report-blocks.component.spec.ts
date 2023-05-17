import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportBlocksComponent } from './report-blocks.component';

describe('ReportBlocksComponent', () => {
  let component: ReportBlocksComponent;
  let fixture: ComponentFixture<ReportBlocksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ReportBlocksComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ReportBlocksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
