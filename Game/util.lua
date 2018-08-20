util = {}

function util.checkForFilters(entity, ...)
    local toReturn = true
    for i = 1, select("#", ...) do
        toReturn = toReturn and (entity[select(i, ...)] ~= nil)
    end
    return toReturn
end

return util