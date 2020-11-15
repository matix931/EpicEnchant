/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.matix.epicenchant.config;

/**
 *
 * @author Mati
 */
public class EeConfigActionDowngrade extends EeConfigAction {

    private Double costPartModifier;

    public void setCostPartModifier(Double costPartModifier) {
        this.costPartModifier = costPartModifier;
    }

    public Double getCostPartModifier() {
        return costPartModifier;
    }
    
}
