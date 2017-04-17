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
	--	print("Warrior Tick" .. entity:getLocation():toString())
	local world = entity:getWorld()
	local player = world:getPlayer()
	
	local myLoc = entity:getCellLocation():cpy()
	local playerLoc = player:getCellLocation():cpy()
	
	local manhattanDist = myLoc:sub(playerLoc)
	
	local abilities = entity:getAbilities()
	print(abilities[1])
--	for ability in abilities:iterator() do
--		 TODO ABILITY CASTING
--
--	end
	--	print(manhattanDist:len2())
	if math.abs(manhattanDist.x) + math.abs(manhattanDist.y) > 5 then
		entity:setTarget(nil)
		return
	end
	
	entity:setTarget(player)
	
	local shouldMove = false
	
	print(manhattanDist.x .. " : " .. manhattanDist.y)
	if manhattanDist.x < 0 then
		entity:setMovementDirection(Direction.RIGHT)
		print("right")
		shouldMove = true
	elseif manhattanDist.x > 0 then
		entity:setMovementDirection(Direction.LEFT)
		shouldMove = true
		print("left")
	end
	
	if manhattanDist.y < 0 then
		entity:setMovementDirection(Direction.UP)
		print("UP")
		shouldMove = true
	elseif manhattanDist.y > 0 then
		print("DOWN")
		entity:setMovementDirection(Direction.DOWN)
		shouldMove = true
	end
	
	if (shouldMove) then
		entity:setMoving(shouldMove)
	end
end
