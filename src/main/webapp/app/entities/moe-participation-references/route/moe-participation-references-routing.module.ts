import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MOEParticipationReferencesComponent } from '../list/moe-participation-references.component';
import { MOEParticipationReferencesDetailComponent } from '../detail/moe-participation-references-detail.component';
import { MOEParticipationReferencesUpdateComponent } from '../update/moe-participation-references-update.component';
import { MOEParticipationReferencesRoutingResolveService } from './moe-participation-references-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const mOEParticipationReferencesRoute: Routes = [
  {
    path: '',
    component: MOEParticipationReferencesComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MOEParticipationReferencesDetailComponent,
    resolve: {
      mOEParticipationReferences: MOEParticipationReferencesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MOEParticipationReferencesUpdateComponent,
    resolve: {
      mOEParticipationReferences: MOEParticipationReferencesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MOEParticipationReferencesUpdateComponent,
    resolve: {
      mOEParticipationReferences: MOEParticipationReferencesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(mOEParticipationReferencesRoute)],
  exports: [RouterModule],
})
export class MOEParticipationReferencesRoutingModule {}
