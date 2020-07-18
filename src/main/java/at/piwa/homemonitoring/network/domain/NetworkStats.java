package at.piwa.homemonitoring.network.domain;

import at.piwa.homemonitoring.MeasuredEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NetworkStats {

    @JsonProperty("monthly_in_gb")
    private Double monthlyInGb;

    @JsonProperty("monthly_out_gb")
    private Double monthlyOutGb;

    @JsonProperty("measured_entity")
    private MeasuredEntity measuredEntity = MeasuredEntity.NetworkTraffic;



}
