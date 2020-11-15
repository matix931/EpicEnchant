package pl.matix.epicenchant.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.HashMap;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.junit.jupiter.api.Test;
import pl.matix.epicenchant.actions.EeActionType;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mati
 */
public class EeConfigTest {
    
    @Test
    public void testConfig() throws URISyntaxException, IOException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        ClassLoader classLoader = getClass().getClassLoader();
        ObjectMapper om = new ObjectMapper();
        EeConfig config = om.readValue(classLoader.getResourceAsStream("config.json"), EeConfig.class);
        assert config != null;
        assert config.getEnchantmentConfig(Enchantment.LOOT_BONUS_BLOCKS).getActions().get(EeActionType.UPGRADE) instanceof EeConfigActionUpgrade;
        assert config.getEnchantmentConfig(Enchantment.LOOT_BONUS_BLOCKS).getActions().get(EeActionType.DOWNGRADE) instanceof EeConfigActionDowngrade;
    }
    
}
