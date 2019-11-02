/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015-2019 TweetWallFX
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

import java.util.Collections;
import java.util.List;
import static org.tweetwallfx.util.ToString.createToString;
import static org.tweetwallfx.util.ToString.map;

/**
 * A Schedule is a list of time slots for a specific day. Each slot is either a
 * {@link ScheduleSlot} with a {@link ScheduleSlot#getBreak() break slot} or a
 * {@link ScheduleSlot} with a {@link ScheduleSlot#getTalk() talk slot}.
 */
public class Schedule {

    private List<ScheduleSlot> slots;

    public List<ScheduleSlot> getSlots() {
        return null == slots
                ? Collections.emptyList()
                : Collections.unmodifiableList(slots);
    }

    public void setSlots(final List<ScheduleSlot> slots) {
        this.slots = slots;
    }

    @Override
    public String toString() {
        return createToString(this, map(
                "slots", getSlots()
        )) + " extends " + super.toString();
    }
}
