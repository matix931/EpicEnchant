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
public class EeConfigGlobalUpgradeChances {
    
    private Double minValue;
    private Double levelMultiplier;
    private Double levelPower;
    private Integer[] exactValues;

    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public Double getLevelMultiplier() {
        return levelMultiplier;
    }

    public void setLevelMultiplier(Double levelMultiplier) {
        this.levelMultiplier = levelMultiplier;
    }

    public Double getLevelPower() {
        return levelPower;
    }

    public void setLevelPower(Double levelPower) {
        this.levelPower = levelPower;
    }

    public Integer[] getExactValues() {
        return exactValues;
    }

    public void setExactValues(Integer[] exactValues) {
        this.exactValues = exactValues;
    }
    
}
