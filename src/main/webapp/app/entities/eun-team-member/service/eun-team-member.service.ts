import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEunTeamMember, NewEunTeamMember } from '../eun-team-member.model';

export type PartialUpdateEunTeamMember = Partial<IEunTeamMember> & Pick<IEunTeamMember, 'id'>;

export type EntityResponseType = HttpResponse<IEunTeamMember>;
export type EntityArrayResponseType = HttpResponse<IEunTeamMember[]>;

@Injectable({ providedIn: 'root' })
export class EunTeamMemberService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/eun-team-members');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(eunTeamMember: NewEunTeamMember): Observable<EntityResponseType> {
    return this.http.post<IEunTeamMember>(this.resourceUrl, eunTeamMember, { observe: 'response' });
  }

  update(eunTeamMember: IEunTeamMember): Observable<EntityResponseType> {
    return this.http.put<IEunTeamMember>(`${this.resourceUrl}/${this.getEunTeamMemberIdentifier(eunTeamMember)}`, eunTeamMember, {
      observe: 'response',
    });
  }

  partialUpdate(eunTeamMember: PartialUpdateEunTeamMember): Observable<EntityResponseType> {
    return this.http.patch<IEunTeamMember>(`${this.resourceUrl}/${this.getEunTeamMemberIdentifier(eunTeamMember)}`, eunTeamMember, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEunTeamMember>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEunTeamMember[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEunTeamMemberIdentifier(eunTeamMember: Pick<IEunTeamMember, 'id'>): number {
    return eunTeamMember.id;
  }

  compareEunTeamMember(o1: Pick<IEunTeamMember, 'id'> | null, o2: Pick<IEunTeamMember, 'id'> | null): boolean {
    return o1 && o2 ? this.getEunTeamMemberIdentifier(o1) === this.getEunTeamMemberIdentifier(o2) : o1 === o2;
  }

  addEunTeamMemberToCollectionIfMissing<Type extends Pick<IEunTeamMember, 'id'>>(
    eunTeamMemberCollection: Type[],
    ...eunTeamMembersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const eunTeamMembers: Type[] = eunTeamMembersToCheck.filter(isPresent);
    if (eunTeamMembers.length > 0) {
      const eunTeamMemberCollectionIdentifiers = eunTeamMemberCollection.map(
        eunTeamMemberItem => this.getEunTeamMemberIdentifier(eunTeamMemberItem)!
      );
      const eunTeamMembersToAdd = eunTeamMembers.filter(eunTeamMemberItem => {
        const eunTeamMemberIdentifier = this.getEunTeamMemberIdentifier(eunTeamMemberItem);
        if (eunTeamMemberCollectionIdentifiers.includes(eunTeamMemberIdentifier)) {
          return false;
        }
        eunTeamMemberCollectionIdentifiers.push(eunTeamMemberIdentifier);
        return true;
      });
      return [...eunTeamMembersToAdd, ...eunTeamMemberCollection];
    }
    return eunTeamMemberCollection;
  }
}
