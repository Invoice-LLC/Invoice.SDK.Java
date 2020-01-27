package Invoice.SDK.REST;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;


public class RestClient {
    public String Url = "https://api.invoice.su/api/v2/";

    String authKey64;
    HttpClient httpClient = HttpClientBuilder.create().build();
    public String Login;
    public String ApiKey;
    public boolean Debug = false;

    public RestClient(String _Login, String _ApiKey) {
        Login = _Login;
        ApiKey = _ApiKey;
    }

    private String Send(String requestType, String json) {
        String result = "";
        try {
            HttpPost request = new HttpPost(Url + requestType);
            StringEntity params = new StringEntity(json, "UTF-8");
            authKey64 = new String(Base64.encodeBase64((Login + ":" + ApiKey).getBytes()));

            request.addHeader("Host", "pay.invoice.su");
            request.addHeader("content-type", "application/json");
            request.addHeader("Authorization", "Basic " + authKey64);
            request.addHeader("User-Agent", "curl/7.55.1");
            request.addHeader("Accept", "*/*");

            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);

            HttpEntity entity2 = response.getEntity();
            result = EntityUtils.toString(entity2);
        }catch (Exception exc){
            print(exc.toString());
        }

        return result;
    }

    public PaymentInfo CreatePayment(CREATE_PAYMENT request) {
        PaymentInfo result = new PaymentInfo();
        try {
            ObjectMapper mapper = new ObjectMapper();
            print("CreatePayment");
            String json = mapper.writeValueAsString(request);
            print("request: " +  json);
            String response = Send("CreatePayment", json);
            print("response: " + response);

            result = mapper.readValue(response, PaymentInfo.class);
        } catch (Exception exc){
            print(exc.toString());
        }
        return result;
    }

    public PaymentInfo GetPayment(GET_PAYMENT request) {
        PaymentInfo result = new PaymentInfo();
        try {
            ObjectMapper mapper = new ObjectMapper();
            print("GetPayment");
            String json = mapper.writeValueAsString(request);
            print("request: " +  json);
            String response = Send("GetPayment", json);
            print("response: " + response);
            result = mapper.readValue(response, PaymentInfo.class);
        } catch (Exception exc){
            print(exc.toString());
        }
        return result;
    }

    public PaymentInfo GetPaymentByOrder(GET_PAYMENT_FROM_ORDER request) {
        PaymentInfo result = new PaymentInfo();
        try {
            ObjectMapper mapper = new ObjectMapper();
            print("GetPaymentFromOrder");
            String json = mapper.writeValueAsString(request);
            print("request: " +  json);
            String response = Send("GetPaymentByOrder", json);
            print("response: " + response);
            result = mapper.readValue(response, PaymentInfo.class);
        } catch (Exception exc){
            print(exc.toString());
        }
        return result;
    }

    public PaymentInfo ClosePayment(CLOSE_PAYMENT request) {
        PaymentInfo result = new PaymentInfo();
        try {
            ObjectMapper mapper = new ObjectMapper();
            print("ClosePayment");
            String json = mapper.writeValueAsString(request);
            print("request: " +  json);
            String response = Send("ClosePayment", json);
            print("response: " + response);
            result = mapper.readValue(response, PaymentInfo.class);
        } catch (Exception exc){
            print(exc.toString());
        }
        return result;
    }

    public RefundInfo CreateRefund(CREATE_REFUND request) {
        RefundInfo result = new RefundInfo();
        try {
            ObjectMapper mapper = new ObjectMapper();
            print("CreateRefund");
            String json = mapper.writeValueAsString(request);
            print("request: " +  json);
            String response = Send("CreateRefund", json);
            print("response: " + response);
            result = mapper.readValue(response, RefundInfo.class);
        } catch (Exception exc){
            print(exc.toString());
        }
        return result;
    }

    public RefundInfo GetRefund(GET_REFUND request) {
        RefundInfo result = new RefundInfo();
        try {
            ObjectMapper mapper = new ObjectMapper();
            print("GetRefund");
            String json = mapper.writeValueAsString(request);
            print("request: " +  json);
            String response = Send("GetRefund", json);
            print("response: " + response);
            result = mapper.readValue(response, RefundInfo.class);
        } catch (Exception exc){
            print(exc.toString());
        }
        return result;
    }

    public TerminalInfo CreateTerminal(CREATE_TERMINAL request) {
        TerminalInfo result = new TerminalInfo();
        try {
            ObjectMapper mapper = new ObjectMapper();
            print("CreateTerminal");
            String json = mapper.writeValueAsString(request);
            print("request: " +  json);
            String response = Send("CreateTerminal", json);
            print("response: " + response);
            result = mapper.readValue(response, TerminalInfo.class);
        } catch (Exception exc){
            print(exc.toString());
        }
        return result;
    }

    public TerminalInfo GetTerminal(GET_TERMINAL request) {
        TerminalInfo result = new TerminalInfo();
        try {
            ObjectMapper mapper = new ObjectMapper();
            print("GetTerminal");
            String json = mapper.writeValueAsString(request);
            print("request: " +  json);
            String response = Send("GetTerminal", json);
            print("response: " + response);
            result = mapper.readValue(response, TerminalInfo.class);
        } catch (Exception exc){
            print(exc.toString());
        }
        return result;
    }
    
    private void print(String msg){
        System.out.println(msg);
    }
}
