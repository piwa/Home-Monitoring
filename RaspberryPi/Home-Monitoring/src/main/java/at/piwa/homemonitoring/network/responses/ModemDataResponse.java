package at.piwa.homemonitoring.network.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ModemDataResponse {

    private String modem_main_state;
    private String pin_status;
    private String opms_wan_mode;
    private String loginfo;
    private String new_version_state;
    private String current_upgrade_state;
    private String is_mandatory;
    private String ppp_dial_conn_fail_counter;
    private String wan_ipaddr;
    private String static_wan_ipaddr;
    private String ipv6_wan_ipaddr;
    private String signalbar;
    private String network_type;
    private String network_provider;
    private String ppp_status;
    @JsonProperty("EX_SSID1")
    private String EX_SSID1;
    private String sta_ip_status;
    @JsonProperty("EX_wifi_profile")
    private String EX_wifi_profile;
    private String guest_switch;
    private String wifi_ap_mode;
    private String wifi_onoff_state;
    private String wifi_chip1_ssid1_ssid;
    private String wifi_chip2_ssid1_ssid;
    private String wifi_chip1_ssid1_access_sta_num;
    private String wifi_chip2_ssid1_access_sta_num;
    private String wifi_chip1_ssid2_access_sta_num;
    private String wifi_chip2_ssid2_access_sta_num;
    private String simcard_roam;
    private String lan_ipaddr;
    private String wifi_access_sta_num;
    private String station_mac;
    private String battery_charging;
    private String battery_vol_percent;
    private String battery_pers;
    private String battery_enable;
    private String battery_exist;
    private String power_adapter_plug;
    private String spn_name_data;
    private String spn_b1_flag;
    private String spn_b2_flag;
    private String wifi_chip1_ssid1_switch_onoff;
    private String wifi_chip2_ssid1_switch_onoff;
    private Long realtime_tx_bytes;
    private Long realtime_rx_bytes;
    private String realtime_time;
    private Long realtime_tx_thrpt;
    private Long realtime_rx_thrpt;
    private Long monthly_rx_bytes;
    private Long monthly_tx_bytes;
    private String monthly_time;
    private String date_month;
    private String data_volume_limit_switch;
    private String data_volume_limit_size;
    private String data_volume_alert_percent;
    private String data_volume_limit_unit;
    private String roam_setting_option;
    private String upg_roam_switch;
    private String ssid;
    private String wifi_enable;
    private String check_web_conflict;
    private String dial_mode;
    private String hmnc;
    private String hmcc;
    private String mnc;
    private String mcc;
    private String wlan_process;
    private String wifi_dfs_status;
    private String privacy_read_flag;
    private String zigbee_update_status;
    private String sms_received_flag;
    private String sts_received_flag;
    private String sms_unread_num;

}
