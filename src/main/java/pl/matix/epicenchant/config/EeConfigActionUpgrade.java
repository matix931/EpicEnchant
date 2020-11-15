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
public class EeConfigActionUpgrade extends EeConfigAction {
    
    private int maxLevel;
    private EeConfigActionUpgradeCosts costs;
    private EeConfigActionUpgradeChances chances;
    private Boolean downgradeOnFail;

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public EeConfigActionUpgradeCosts getCosts() {
        return costs;
    }

    public void setCosts(EeConfigActionUpgradeCosts costs) {
        this.costs = costs;
    }

    public void setChances(EeConfigActionUpgradeChances chances) {
        this.chances = chances;
    }

    public EeConfigActionUpgradeChances getChances() {
        return chances;
    }

    public Boolean getDowngradeOnFail() {
        return downgradeOnFail;
    }

    public void setDowngradeOnFail(Boolean downgradeOnFail) {
        this.downgradeOnFail = downgradeOnFail;
    }
   
}
