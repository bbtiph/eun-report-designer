import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMoeContacts, NewMoeContacts } from '../moe-contacts.model';

export type PartialUpdateMoeContacts = Partial<IMoeContacts> & Pick<IMoeContacts, 'id'>;

export type EntityResponseType = HttpResponse<IMoeContacts>;
export type EntityArrayResponseType = HttpResponse<IMoeContacts[]>;

@Injectable({ providedIn: 'root' })
export class MoeContactsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/moe-contacts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(moeContacts: NewMoeContacts): Observable<EntityResponseType> {
    return this.http.post<IMoeContacts>(this.resourceUrl, moeContacts, { observe: 'response' });
  }

  upload(data: FormData): Observable<HttpResponse<{}>> {
    return this.http.post<String>(`${this.resourceUrl}/upload`, data, { observe: 'response' });
  }

  update(moeContacts: IMoeContacts): Observable<EntityResponseType> {
    return this.http.put<IMoeContacts>(`${this.resourceUrl}/${this.getMoeContactsIdentifier(moeContacts)}`, moeContacts, {
      observe: 'response',
    });
  }

  partialUpdate(moeContacts: PartialUpdateMoeContacts): Observable<EntityResponseType> {
    return this.http.patch<IMoeContacts>(`${this.resourceUrl}/${this.getMoeContactsIdentifier(moeContacts)}`, moeContacts, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMoeContacts>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMoeContacts[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMoeContactsIdentifier(moeContacts: Pick<IMoeContacts, 'id'>): number {
    return moeContacts.id;
  }

  compareMoeContacts(o1: Pick<IMoeContacts, 'id'> | null, o2: Pick<IMoeContacts, 'id'> | null): boolean {
    return o1 && o2 ? this.getMoeContactsIdentifier(o1) === this.getMoeContactsIdentifier(o2) : o1 === o2;
  }

  addMoeContactsToCollectionIfMissing<Type extends Pick<IMoeContacts, 'id'>>(
    moeContactsCollection: Type[],
    ...moeContactsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const moeContacts: Type[] = moeContactsToCheck.filter(isPresent);
    if (moeContacts.length > 0) {
      const moeContactsCollectionIdentifiers = moeContactsCollection.map(
        moeContactsItem => this.getMoeContactsIdentifier(moeContactsItem)!
      );
      const moeContactsToAdd = moeContacts.filter(moeContactsItem => {
        const moeContactsIdentifier = this.getMoeContactsIdentifier(moeContactsItem);
        if (moeContactsCollectionIdentifiers.includes(moeContactsIdentifier)) {
          return false;
        }
        moeContactsCollectionIdentifiers.push(moeContactsIdentifier);
        return true;
      });
      return [...moeContactsToAdd, ...moeContactsCollection];
    }
    return moeContactsCollection;
  }
}
