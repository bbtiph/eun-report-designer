import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { OrganizationEunIndicatorComponent } from './list/organization-eun-indicator.component';
import { OrganizationEunIndicatorDetailComponent } from './detail/organization-eun-indicator-detail.component';
import { OrganizationEunIndicatorUpdateComponent } from './update/organization-eun-indicator-update.component';
import { OrganizationEunIndicatorDeleteDialogComponent } from './delete/organization-eun-indicator-delete-dialog.component';
import { OrganizationEunIndicatorRoutingModule } from './route/organization-eun-indicator-routing.module';

@NgModule({
  imports: [SharedModule, OrganizationEunIndicatorRoutingModule],
  declarations: [
    OrganizationEunIndicatorComponent,
    OrganizationEunIndicatorDetailComponent,
    OrganizationEunIndicatorUpdateComponent,
    OrganizationEunIndicatorDeleteDialogComponent,
  ],
})
export class OrganizationEunIndicatorModule {}
