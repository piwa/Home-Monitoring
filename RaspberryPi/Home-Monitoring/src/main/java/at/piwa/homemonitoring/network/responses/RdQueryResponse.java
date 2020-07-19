package at.piwa.homemonitoring.network.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RdQueryResponse {

    @JsonProperty("RD")
    private String rd;

}
