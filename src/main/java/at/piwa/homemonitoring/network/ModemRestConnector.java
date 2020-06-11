package at.piwa.homemonitoring.network;

import at.piwa.homemonitoring.network.goform.GetDataCmdBuilder;
import at.piwa.homemonitoring.network.goform.GetDataCmdEnum;
import at.piwa.homemonitoring.network.goform.GoFormGetCmdBuilder;
import at.piwa.homemonitoring.network.goform.GoFormSetCmdBuilder;
import at.piwa.homemonitoring.network.responses.LoginResponse;
import at.piwa.homemonitoring.network.responses.LogoutResponse;
import at.piwa.homemonitoring.network.responses.ModemDataResponse;
import at.piwa.homemonitoring.network.responses.RdQueryResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.List;

@Slf4j
@Component
public class ModemRestConnector {

    @Value("${modem.ip}")
    private String modemIp;
    @Value("${modem.password}")
    private String modemPasswordString;
    @Value("${modem.rd.prefix}")
    private String rdPrefix;

    public String login() throws at.piwa.homemonitoring.network.ModemConnectionException {
        String base64ModemPassword = Base64.getEncoder().encodeToString(modemPasswordString.getBytes());
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = getPostHeaders();

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("isTest", "false");
        body.add("goformId", "LOGIN");
        body.add("password", base64ModemPassword);

        GoFormSetCmdBuilder goFormSetCmdBuilder = new GoFormSetCmdBuilder(modemIp);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(goFormSetCmdBuilder.builder(), request, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            LoginResponse loginResponse = objectMapper.readValue(response.getBody(), LoginResponse.class);

            if (loginResponse.isSuccess()) {
                List<String> cookies = response.getHeaders().get("Set-Cookie");
                if(cookies != null && cookies.size() > 0) {
                    String cookie = cookies.get(0);
                    String loginId = cookie.split("\"")[1];
                    return loginId;
                }
            }
        } catch (Exception e) {
            log.error("Exception", e);
            throw new at.piwa.homemonitoring.network.ModemConnectionException(e);
        }

        throw new at.piwa.homemonitoring.network.ModemConnectionException("Exception while logging in");
    }


    public ModemDataResponse getData(String loginId) throws at.piwa.homemonitoring.network.ModemConnectionException {
        GetDataCmdBuilder getDataCmdBuilder = new GetDataCmdBuilder();
        getDataCmdBuilder.addCmd(GetDataCmdEnum.monthly_rx_bytes);
        getDataCmdBuilder.addCmd(GetDataCmdEnum.monthly_tx_bytes);
        getDataCmdBuilder.addCmd(GetDataCmdEnum.monthly_time);
        getDataCmdBuilder.addCmd(GetDataCmdEnum.realtime_rx_bytes);
        getDataCmdBuilder.addCmd(GetDataCmdEnum.realtime_tx_bytes);
        getDataCmdBuilder.addCmd(GetDataCmdEnum.realtime_rx_thrpt);
        getDataCmdBuilder.addCmd(GetDataCmdEnum.realtime_tx_thrpt);
        getDataCmdBuilder.addCmd(GetDataCmdEnum.realtime_time);
        getDataCmdBuilder.addCmd(GetDataCmdEnum.wifi_chip1_ssid1_access_sta_num);

        GoFormGetCmdBuilder goFormGetCmdBuilder = new GoFormGetCmdBuilder(modemIp);
        goFormGetCmdBuilder.addCmd(getDataCmdBuilder.buildCmdString());
        goFormGetCmdBuilder.isMultiDataCmd();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = getGetHeaders(loginId);
        HttpEntity<String> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(goFormGetCmdBuilder.builder(), HttpMethod.GET, request, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            ModemDataResponse modemDataResponse = objectMapper.readValue(response.getBody(), ModemDataResponse.class);

            return modemDataResponse;
        } catch (Exception e) {
            log.error("Exception", e);
            throw new at.piwa.homemonitoring.network.ModemConnectionException(e);
        }
    }

    public String getRD(String loginId) throws at.piwa.homemonitoring.network.ModemConnectionException {

        GoFormGetCmdBuilder goFormGetCmdBuilder = new GoFormGetCmdBuilder(modemIp);
        goFormGetCmdBuilder.addCmd("RD");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = getGetHeaders(loginId);
        HttpEntity<String> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(goFormGetCmdBuilder.builder(), HttpMethod.GET, request, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            RdQueryResponse result = objectMapper.readValue(response.getBody(), RdQueryResponse.class);
            if (result == null) {
                throw new at.piwa.homemonitoring.network.ModemConnectionException("Exception while getting RD");
            }

            return result.getRd();
        } catch (Exception e) {
            log.error("Exception", e);
            throw new at.piwa.homemonitoring.network.ModemConnectionException(e);
        }
    }

    public boolean logout(String loginId, String rd) throws at.piwa.homemonitoring.network.ModemConnectionException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = getPostHeaders(loginId);

        String adString = rdPrefix + rd;
        String adMd5Hex = DigestUtils.md5DigestAsHex(adString.getBytes());

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("isTest", "false");
        body.add("goformId", "LOGOUT");
        body.add("AD", adMd5Hex);

        GoFormSetCmdBuilder goFormSetCmdBuilder = new GoFormSetCmdBuilder(modemIp);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(goFormSetCmdBuilder.builder(), request, String.class);
            ObjectMapper objectMapper = new ObjectMapper();

            LogoutResponse result = objectMapper.readValue(response.getBody(), LogoutResponse.class);
            if (result == null) {
                throw new at.piwa.homemonitoring.network.ModemConnectionException("Exception while logging out");
            }

            return result.isSuccess();
        } catch (Exception e) {
            log.error("Exception", e);
            throw new at.piwa.homemonitoring.network.ModemConnectionException(e);
        }
    }

    private HttpHeaders getPostHeaders() {
        return getPostHeaders("");
    }

    private HttpHeaders getPostHeaders(String loginId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Referer", "http://" + modemIp + "/index.html");
        headers.set("Accept", "application/json, text/javascript, */*; q=0.01");
        headers.set("Pragma", "no-cach e");
        headers.set("Cache-Contro", "no-cache");
        if (!StringUtils.isEmpty(loginId)) {
            headers.set("Cookie", "zwsd=\"" + loginId + "\"");
        }
        return headers;
    }

    private HttpHeaders getGetHeaders(String loginId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Referer", "http://" + modemIp + "/index.html");
        headers.set("Accept", "application/json, text/javascript, */*; q=0.01");
        headers.set("Pragma", "no-cache");
        headers.set("Cache-Contro", "no-cache");
        headers.set("Cookie", "zwsd=\"" + loginId + "\"");

        return headers;
    }

}
