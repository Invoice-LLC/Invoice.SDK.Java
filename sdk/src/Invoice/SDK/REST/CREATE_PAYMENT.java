package Invoice.SDK.REST;

import Invoice.SDK.REST.common.*;
import java.util.Map;
import java.util.List;

public class CREATE_PAYMENT {
    public ORDER order;
    public SETTINGS settings;
    public Map<String, String> custom_parameters;
    public List<ITEM> receipt;
}
