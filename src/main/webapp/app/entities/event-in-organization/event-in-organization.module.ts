import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EventInOrganizationComponent } from './list/event-in-organization.component';
import { EventInOrganizationDetailComponent } from './detail/event-in-organization-detail.component';
import { EventInOrganizationUpdateComponent } from './update/event-in-organization-update.component';
import { EventInOrganizationDeleteDialogComponent } from './delete/event-in-organization-delete-dialog.component';
import { EventInOrganizationRoutingModule } from './route/event-in-organization-routing.module';

@NgModule({
  imports: [SharedModule, EventInOrganizationRoutingModule],
  declarations: [
    EventInOrganizationComponent,
    EventInOrganizationDetailComponent,
    EventInOrganizationUpdateComponent,
    EventInOrganizationDeleteDialogComponent,
  ],
})
export class EventInOrganizationModule {}
