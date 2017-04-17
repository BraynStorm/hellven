--
-- Warrior MOB AI
--
-- Created by IntelliJ IDEA.
-- User: Braynstorm
-- Date: 9.4.2017 г.
-- Time: 14:52

local Direction = luajava.bindClass("braynstorm.hellven.game.Direction")

function tick(entity)
	local world = entity:getWorld()
	local player = world:getPlayer()
	
	local myLoc = entity:getCellLocation():cpy()
	local playerLoc = player:getCellLocation():cpy()
	
	local manhattanDist = myLoc:sub(playerLoc)
	
	if math.abs(manhattanDist.x) + math.abs(manhattanDist.y) > 5 then
		entity:setTarget(nil)
		return
	end
	
	entity:setTarget(player)
	
	local shouldMove = false
	
	if manhattanDist.x < 0 then
		entity:setMovementDirection(Direction.RIGHT)
		shouldMove = true
	elseif manhattanDist.x > 0 then
		entity:setMovementDirection(Direction.LEFT)
		shouldMove = true
	end
	
	if manhattanDist.y < 0 then
		entity:setMovementDirection(Direction.UP)
		shouldMove = true
	elseif manhattanDist.y > 0 then
		entity:setMovementDirection(Direction.DOWN)
		shouldMove = true
	end
	
	if (shouldMove) then
		entity:setMoving(shouldMove)
	end
	
	local abilities = entity:getAbilities()
	for i = 0, abilities:size() - 1 do
		if abilities:get(i):use() then
			print("Used " .. abilities:get(i):toString())
		end
	end
end
