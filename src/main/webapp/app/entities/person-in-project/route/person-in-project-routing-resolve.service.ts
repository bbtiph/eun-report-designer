import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPersonInProject } from '../person-in-project.model';
import { PersonInProjectService } from '../service/person-in-project.service';

@Injectable({ providedIn: 'root' })
export class PersonInProjectRoutingResolveService implements Resolve<IPersonInProject | null> {
  constructor(protected service: PersonInProjectService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPersonInProject | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((personInProject: HttpResponse<IPersonInProject>) => {
          if (personInProject.body) {
            return of(personInProject.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
