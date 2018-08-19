Entities = require("entities")
Systems = require("systems.systems")

Window = {
    width = love.graphics.getWidth(),
    height = love.graphics.getHeight()
}

collision_position = {}

function love.load()
    Shader = love.graphics.newShader[[

        uniform vec2 collision_position[2];

        vec4 lines(vec4 color, Image texture, vec2 texture_coords, vec2 screen_coords){
            vec4 outColor = vec4(0.0,0.0,0.0,color.a);
            int pp = int(mod(screen_coords.x, 3));
            if(pp == 1){
                outColor.r = color.r;
            } else if (pp == 2){
                outColor.g = color.g;
            }else{
                outColor.b = color.b;
            }

            if(int(mod(screen_coords.y, 3)) == 0){
                //outColor *= vec4(0.4,0.4,0.4,1.0);
            }

            return outColor;
        }

        vec4 collision(vec4 color, Image texture, vec2 texture_coords, vec2 screen_coords, vec2 collision){
            vec4 pixel = Texel(texture, texture_coords);
            float d = sqrt((collision.x - screen_coords.x) * (collision.x - screen_coords.x) + (collision.y - screen_coords.y) * (collision.y - screen_coords.y));
            return 1/(d/100) * vec4(0.5,0.0,0.0,1.0);
        }

        vec4 effect(vec4 color, Image texture, vec2 texture_coords, vec2 screen_coords){
            vec4 pixel = Texel(texture, texture_coords);
            vec4 temp = vec4(1.0,1.0,1.0,1.0);
            for(int i = 0; i < 2; i++){
                temp = temp + collision(color, texture, texture_coords, screen_coords, collision_position[i]);
            }
            return pixel * lines(color, texture, texture_coords, screen_coords);// * normalize(temp));
        }
    ]]

    world = love.physics.newWorld(0, 700, true)
    world:setCallbacks(beginContact)

    for key, value in pairs(Entities) do
        Systems.common_systems.generatePhysicsElements(value, world)
    end
end

function love.update(dt)
    world:update(dt)
    for key, value in pairs(Entities) do
        Systems.player_systems.inputSystem(value, dt)
    end
    if collision_position.player_1 ~= nil and collision_position.player_2 ~= nil then
        --print(collision_position.player_1.x .. ", " .. collision_position.player_1.y)
        --print(collision_position.player_2.x .. ", " .. collision_position.player_2.y)
        --Shader:send("collision_position", collision_position.player_1, collision_position.player_2)
    
    end
end

function love.draw()
    love.graphics.setShader(Shader)
    for key, value in pairs(Entities) do
        Systems.common_systems.drawSystem(value)
    end
    love.graphics.setShader()
end

function beginContact(a, b, coll)
    print(a:getUserData())
    print(b:getUserData())
    x1, y1, x2, y2 = coll:getPositions()
    if b:getUserData() == "player 2" then
        print("hit 2")
        collision_position.player_2 = {x1, y1}
    elseif b:getUserData() == "player 1" then
        print("hit 1")
        collision_position.player_1 = {x1, y1}
    end

    if a:getUserData() == "player 2" then
        print("hit 2")
        collision_position.player_2 = {x1, y1}
    elseif a:getUserData() == "player 1" then
        print("hit 1")
        collision_position.player_1 = {x1, y1}
    end
end
