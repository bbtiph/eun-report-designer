import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { OrganizationInMinistryComponent } from './list/organization-in-ministry.component';
import { OrganizationInMinistryDetailComponent } from './detail/organization-in-ministry-detail.component';
import { OrganizationInMinistryUpdateComponent } from './update/organization-in-ministry-update.component';
import { OrganizationInMinistryDeleteDialogComponent } from './delete/organization-in-ministry-delete-dialog.component';
import { OrganizationInMinistryRoutingModule } from './route/organization-in-ministry-routing.module';

@NgModule({
  imports: [SharedModule, OrganizationInMinistryRoutingModule],
  declarations: [
    OrganizationInMinistryComponent,
    OrganizationInMinistryDetailComponent,
    OrganizationInMinistryUpdateComponent,
    OrganizationInMinistryDeleteDialogComponent,
  ],
})
export class OrganizationInMinistryModule {}
