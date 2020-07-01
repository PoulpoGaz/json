package fr.poulpogaz.json.context;

import fr.poulpogaz.json.JsonException;

public class ArrayWriteContext extends JsonWriteContext {

    public ArrayWriteContext(JsonWriteContext parent) {
        super(parent, STATE_EMPTY);
    }

    @Override
    public void newKey() throws JsonException {
        throw new JsonException("Array doesn't have keys");
    }

    @Override
    public void newValue() throws JsonException {
        if (state != STATE_EMPTY && state != STATE_AFTER_VALUE) {
            throw new JsonException("Nesting problem");
        } else if (state == STATE_EMPTY) {
            state = STATE_AFTER_VALUE;
        }
    }

    @Override
    public void newField() throws JsonException {
        throw new JsonException("Cannot add field to an array");
    }

    @Override
    public JsonWriteContext close() throws JsonException {
        if (state == STATE_AFTER_VALUE || state == STATE_EMPTY) {
            state = STATE_END;
            return parent;
        } else {
            throw new JsonException("Nesting problem");
        }
    }

    @Override
    public boolean isArray() {
        return true;
    }
}