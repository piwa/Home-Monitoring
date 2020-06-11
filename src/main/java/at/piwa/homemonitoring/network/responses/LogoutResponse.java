package at.piwa.homemonitoring.network.responses;

import lombok.Data;

@Data
public class LogoutResponse {

    private String result;

    public boolean isSuccess() {
        return result.equals("success");
    }
}
