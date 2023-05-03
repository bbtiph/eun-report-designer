import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { OrganizationInProjectComponent } from './list/organization-in-project.component';
import { OrganizationInProjectDetailComponent } from './detail/organization-in-project-detail.component';
import { OrganizationInProjectUpdateComponent } from './update/organization-in-project-update.component';
import { OrganizationInProjectDeleteDialogComponent } from './delete/organization-in-project-delete-dialog.component';
import { OrganizationInProjectRoutingModule } from './route/organization-in-project-routing.module';

@NgModule({
  imports: [SharedModule, OrganizationInProjectRoutingModule],
  declarations: [
    OrganizationInProjectComponent,
    OrganizationInProjectDetailComponent,
    OrganizationInProjectUpdateComponent,
    OrganizationInProjectDeleteDialogComponent,
  ],
})
export class OrganizationInProjectModule {}
