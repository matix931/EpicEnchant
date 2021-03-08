EpicEnchant - plugin for Minecraft

Cost formula:
(minValue * minValueModifier) + ( 
	currentEnchantLevel ^ (levelPower * levelPowerModifier) 
	* (levelMultiplier * levelMultiplierModifier) 
)

Chance (%) formula:
100 - (
	currentEnchantLevel ^ (levelPower * levelPowerModifier) 
	* (levelMultiplier * levelMultiplierModifier) 
)
if chance is less then minValue then chance = minValue