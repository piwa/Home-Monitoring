package at.piwa.homemonitoring.network;

import at.piwa.homemonitoring.network.domain.NetworkStats;
import at.piwa.homemonitoring.network.responses.ModemDataResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class ReadNetworkStats {

    @Autowired
    private ModemRestConnector modemRestConnector;
    @Autowired
    private MqttConnectorNetworkStats mqttConnector;

    @Value("${measuring.entities.network}")
    private Boolean measureNetwork = false;

    @Scheduled(fixedRateString = "${network.read.task.interval}")
    public void getNetworkStats() {

        if(measureNetwork) {
            try {
                String loginId = modemRestConnector.login();
                ModemDataResponse data = modemRestConnector.getData(loginId);

                TimeUnit.SECONDS.sleep(2);
                String rd = modemRestConnector.getRD(loginId);
                modemRestConnector.logout(loginId, rd);


                double monthlyIn = (double) data.getMonthly_rx_bytes();
                double monthlyOut = (double) data.getMonthly_tx_bytes();
                double monthlyInGb = (double) data.getMonthly_rx_bytes() / Math.pow(1024,2);
                double monthlyOutGb = (double) data.getMonthly_tx_bytes() / Math.pow(1024,2);

                double monthlyInGbRounded = Math.round(monthlyInGb * 100.0) / 100.0;
                double monthlyOutGbRounded = Math.round(monthlyOutGb * 100.0) / 100.0;


                log.info("Realtime IN traffic (GB): " + (double) data.getRealtime_rx_bytes() / Math.pow(1024,2));
                log.info("Realtime OUT traffic (GB): " + (double) data.getRealtime_tx_bytes() / Math.pow(1024,2));
                log.info("Realtime Time (sec): " + data.getRealtime_time());
                log.info("Realtime IN throughput (GB): " + (double) data.getRealtime_rx_thrpt() / Math.pow(1024,2));
                log.info("Realtime OUT throughput (GB): " + (double) data.getRealtime_tx_thrpt() / Math.pow(1024,2));
                log.info("Monthly IN traffic (GB): " + monthlyInGbRounded);
                log.info("Monthly OUT traffic (GB): " + monthlyOutGbRounded);
                log.info("Monthly Time (sec): " + data.getMonthly_time());

                NetworkStats networkStats = new NetworkStats();
                networkStats.setMonthlyInGb(monthlyInGb);
                networkStats.setMonthlyOutGb(monthlyOutGb);

                mqttConnector.sendNetworkStats(networkStats);

            } catch (ModemConnectionException | InterruptedException ex) {
                log.error("Exception", ex);
            }
        }
    }


}
