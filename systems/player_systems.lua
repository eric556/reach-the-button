util = require("util")

player_systems = {}

function player_systems.inputSystem(entity, dt)
    if util.checkForFilters(entity, "body", "joystick_id", "jump_timer") then
        joystick = love.joystick.getJoysticks()[entity.joystick_id]

        if joystick ~= nil then
            local axisDir1, axisDir2, axisDir3, axisDir4 = joystick:getAxes()

            if axisDir1 > 0.1 then
                entity.body:applyForce(1000 * axisDir1, 0)
            elseif axisDir1 < -0.1 then
                entity.body:applyForce(1000 * axisDir1, 0)
            end


            if joystick:isGamepadDown('a') then
                entity.body:applyForce(0, -3000)
                entity.jump_timer = 0
            end
            entity.jump_timer = entity.jump_timer + dt
        end
        --print(axisDir1)
        --print(axisDir2)
        --print(axisDir3)
        --print(axisDir4)
    end
end

return player_systems