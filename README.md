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

globalDowngradeCostPart: 0.1
if UPGRADE cost will be 10000, then downgrade cost will be 1000 (0.1 * 10000)

globalDowngradeCostPart: -0.1
if UPGRADE cost will be 10000, then downgrade REWARD will be 1000 (-0.1 * 10000) (selling enchantments)

randomWeight: higher values means better chance to get that enchantment on rolling random enchantment.

if you don't like cost/chance formula, you can define values for each level
for example:
exactValues: [ 500, 2000, 5000, 50000 ]
500 for upgrade to I
2000 for upgrade to II
5000 for upgrade to III
50000 for upgrade to IV
