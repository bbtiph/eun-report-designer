import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOperationalBody, NewOperationalBody } from '../operational-body.model';

export type PartialUpdateOperationalBody = Partial<IOperationalBody> & Pick<IOperationalBody, 'id'>;

export type EntityResponseType = HttpResponse<IOperationalBody>;
export type EntityArrayResponseType = HttpResponse<IOperationalBody[]>;

@Injectable({ providedIn: 'root' })
export class OperationalBodyService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/operational-bodies');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(operationalBody: NewOperationalBody): Observable<EntityResponseType> {
    return this.http.post<IOperationalBody>(this.resourceUrl, operationalBody, { observe: 'response' });
  }

  update(operationalBody: IOperationalBody): Observable<EntityResponseType> {
    return this.http.put<IOperationalBody>(`${this.resourceUrl}/${this.getOperationalBodyIdentifier(operationalBody)}`, operationalBody, {
      observe: 'response',
    });
  }

  partialUpdate(operationalBody: PartialUpdateOperationalBody): Observable<EntityResponseType> {
    return this.http.patch<IOperationalBody>(`${this.resourceUrl}/${this.getOperationalBodyIdentifier(operationalBody)}`, operationalBody, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOperationalBody>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOperationalBody[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getOperationalBodyIdentifier(operationalBody: Pick<IOperationalBody, 'id'>): number {
    return operationalBody.id;
  }

  compareOperationalBody(o1: Pick<IOperationalBody, 'id'> | null, o2: Pick<IOperationalBody, 'id'> | null): boolean {
    return o1 && o2 ? this.getOperationalBodyIdentifier(o1) === this.getOperationalBodyIdentifier(o2) : o1 === o2;
  }

  addOperationalBodyToCollectionIfMissing<Type extends Pick<IOperationalBody, 'id'>>(
    operationalBodyCollection: Type[],
    ...operationalBodiesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const operationalBodies: Type[] = operationalBodiesToCheck.filter(isPresent);
    if (operationalBodies.length > 0) {
      const operationalBodyCollectionIdentifiers = operationalBodyCollection.map(
        operationalBodyItem => this.getOperationalBodyIdentifier(operationalBodyItem)!
      );
      const operationalBodiesToAdd = operationalBodies.filter(operationalBodyItem => {
        const operationalBodyIdentifier = this.getOperationalBodyIdentifier(operationalBodyItem);
        if (operationalBodyCollectionIdentifiers.includes(operationalBodyIdentifier)) {
          return false;
        }
        operationalBodyCollectionIdentifiers.push(operationalBodyIdentifier);
        return true;
      });
      return [...operationalBodiesToAdd, ...operationalBodyCollection];
    }
    return operationalBodyCollection;
  }
}
