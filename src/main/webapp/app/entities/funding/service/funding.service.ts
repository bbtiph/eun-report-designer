import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFunding, NewFunding } from '../funding.model';

export type PartialUpdateFunding = Partial<IFunding> & Pick<IFunding, 'id'>;

export type EntityResponseType = HttpResponse<IFunding>;
export type EntityArrayResponseType = HttpResponse<IFunding[]>;

@Injectable({ providedIn: 'root' })
export class FundingService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/fundings');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(funding: NewFunding): Observable<EntityResponseType> {
    return this.http.post<IFunding>(this.resourceUrl, funding, { observe: 'response' });
  }

  update(funding: IFunding): Observable<EntityResponseType> {
    return this.http.put<IFunding>(`${this.resourceUrl}/${this.getFundingIdentifier(funding)}`, funding, { observe: 'response' });
  }

  partialUpdate(funding: PartialUpdateFunding): Observable<EntityResponseType> {
    return this.http.patch<IFunding>(`${this.resourceUrl}/${this.getFundingIdentifier(funding)}`, funding, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFunding>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFunding[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFundingIdentifier(funding: Pick<IFunding, 'id'>): number {
    return funding.id;
  }

  compareFunding(o1: Pick<IFunding, 'id'> | null, o2: Pick<IFunding, 'id'> | null): boolean {
    return o1 && o2 ? this.getFundingIdentifier(o1) === this.getFundingIdentifier(o2) : o1 === o2;
  }

  addFundingToCollectionIfMissing<Type extends Pick<IFunding, 'id'>>(
    fundingCollection: Type[],
    ...fundingsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const fundings: Type[] = fundingsToCheck.filter(isPresent);
    if (fundings.length > 0) {
      const fundingCollectionIdentifiers = fundingCollection.map(fundingItem => this.getFundingIdentifier(fundingItem)!);
      const fundingsToAdd = fundings.filter(fundingItem => {
        const fundingIdentifier = this.getFundingIdentifier(fundingItem);
        if (fundingCollectionIdentifiers.includes(fundingIdentifier)) {
          return false;
        }
        fundingCollectionIdentifiers.push(fundingIdentifier);
        return true;
      });
      return [...fundingsToAdd, ...fundingCollection];
    }
    return fundingCollection;
  }
}
