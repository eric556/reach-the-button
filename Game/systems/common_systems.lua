util = require("util")
common_systems = {}

function common_systems.drawSystem(entity)
    r,g,b,a = love.graphics.getColor()
    if util.checkForFilters(entity, "shape", "body") then
        if entity.shape.type == "circle" then
            love.graphics.setColor(229/255,115/255,115/255 ,1)
            love.graphics.circle("fill", entity.body:getX(), entity.body:getY(), entity.shape.s:getRadius(), 20)
        elseif entity.shape.type == "rectangle" then
            love.graphics.setColor(100/255,181/255,246/255 ,1)
            love.graphics.polygon("fill", entity.body:getWorldPoints(entity.shape.s:getPoints()))
        end
    end
    love.graphics.setColor(r,g,b,a)
end

function common_systems.generatePhysicsElements(entity, world)
    if util.checkForFilters(entity, "shape", "type", "position") then
        entity.body = love.physics.newBody(world, entity.position.x, entity.position.y, entity.type)
        if entity.type == "dynamic" then
            entity.body:setMass(entity.mass)
            entity.fixture = love.physics.newFixture(entity.body, entity.shape.s)
            entity.fixture:setRestitution(entity.restitution)
        else
            entity.fixture = love.physics.newFixture(entity.body, entity.shape.s)
        end
        entity.fixture:setUserData(entity.name)
    end
end

return common_systems