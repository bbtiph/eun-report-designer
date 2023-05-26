import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MoeContactsComponent } from '../list/moe-contacts.component';
import { MoeContactsDetailComponent } from '../detail/moe-contacts-detail.component';
import { MoeContactsUpdateComponent } from '../update/moe-contacts-update.component';
import { MoeContactsRoutingResolveService } from './moe-contacts-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const moeContactsRoute: Routes = [
  {
    path: '',
    component: MoeContactsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MoeContactsDetailComponent,
    resolve: {
      moeContacts: MoeContactsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MoeContactsUpdateComponent,
    resolve: {
      moeContacts: MoeContactsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MoeContactsUpdateComponent,
    resolve: {
      moeContacts: MoeContactsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(moeContactsRoute)],
  exports: [RouterModule],
})
export class MoeContactsRoutingModule {}
