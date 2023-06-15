import { Pipe, PipeTransform } from '@angular/core';
import { IReportBlocksContent } from '../../report-blocks-content/report-blocks-content.model';

@Pipe({
  name: 'sortContentByPriority',
})
export class SortContentByPriorityPipe implements PipeTransform {
  transform(contents: IReportBlocksContent[]): IReportBlocksContent[] {
    // @ts-ignore
    return contents.sort((a, b) => a.priorityNumber - b.priorityNumber);
  }
}
