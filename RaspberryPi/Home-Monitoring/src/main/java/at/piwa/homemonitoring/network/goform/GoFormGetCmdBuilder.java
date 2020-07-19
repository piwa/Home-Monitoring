package at.piwa.homemonitoring.network.goform;

public class GoFormGetCmdBuilder {

    private String ipAddress = "";
    private Boolean isTest = false;
    private Boolean multiData = false;
    private String cmd = "";

    public GoFormGetCmdBuilder(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void addCmd(String cmd) {
        this.cmd = cmd;
    }

    public void isMultiDataCmd() {
        multiData = true;
    }

    public String builder() {
        String url = "http://" + ipAddress + "/goform/goform_get_cmd_process?";
        if(multiData) {
            url = url + "multi_data=1&";
        }
        url = url + "isTest=" + isTest + "&cmd=" + cmd;
        return url;
    }
}
