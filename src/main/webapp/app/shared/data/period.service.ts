export interface Period {
  displayName: string;
  code: string;
  id: number;
}

// period.service.ts

import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class PeriodService {
  private periods: Period[] = [
    {
      displayName: 'This year',
      code: 'thisYear',
      id: 1,
    },
    {
      displayName: 'Last year',
      code: 'lastYear',
      id: 2,
    },
    {
      displayName: 'Last two years',
      code: 'lastTwoYears',
      id: 3,
    },
    {
      displayName: 'Last five years',
      code: 'lastFiveYears',
      id: 4,
    },
  ];

  getPeriods(): Period[] {
    return this.periods;
  }

  getPeriodByCode(code: string): Period | undefined {
    return this.periods.find(period => period.code === code);
  }

  getPeriodById(id: number): Period | undefined {
    return this.periods.find(period => period.id == id);
  }

  calculateDateRange(periodCode: string, type: number): { from: string; to: string } | undefined {
    const period = this.getPeriodByCode(periodCode);

    if (!period) {
      return undefined;
    }

    const currentDate = new Date();
    let from: Date;
    let to: Date;

    switch (periodCode) {
      case 'thisYear':
        from = new Date(currentDate.getFullYear(), 0, 1);
        to = new Date(currentDate.getFullYear(), 11, 31);
        break;
      case 'lastYear':
        from = new Date(currentDate.getFullYear() - 1, 0, 1);
        to = new Date(currentDate.getFullYear() - 1, 11, 31);
        break;
      case 'lastTwoYears':
        from = new Date(currentDate.getFullYear() - 2, 0, 1);
        to = new Date(currentDate.getFullYear() - 1, 11, 31);
        break;
      case 'lastFiveYears':
        from = new Date(currentDate.getFullYear() - 5, 0, 1);
        to = new Date(currentDate.getFullYear() - 1, 11, 31);
        break;
      default:
        return undefined;
    }

    if (type === 1) {
      return {
        from: this.formatDateWithOnlyYear(from),
        to: this.formatDateWithOnlyYear(to),
      };
    }
    return {
      from: this.formatDate(from),
      to: this.formatDate(to),
    };
  }

  private formatDate(date: Date): string {
    const day = date.getDate().toString().padStart(2, '0');
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const year = date.getFullYear();

    return `${year}-${month}-${day}`;
  }

  private formatDateWithOnlyYear(date: Date): string {
    const year = date.getFullYear();

    return `${year}`;
  }
}
