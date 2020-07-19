package at.piwa.homemonitoring.network.goform;

public class GoFormSetCmdBuilder {

    private String ipAddress = "";

    public GoFormSetCmdBuilder(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String builder() {
        return  "http://" + ipAddress + "/goform/goform_set_cmd_process";
    }
}
