import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportBlocksManageComponent } from './report-blocks-manage.component';

describe('ReportBlocksManageComponent', () => {
  let component: ReportBlocksManageComponent;
  let fixture: ComponentFixture<ReportBlocksManageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ReportBlocksManageComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ReportBlocksManageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
