/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2019 TweetWallFX
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.tweetwallfx.devoxx.api.cfp.client;

import static org.tweetwallfx.util.ToString.createToString;
import static org.tweetwallfx.util.ToString.map;

/**
 * The "Room" object gives you details about location and room capacity.
 */
public class Room {

    /**
     * ID of the room.
     */
    private String id;

    /**
     * Name of the room.
     */
    private String name;

    /**
     * The capacity of the room.
     */
    private int capacity;

    /**
     * The setup of the room.
     */
    private Type setup;

    /**
     * The recording capabilityof the room.
     */
    private String recorded;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(final int capacity) {
        this.capacity = capacity;
    }

    public Type getSetup() {
        return setup;
    }

    public void setSetup(final Type setup) {
        this.setup = setup;
    }

    public String getRecorded() {
        return recorded;
    }

    public void setRecorded(final String recorded) {
        this.recorded = recorded;
    }

    @Override
    public String toString() {
        return createToString(this, map(
                "id", getId(),
                "name", getName(),
                "capacity", getCapacity(),
                "setup", getSetup(),
                "recorded", getRecorded()
        )) + " extends " + super.toString();
    }

    public enum Type {

        theatre,
        classroom,
        special;
    }
}
