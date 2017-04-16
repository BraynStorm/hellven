--
-- Created by IntelliJ IDEA.
-- User: Braynstorm
-- Date: 9.4.2017 г.
-- Time: 14:52
-- To change this template use File | Settings | File Templates.
--

local Direction = luajava.bindClass("braynstorm.hellven.game.Direction")

-- TODO warrior AI
function tick(entity)
	local world = entity:getWorld()
	local player = world:getPlayer()
	
	local myLoc = entity:getLocation():cpy()
	local playerLoc = player:getLocation()
	
	local manhattanDist = myLoc:sub(playerLoc)
	
	--[[local abilities = entity:getAbilities()
	
	for ability in abilities do
		-- TODO ABILITY CASTING
		print(ability:getName() .. " is "..ability:canUse())
	end
	]]--
	
	if manhattanDist.x < 1 then
		world:entityTryMove(Direction.RIGHT, entity)
	elseif manhattanDist.x > 1 then
		world:entityTryMove(Direction.LEFT, entity)
	end
	
	if manhattanDist.y < 1 then
		world:entityTryMove(Direction.UP, entity)
	elseif manhattanDist.y > 1 then
		world:entityTryMove(Direction.DOWN, entity)
	end
end
