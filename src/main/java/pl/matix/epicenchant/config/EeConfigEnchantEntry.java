/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.matix.epicenchant.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Map;
import pl.matix.epicenchant.actions.EeActionType;

/**
 *
 * @author Mati
 */
public class EeConfigEnchantEntry {
    
    @JsonDeserialize(contentUsing = EeConfigActionDeserializer.class)
    private Map<EeActionType, EeConfigAction> actions;

    public EeConfigEnchantEntry() {
    }

    public Map<EeActionType, EeConfigAction> getActions() {
        return actions;
    }

    public void setActions(Map<EeActionType, EeConfigAction> actions) {
        this.actions = actions;
    }
    
    @JsonIgnore
    public EeConfigActionUpgrade getActionUpgrade() {
        return (EeConfigActionUpgrade) actions.get(EeActionType.UPGRADE);
    }
    
    @JsonIgnore
    public EeConfigActionDowngrade getActionDowngrade() {
        return (EeConfigActionDowngrade) actions.get(EeActionType.DOWNGRADE);
    }

}
