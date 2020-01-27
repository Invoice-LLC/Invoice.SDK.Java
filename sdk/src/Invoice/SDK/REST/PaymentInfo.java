package Invoice.SDK.REST;

import Invoice.SDK.REST.common.*;
import java.util.Date;
import java.util.Map;

public class PaymentInfo {
    public String id;
    public ORDER order;
    public PAYMENT_STATE status;
    public String status_description;
    public String ip;
    public PAYMENT_METHOD payment_method;
    public Date create_date;
    public Date update_date;
    public Date expire_date;
    public Map<String, String> custom_parameters;
    public String payment_url;
    public String error;
    public String description;
}
