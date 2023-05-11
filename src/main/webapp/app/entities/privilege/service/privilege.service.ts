import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPrivilege, NewPrivilege } from '../privilege.model';

export type PartialUpdatePrivilege = Partial<IPrivilege> & Pick<IPrivilege, 'id'>;

export type EntityResponseType = HttpResponse<IPrivilege>;
export type EntityArrayResponseType = HttpResponse<IPrivilege[]>;

@Injectable({ providedIn: 'root' })
export class PrivilegeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/privileges');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(privilege: NewPrivilege): Observable<EntityResponseType> {
    return this.http.post<IPrivilege>(this.resourceUrl, privilege, { observe: 'response' });
  }

  update(privilege: IPrivilege): Observable<EntityResponseType> {
    return this.http.put<IPrivilege>(`${this.resourceUrl}/${this.getPrivilegeIdentifier(privilege)}`, privilege, { observe: 'response' });
  }

  partialUpdate(privilege: PartialUpdatePrivilege): Observable<EntityResponseType> {
    return this.http.patch<IPrivilege>(`${this.resourceUrl}/${this.getPrivilegeIdentifier(privilege)}`, privilege, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPrivilege>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPrivilege[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPrivilegeIdentifier(privilege: Pick<IPrivilege, 'id'>): number {
    return privilege.id;
  }

  comparePrivilege(o1: Pick<IPrivilege, 'id'> | null, o2: Pick<IPrivilege, 'id'> | null): boolean {
    return o1 && o2 ? this.getPrivilegeIdentifier(o1) === this.getPrivilegeIdentifier(o2) : o1 === o2;
  }

  addPrivilegeToCollectionIfMissing<Type extends Pick<IPrivilege, 'id'>>(
    privilegeCollection: Type[],
    ...privilegesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const privileges: Type[] = privilegesToCheck.filter(isPresent);
    if (privileges.length > 0) {
      const privilegeCollectionIdentifiers = privilegeCollection.map(privilegeItem => this.getPrivilegeIdentifier(privilegeItem)!);
      const privilegesToAdd = privileges.filter(privilegeItem => {
        const privilegeIdentifier = this.getPrivilegeIdentifier(privilegeItem);
        if (privilegeCollectionIdentifiers.includes(privilegeIdentifier)) {
          return false;
        }
        privilegeCollectionIdentifiers.push(privilegeIdentifier);
        return true;
      });
      return [...privilegesToAdd, ...privilegeCollection];
    }
    return privilegeCollection;
  }
}
