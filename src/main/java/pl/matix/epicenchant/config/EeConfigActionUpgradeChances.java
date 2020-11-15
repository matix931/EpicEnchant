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
public class EeConfigActionUpgradeChances {
    
    private Double minValueModifier;
    private Double levelMultiplierModifier;
    private Double levelPowerModifier;
    private Integer[] exactValues;

    public Double getMinValueModifier() {
        return minValueModifier;
    }

    public void setMinValueModifier(Double minValueModifier) {
        this.minValueModifier = minValueModifier;
    }

    public Double getLevelMultiplierModifier() {
        return levelMultiplierModifier;
    }

    public void setLevelMultiplierModifier(Double levelMultiplierModifier) {
        this.levelMultiplierModifier = levelMultiplierModifier;
    }

    public Double getLevelPowerModifier() {
        return levelPowerModifier;
    }

    public void setLevelPowerModifier(Double levelPowerModifier) {
        this.levelPowerModifier = levelPowerModifier;
    }

    public Integer[] getExactValues() {
        return exactValues;
    }

    public void setExactValues(Integer[] exactValues) {
        this.exactValues = exactValues;
    }
}
