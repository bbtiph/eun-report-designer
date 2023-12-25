import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EventReferencesParticipantsCategoryComponent } from '../list/event-references-participants-category.component';
import { EventReferencesParticipantsCategoryDetailComponent } from '../detail/event-references-participants-category-detail.component';
import { EventReferencesParticipantsCategoryUpdateComponent } from '../update/event-references-participants-category-update.component';
import { EventReferencesParticipantsCategoryRoutingResolveService } from './event-references-participants-category-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const eventReferencesParticipantsCategoryRoute: Routes = [
  {
    path: '',
    component: EventReferencesParticipantsCategoryComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EventReferencesParticipantsCategoryDetailComponent,
    resolve: {
      eventReferencesParticipantsCategory: EventReferencesParticipantsCategoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EventReferencesParticipantsCategoryUpdateComponent,
    resolve: {
      eventReferencesParticipantsCategory: EventReferencesParticipantsCategoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EventReferencesParticipantsCategoryUpdateComponent,
    resolve: {
      eventReferencesParticipantsCategory: EventReferencesParticipantsCategoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(eventReferencesParticipantsCategoryRoute)],
  exports: [RouterModule],
})
export class EventReferencesParticipantsCategoryRoutingModule {}
