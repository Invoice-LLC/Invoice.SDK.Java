package Invoice.SDK.REST;

import Invoice.SDK.REST.common.*;
import com.fasterxml.jackson.core.JsonToken;
import java.util.Map;
import java.util.List;

public class CREATE_TERMINAL {
    public String alias;
    public String name;
    public String description;
    public TERMINAL_TYPE type;
    public double defaultPrice;
    public boolean canComments;
}
