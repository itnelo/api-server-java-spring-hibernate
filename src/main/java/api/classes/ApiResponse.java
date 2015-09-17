package api.classes;

import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Map;

public class ApiResponse {

    protected byte[] output;

    public byte[] getOutput() {
        return output;
    }

    public void setOutput(Map<String, String> output) throws UnsupportedEncodingException {
        this.output = new JSONObject(output).toString().getBytes("UTF-8");
    }

    public void setOutput(String output) throws UnsupportedEncodingException {
        String o = null;
        try {
            o = new JSONObject(output).toString();
        } catch (JSONException e) {
            o = output;
        }
        this.output = o.getBytes("UTF-8");
    }
}
