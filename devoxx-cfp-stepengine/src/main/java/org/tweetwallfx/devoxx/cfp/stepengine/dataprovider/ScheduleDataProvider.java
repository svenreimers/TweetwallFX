/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2022 TweetWallFX
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
package org.tweetwallfx.devoxx.cfp.stepengine.dataprovider;

import java.time.LocalDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.tweetwallfx.devoxx.api.cfp.client.CFPClient;
import org.tweetwallfx.devoxx.api.cfp.client.Schedule;
import org.tweetwallfx.devoxx.api.cfp.client.ScheduleSlot;
import org.tweetwallfx.stepengine.api.DataProvider;
import org.tweetwallfx.stepengine.api.config.StepEngineSettings;
import static org.tweetwallfx.util.Nullable.valueOrDefault;

/**
 * DataProvider Implementation for Schedule Data
 */
public class ScheduleDataProvider implements DataProvider, DataProvider.Scheduled {

    private volatile List<ScheduleSlot> scheduleSlots = Collections.emptyList();
    private final Config config;

    private ScheduleDataProvider(final Config config) {
        this.config = config;
    }

    @Override
    public ScheduledConfig getScheduleConfig() {
        return config;
    }

    @Override
    public void run() {
        try {
            CFPClient.getClient()
                    .getSchedule(Optional
                            .ofNullable(System.getProperty("org.tweetwallfx.scheduledata.day"))
                            .orElseGet(()
                                    -> LocalDateTime.now(ZoneId.systemDefault())
                                    .getDayOfWeek()
                                    .getDisplayName(TextStyle.FULL, Locale.ENGLISH)
                                    .toLowerCase(Locale.ENGLISH)))
                    .map(Schedule::getSlots)
                    .ifPresent(slots -> scheduleSlots = slots);
        } catch (Exception ex) {
            LogManager.getLogger().error("Exception occurred constructing CFP client instance: ", ex);
        }
    }

    public List<SessionData> getFilteredSessionData() {
        ZoneId zoneId = Optional.ofNullable(System.getProperty("org.tweetwallfx.scheduledata.zone"))
                .map(ZoneId::of)
                .orElseGet(ZoneId::systemDefault);
        OffsetTime liveOffset = Optional.ofNullable(System.getProperty("org.tweetwallfx.scheduledata.time"))
                .map(OffsetTime::parse)
                .orElseGet(() -> OffsetTime.now(zoneId));
        return SessionData.from(scheduleSlots, liveOffset, zoneId);
    }

    public static class FactoryImpl implements DataProvider.Factory {

        @Override
        public DataProvider create(final StepEngineSettings.DataProviderSetting dataProviderSetting) {
            return new ScheduleDataProvider(dataProviderSetting.getConfig(Config.class));
        }

        @Override
        public Class<ScheduleDataProvider> getDataProviderClass() {
            return ScheduleDataProvider.class;
        }
    }

    /**
     * POJO used to configure {@link ScheduleDataProvider}.
     *
     * <p>
     * Param {@code initialDelay} The type of scheduling to perform. Defaults to
     * {@link ScheduleType#FIXED_RATE}.
     *
     * <p>
     * Param {@code initialDelay} Delay until the first execution in seconds.
     * Defaults to {@code 0L}.
     *
     * <p>
     * Param {@code scheduleDuration} Fixed rate of / delay between consecutive
     * executions in seconds. Defaults to {@code 300L}.
     */
    private static record Config(
            ScheduleType scheduleType,
            Long initialDelay,
            Long scheduleDuration) implements ScheduledConfig {

        @SuppressWarnings("unused")
        public Config(
                final ScheduleType scheduleType,
                final Long initialDelay,
                final Long scheduleDuration) {
            this.scheduleType = valueOrDefault(scheduleType, ScheduleType.FIXED_RATE);
            this.initialDelay = valueOrDefault(initialDelay, 0L);
            this.scheduleDuration = valueOrDefault(scheduleDuration, 5 * 60L);
        }
    }
}
