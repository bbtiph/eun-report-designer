package org.eun.back.service.dto;

import java.util.ArrayList;
import java.util.List;

public class IndicatorsDto {

    private List<Indicator> indicators = new ArrayList<>();

    public IndicatorsDto() {}

    public List<Indicator> getIndicators() {
        return indicators;
    }

    public void setIndicators(List<Indicator> indicators) {
        this.indicators = indicators;
    }
}
