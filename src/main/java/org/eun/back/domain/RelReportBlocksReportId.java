package org.eun.back.domain;

import java.io.Serializable;
import java.util.Objects;

public class RelReportBlocksReportId implements Serializable {

    private Long reportBlocksId;
    private Long reportId;

    // Конструктор без аргументов
    public RelReportBlocksReportId() {}

    // Конструктор с аргументами
    public RelReportBlocksReportId(Long reportBlocksId, Long reportId) {
        this.reportBlocksId = reportBlocksId;
        this.reportId = reportId;
    }

    // Геттеры и сеттеры для reportBlocksId и reportId

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelReportBlocksReportId that = (RelReportBlocksReportId) o;
        return Objects.equals(reportBlocksId, that.reportBlocksId) && Objects.equals(reportId, that.reportId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportBlocksId, reportId);
    }
}
