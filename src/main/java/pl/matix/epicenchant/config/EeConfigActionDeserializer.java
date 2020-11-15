/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.matix.epicenchant.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import pl.matix.epicenchant.actions.EeActionType;

/**
 *
 * @author Mati
 */
public class EeConfigActionDeserializer extends StdDeserializer<EeConfigAction> {

    public EeConfigActionDeserializer() {
        this(EeConfigAction.class);
    }
    
    public EeConfigActionDeserializer(Class<?> vc) {
        super(vc);
    }
    
    @Override
    public EeConfigAction deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        String key = jp.getParsingContext().getParent().getCurrentName();
        EeActionType actionType = EeActionType.fromString(key);
        Class configClass = actionType.getActionHandler().getConfigClass();
        return (EeConfigAction) jp.readValueAs(configClass);
    }
    
}
