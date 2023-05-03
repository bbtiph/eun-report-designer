import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PersonInProjectComponent } from './list/person-in-project.component';
import { PersonInProjectDetailComponent } from './detail/person-in-project-detail.component';
import { PersonInProjectUpdateComponent } from './update/person-in-project-update.component';
import { PersonInProjectDeleteDialogComponent } from './delete/person-in-project-delete-dialog.component';
import { PersonInProjectRoutingModule } from './route/person-in-project-routing.module';

@NgModule({
  imports: [SharedModule, PersonInProjectRoutingModule],
  declarations: [
    PersonInProjectComponent,
    PersonInProjectDetailComponent,
    PersonInProjectUpdateComponent,
    PersonInProjectDeleteDialogComponent,
  ],
})
export class PersonInProjectModule {}
