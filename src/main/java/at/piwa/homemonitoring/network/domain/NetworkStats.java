package at.piwa.homemonitoring.network.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NetworkStats {

    @JsonProperty("monthly_in_gb")
    private Double monthlyInGb;

    @JsonProperty("monthly_out_gb")
    private Double monthlyOutGb;

}
