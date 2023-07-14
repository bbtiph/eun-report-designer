import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IReferenceTableSettings, NewReferenceTableSettings } from '../reference-table-settings.model';

export type PartialUpdateReferenceTableSettings = Partial<IReferenceTableSettings> & Pick<IReferenceTableSettings, 'id'>;

export type EntityResponseType = HttpResponse<IReferenceTableSettings>;
export type EntityArrayResponseType = HttpResponse<IReferenceTableSettings[]>;

@Injectable({ providedIn: 'root' })
export class ReferenceTableSettingsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/reference-table-settings');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(referenceTableSettings: NewReferenceTableSettings): Observable<EntityResponseType> {
    return this.http.post<IReferenceTableSettings>(this.resourceUrl, referenceTableSettings, { observe: 'response' });
  }

  update(referenceTableSettings: IReferenceTableSettings): Observable<EntityResponseType> {
    return this.http.put<IReferenceTableSettings>(
      `${this.resourceUrl}/${this.getReferenceTableSettingsIdentifier(referenceTableSettings)}`,
      referenceTableSettings,
      { observe: 'response' }
    );
  }

  partialUpdate(referenceTableSettings: PartialUpdateReferenceTableSettings): Observable<EntityResponseType> {
    return this.http.patch<IReferenceTableSettings>(
      `${this.resourceUrl}/${this.getReferenceTableSettingsIdentifier(referenceTableSettings)}`,
      referenceTableSettings,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IReferenceTableSettings>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IReferenceTableSettings[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getReferenceTableSettingsIdentifier(referenceTableSettings: Pick<IReferenceTableSettings, 'id'>): number {
    return referenceTableSettings.id;
  }

  compareReferenceTableSettings(o1: Pick<IReferenceTableSettings, 'id'> | null, o2: Pick<IReferenceTableSettings, 'id'> | null): boolean {
    return o1 && o2 ? this.getReferenceTableSettingsIdentifier(o1) === this.getReferenceTableSettingsIdentifier(o2) : o1 === o2;
  }

  addReferenceTableSettingsToCollectionIfMissing<Type extends Pick<IReferenceTableSettings, 'id'>>(
    referenceTableSettingsCollection: Type[],
    ...referenceTableSettingsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const referenceTableSettings: Type[] = referenceTableSettingsToCheck.filter(isPresent);
    if (referenceTableSettings.length > 0) {
      const referenceTableSettingsCollectionIdentifiers = referenceTableSettingsCollection.map(
        referenceTableSettingsItem => this.getReferenceTableSettingsIdentifier(referenceTableSettingsItem)!
      );
      const referenceTableSettingsToAdd = referenceTableSettings.filter(referenceTableSettingsItem => {
        const referenceTableSettingsIdentifier = this.getReferenceTableSettingsIdentifier(referenceTableSettingsItem);
        if (referenceTableSettingsCollectionIdentifiers.includes(referenceTableSettingsIdentifier)) {
          return false;
        }
        referenceTableSettingsCollectionIdentifiers.push(referenceTableSettingsIdentifier);
        return true;
      });
      return [...referenceTableSettingsToAdd, ...referenceTableSettingsCollection];
    }
    return referenceTableSettingsCollection;
  }
}
