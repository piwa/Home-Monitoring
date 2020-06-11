package at.piwa.homemonitoring.network.responses;

import lombok.Data;

@Data
public class LoginResponse {

    private String result;

    public boolean isSuccess() {
        return result.equals("0");
    }

}
