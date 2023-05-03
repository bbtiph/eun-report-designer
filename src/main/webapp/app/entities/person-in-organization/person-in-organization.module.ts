import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PersonInOrganizationComponent } from './list/person-in-organization.component';
import { PersonInOrganizationDetailComponent } from './detail/person-in-organization-detail.component';
import { PersonInOrganizationUpdateComponent } from './update/person-in-organization-update.component';
import { PersonInOrganizationDeleteDialogComponent } from './delete/person-in-organization-delete-dialog.component';
import { PersonInOrganizationRoutingModule } from './route/person-in-organization-routing.module';

@NgModule({
  imports: [SharedModule, PersonInOrganizationRoutingModule],
  declarations: [
    PersonInOrganizationComponent,
    PersonInOrganizationDetailComponent,
    PersonInOrganizationUpdateComponent,
    PersonInOrganizationDeleteDialogComponent,
  ],
})
export class PersonInOrganizationModule {}
