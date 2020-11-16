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
public class EeConfigRandom {
    
    private Long minCost;
    private Long maxCost;
    private Double costMultiplier;
    private Integer minEnchantmentPoolSize;

    public Long getMinCost() {
        return minCost;
    }

    public void setMinCost(Long minCost) {
        this.minCost = minCost;
    }

    public Long getMaxCost() {
        return maxCost;
    }

    public void setMaxCost(Long maxCost) {
        this.maxCost = maxCost;
    }

    public void setCostMultiplier(Double costMultiplier) {
        this.costMultiplier = costMultiplier;
    }

    public Double getCostMultiplier() {
        return costMultiplier;
    }

    public void setMinEnchantmentPoolSize(Integer minEnchantmentPoolSize) {
        this.minEnchantmentPoolSize = minEnchantmentPoolSize;
    }

    public Integer getMinEnchantmentPoolSize() {
        return minEnchantmentPoolSize;
    }
    
}
