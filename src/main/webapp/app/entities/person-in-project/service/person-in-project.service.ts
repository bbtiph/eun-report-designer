import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPersonInProject, NewPersonInProject } from '../person-in-project.model';

export type PartialUpdatePersonInProject = Partial<IPersonInProject> & Pick<IPersonInProject, 'id'>;

export type EntityResponseType = HttpResponse<IPersonInProject>;
export type EntityArrayResponseType = HttpResponse<IPersonInProject[]>;

@Injectable({ providedIn: 'root' })
export class PersonInProjectService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/person-in-projects');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(personInProject: NewPersonInProject): Observable<EntityResponseType> {
    return this.http.post<IPersonInProject>(this.resourceUrl, personInProject, { observe: 'response' });
  }

  update(personInProject: IPersonInProject): Observable<EntityResponseType> {
    return this.http.put<IPersonInProject>(`${this.resourceUrl}/${this.getPersonInProjectIdentifier(personInProject)}`, personInProject, {
      observe: 'response',
    });
  }

  partialUpdate(personInProject: PartialUpdatePersonInProject): Observable<EntityResponseType> {
    return this.http.patch<IPersonInProject>(`${this.resourceUrl}/${this.getPersonInProjectIdentifier(personInProject)}`, personInProject, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPersonInProject>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPersonInProject[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPersonInProjectIdentifier(personInProject: Pick<IPersonInProject, 'id'>): number {
    return personInProject.id;
  }

  comparePersonInProject(o1: Pick<IPersonInProject, 'id'> | null, o2: Pick<IPersonInProject, 'id'> | null): boolean {
    return o1 && o2 ? this.getPersonInProjectIdentifier(o1) === this.getPersonInProjectIdentifier(o2) : o1 === o2;
  }

  addPersonInProjectToCollectionIfMissing<Type extends Pick<IPersonInProject, 'id'>>(
    personInProjectCollection: Type[],
    ...personInProjectsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const personInProjects: Type[] = personInProjectsToCheck.filter(isPresent);
    if (personInProjects.length > 0) {
      const personInProjectCollectionIdentifiers = personInProjectCollection.map(
        personInProjectItem => this.getPersonInProjectIdentifier(personInProjectItem)!
      );
      const personInProjectsToAdd = personInProjects.filter(personInProjectItem => {
        const personInProjectIdentifier = this.getPersonInProjectIdentifier(personInProjectItem);
        if (personInProjectCollectionIdentifiers.includes(personInProjectIdentifier)) {
          return false;
        }
        personInProjectCollectionIdentifiers.push(personInProjectIdentifier);
        return true;
      });
      return [...personInProjectsToAdd, ...personInProjectCollection];
    }
    return personInProjectCollection;
  }
}
