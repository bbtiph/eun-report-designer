import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EventReferencesComponent } from '../list/event-references.component';
import { EventReferencesDetailComponent } from '../detail/event-references-detail.component';
import { EventReferencesUpdateComponent } from '../update/event-references-update.component';
import { EventReferencesRoutingResolveService } from './event-references-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const eventReferencesRoute: Routes = [
  {
    path: '',
    component: EventReferencesComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EventReferencesDetailComponent,
    resolve: {
      eventReferences: EventReferencesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EventReferencesUpdateComponent,
    resolve: {
      eventReferences: EventReferencesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EventReferencesUpdateComponent,
    resolve: {
      eventReferences: EventReferencesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(eventReferencesRoute)],
  exports: [RouterModule],
})
export class EventReferencesRoutingModule {}
