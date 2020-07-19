package at.piwa.homemonitoring.network.goform;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GetDataCmdBuilder {

//    http://192.168.0.1/goform/goform_get_cmd_process?multi_data=1&isTest=false&sms_received_flag_flag=0&sts_received_flag_flag=0&cmd=modem_main_state%2Cpin_status%2Copms_wan_mode%2Cloginfo%2Cnew_version_state%2Ccurrent_upgrade_state%2Cis_mandatory%2Cwan_ipaddr%2Cstatic_wan_ipaddr%2Cipv6_wan_ipaddr%2Csignalbar%2Cnetwork_type%2Cnetwork_provider%2Cppp_status%2CEX_SSID1%2Csta_ip_status%2CEX_wifi_profile%2Cguest_switch%2Cwifi_ap_mode%2Cwifi_onoff_state%2Cwifi_chip1_ssid1_ssid%2Cwifi_chip2_ssid1_ssid%2Cwifi_chip1_ssid1_access_sta_num%2Cwifi_chip2_ssid1_access_sta_num%2Cwifi_chip1_ssid2_access_sta_num%2Cwifi_chip2_ssid2_access_sta_num%2Csimcard_roam%2Clan_ipaddr%2Cwifi_access_sta_num%2Cstation_mac%2Cbattery_charging%2Cbattery_vol_percent%2Cbattery_pers%2Cbattery_enable%2Cbattery_exist%2Cpower_adapter_plug%2Cspn_name_data%2Cspn_b1_flag%2Cspn_b2_flag%2Cwifi_chip1_ssid1_switch_onoff%2Cwifi_chip2_ssid1_switch_onoff%2Crealtime_tx_bytes%2Crealtime_rx_bytes%2Crealtime_time%2Crealtime_tx_thrpt%2Crealtime_rx_thrpt%2Cmonthly_rx_bytes%2Cmonthly_tx_bytes%2Cmonthly_time%2Cdate_month%2Cdata_volume_limit_switch%2Cdata_volume_limit_size%2Cdata_volume_alert_percent%2Cdata_volume_limit_unit%2Croam_setting_option%2Cupg_roam_switch%2Cssid%2Cwifi_enable%2Ccheck_web_conflict%2Cdial_mode%2Cppp_dial_conn_fail_counter%2Chmnc%2Chmcc%2Cmnc%2Cmcc%2Cwlan_process%2Cwifi_dfs_status%2Cprivacy_read_flag%2Czigbee_update_status%2Csms_received_flag%2Csts_received_flag%2Csms_unread_num&_=1591352812302


    private String separater = "%2C";
    private List<GetDataCmdEnum> cmdList = new ArrayList<>();

    public void addCmd(GetDataCmdEnum cmd) {
        cmdList.add(cmd);
    }

    public String buildCmdString() {
        return cmdList.stream().map(Enum::name).collect(Collectors.joining(separater));
    }


}
