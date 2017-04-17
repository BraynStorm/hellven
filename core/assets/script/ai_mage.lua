--
-- Mage MOB AI
--
-- Created by IntelliJ IDEA.
-- User: Braynstorm
-- Date: 16.4.2017 г.
-- Time: 17:36


-- TODO mage AI
function tick(entity)
	local world = entity:getWorld()
	local player = world:getPlayer()
	
	local myLoc = entity:getCellLocation():cpy()
	local playerLoc = player:getCellLocation():cpy()
	
	local manhattanDist = myLoc:sub(playerLoc)
	
	if math.abs(manhattanDist.x) + math.abs(manhattanDist.y) > 7 or player:getDead() then
		entity:setTarget(nil)
		return
	end
	
	entity:setTarget(player)
	
	local abilities = entity:getAbilities()
	for i = 0, abilities:size() - 1 do
		if abilities:get(i):use() then
			print("Casted " .. abilities:get(i):toString())
		end
	end
end
