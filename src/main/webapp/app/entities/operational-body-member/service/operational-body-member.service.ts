import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOperationalBodyMember, NewOperationalBodyMember } from '../operational-body-member.model';

export type PartialUpdateOperationalBodyMember = Partial<IOperationalBodyMember> & Pick<IOperationalBodyMember, 'id'>;

type RestOf<T extends IOperationalBodyMember | NewOperationalBodyMember> = Omit<T, 'startDate' | 'endDate'> & {
  startDate?: string | null;
  endDate?: string | null;
};

export type RestOperationalBodyMember = RestOf<IOperationalBodyMember>;

export type NewRestOperationalBodyMember = RestOf<NewOperationalBodyMember>;

export type PartialUpdateRestOperationalBodyMember = RestOf<PartialUpdateOperationalBodyMember>;

export type EntityResponseType = HttpResponse<IOperationalBodyMember>;
export type EntityArrayResponseType = HttpResponse<IOperationalBodyMember[]>;

@Injectable({ providedIn: 'root' })
export class OperationalBodyMemberService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/operational-body-members');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(operationalBodyMember: NewOperationalBodyMember): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(operationalBodyMember);
    return this.http
      .post<RestOperationalBodyMember>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(operationalBodyMember: IOperationalBodyMember): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(operationalBodyMember);
    return this.http
      .put<RestOperationalBodyMember>(`${this.resourceUrl}/${this.getOperationalBodyMemberIdentifier(operationalBodyMember)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(operationalBodyMember: PartialUpdateOperationalBodyMember): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(operationalBodyMember);
    return this.http
      .patch<RestOperationalBodyMember>(`${this.resourceUrl}/${this.getOperationalBodyMemberIdentifier(operationalBodyMember)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestOperationalBodyMember>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestOperationalBodyMember[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getOperationalBodyMemberIdentifier(operationalBodyMember: Pick<IOperationalBodyMember, 'id'>): number {
    return operationalBodyMember.id;
  }

  compareOperationalBodyMember(o1: Pick<IOperationalBodyMember, 'id'> | null, o2: Pick<IOperationalBodyMember, 'id'> | null): boolean {
    return o1 && o2 ? this.getOperationalBodyMemberIdentifier(o1) === this.getOperationalBodyMemberIdentifier(o2) : o1 === o2;
  }

  addOperationalBodyMemberToCollectionIfMissing<Type extends Pick<IOperationalBodyMember, 'id'>>(
    operationalBodyMemberCollection: Type[],
    ...operationalBodyMembersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const operationalBodyMembers: Type[] = operationalBodyMembersToCheck.filter(isPresent);
    if (operationalBodyMembers.length > 0) {
      const operationalBodyMemberCollectionIdentifiers = operationalBodyMemberCollection.map(
        operationalBodyMemberItem => this.getOperationalBodyMemberIdentifier(operationalBodyMemberItem)!
      );
      const operationalBodyMembersToAdd = operationalBodyMembers.filter(operationalBodyMemberItem => {
        const operationalBodyMemberIdentifier = this.getOperationalBodyMemberIdentifier(operationalBodyMemberItem);
        if (operationalBodyMemberCollectionIdentifiers.includes(operationalBodyMemberIdentifier)) {
          return false;
        }
        operationalBodyMemberCollectionIdentifiers.push(operationalBodyMemberIdentifier);
        return true;
      });
      return [...operationalBodyMembersToAdd, ...operationalBodyMemberCollection];
    }
    return operationalBodyMemberCollection;
  }

  protected convertDateFromClient<T extends IOperationalBodyMember | NewOperationalBodyMember | PartialUpdateOperationalBodyMember>(
    operationalBodyMember: T
  ): RestOf<T> {
    return {
      ...operationalBodyMember,
      startDate: operationalBodyMember.startDate?.format(DATE_FORMAT) ?? null,
      endDate: operationalBodyMember.endDate?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restOperationalBodyMember: RestOperationalBodyMember): IOperationalBodyMember {
    return {
      ...restOperationalBodyMember,
      startDate: restOperationalBodyMember.startDate ? dayjs(restOperationalBodyMember.startDate) : undefined,
      endDate: restOperationalBodyMember.endDate ? dayjs(restOperationalBodyMember.endDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestOperationalBodyMember>): HttpResponse<IOperationalBodyMember> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestOperationalBodyMember[]>): HttpResponse<IOperationalBodyMember[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
