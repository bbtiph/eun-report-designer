import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEunTeam, NewEunTeam } from '../eun-team.model';

export type PartialUpdateEunTeam = Partial<IEunTeam> & Pick<IEunTeam, 'id'>;

export type EntityResponseType = HttpResponse<IEunTeam>;
export type EntityArrayResponseType = HttpResponse<IEunTeam[]>;

@Injectable({ providedIn: 'root' })
export class EunTeamService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/eun-teams');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(eunTeam: NewEunTeam): Observable<EntityResponseType> {
    return this.http.post<IEunTeam>(this.resourceUrl, eunTeam, { observe: 'response' });
  }

  update(eunTeam: IEunTeam): Observable<EntityResponseType> {
    return this.http.put<IEunTeam>(`${this.resourceUrl}/${this.getEunTeamIdentifier(eunTeam)}`, eunTeam, { observe: 'response' });
  }

  partialUpdate(eunTeam: PartialUpdateEunTeam): Observable<EntityResponseType> {
    return this.http.patch<IEunTeam>(`${this.resourceUrl}/${this.getEunTeamIdentifier(eunTeam)}`, eunTeam, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEunTeam>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEunTeam[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEunTeamIdentifier(eunTeam: Pick<IEunTeam, 'id'>): number {
    return eunTeam.id;
  }

  compareEunTeam(o1: Pick<IEunTeam, 'id'> | null, o2: Pick<IEunTeam, 'id'> | null): boolean {
    return o1 && o2 ? this.getEunTeamIdentifier(o1) === this.getEunTeamIdentifier(o2) : o1 === o2;
  }

  addEunTeamToCollectionIfMissing<Type extends Pick<IEunTeam, 'id'>>(
    eunTeamCollection: Type[],
    ...eunTeamsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const eunTeams: Type[] = eunTeamsToCheck.filter(isPresent);
    if (eunTeams.length > 0) {
      const eunTeamCollectionIdentifiers = eunTeamCollection.map(eunTeamItem => this.getEunTeamIdentifier(eunTeamItem)!);
      const eunTeamsToAdd = eunTeams.filter(eunTeamItem => {
        const eunTeamIdentifier = this.getEunTeamIdentifier(eunTeamItem);
        if (eunTeamCollectionIdentifiers.includes(eunTeamIdentifier)) {
          return false;
        }
        eunTeamCollectionIdentifiers.push(eunTeamIdentifier);
        return true;
      });
      return [...eunTeamsToAdd, ...eunTeamCollection];
    }
    return eunTeamCollection;
  }
}
