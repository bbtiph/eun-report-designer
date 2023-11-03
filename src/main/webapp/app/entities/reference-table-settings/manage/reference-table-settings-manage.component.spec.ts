import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReferenceTableSettingsManageComponent } from './reference-table-settings-manage.component';

describe('ReferenceTableSettingsManageComponent', () => {
  let component: ReferenceTableSettingsManageComponent;
  let fixture: ComponentFixture<ReferenceTableSettingsManageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ReferenceTableSettingsManageComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ReferenceTableSettingsManageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
