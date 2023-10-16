package org.eun.back.domain;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "rel_report_blocks_report")
@IdClass(RelReportBlocksReportId.class)
public class RelReportBlocksReport implements Serializable {

    @Id
    @Column(name = "report_blocks_id")
    private Long reportBlocksId;

    @Id
    @Column(name = "report_id")
    private Long reportId;

    @Column(name = "priority_number")
    private Long priorityNumber;

    public RelReportBlocksReport(Long reportBlocksId, Long reportId, Long priorityNumber) {
        this.reportBlocksId = reportBlocksId;
        this.reportId = reportId;
        this.priorityNumber = priorityNumber;
    }

    public RelReportBlocksReport() {}

    public Long getReportBlocksId() {
        return reportBlocksId;
    }

    public void setReportBlocksId(Long reportBlocksId) {
        this.reportBlocksId = reportBlocksId;
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public Long getPriorityNumber() {
        return priorityNumber;
    }

    public void setPriorityNumber(Long priorityNumber) {
        this.priorityNumber = priorityNumber;
    }

    @Override
    public String toString() {
        return (
            "RelReportBlocksReport{" +
            "reportBlocksId=" +
            reportBlocksId +
            ", reportId=" +
            reportId +
            ", priorityNumber=" +
            priorityNumber +
            '}'
        );
    }
}
