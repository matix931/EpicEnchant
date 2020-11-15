/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.matix.epicenchant.actions;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Mati
 */
public enum EeActionType {
    
    UPGRADE(new EeActionHandlerUpgrade()),
    DOWNGRADE(new EeActionHandlerDowngrade())
    ;
    
    private static final Map<String, EeActionType> map = new HashMap<>();    
    static {
        for(EeActionType st : values()) {
            map.put(st.name(), st);
        }
    }
    
    private final EeActionHandler actionHandler;

    private EeActionType(EeActionHandler ah) {
        this.actionHandler = ah;
    }
    
    public static EeActionType fromString(String st) {
        if(st==null) {
            return null;
        }
        return map.get(st.trim().toUpperCase());
    }

    public EeActionHandler getActionHandler() {
        return actionHandler;
    }
    
}
