-- AI - (D)umb (a)s (F)uck.
--
-- An empty AI script. For entities that are just that dumb.
--
-- Created by IntelliJ IDEA.
-- User: Braynstorm
-- Date: 9.4.2017 г.
-- Time: 19:57
-- To change this template use File | Settings | File Templates.
--

-- Mindlessly follows the player
local Direction = luajava.bindClass("braynstorm.hellven.game.Direction")

function tick(entity)
	local world = entity:getWorld()
	local player = world:getPlayer()
	
	local myLoc = entity:getLocation():cpy()
	local playerLoc = player:getLocation()
	
	local manhattanDist = myLoc:sub(playerLoc)
	
	
	if manhattanDist.x < 0 then
	--	world:entityTryMove(Direction.RIGHT, entity)
	elseif manhattanDist.x > 0 then
	--	world:entityTryMove(Direction.LEFT, entity)
	end
	
	if manhattanDist.y < 0 then
		--world:entityTryMove(Direction.UP, entity)
	elseif manhattanDist.y > 0 then
	--	world:entityTryMove(Direction.DOWN, entity)
	end
	
end
