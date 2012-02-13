package com.github.mobile.gauges.ui.airtraffic;

import static android.graphics.Bitmap.Config.ARGB_8888;
import static android.util.DisplayMetrics.DENSITY_DEFAULT;
import static com.github.mobile.gauges.R.drawable.pin0;
import static com.github.mobile.gauges.R.drawable.pin1;
import static com.github.mobile.gauges.R.drawable.pin2;
import static com.github.mobile.gauges.R.drawable.pin3;
import static com.github.mobile.gauges.R.drawable.pin4;
import static com.github.mobile.gauges.R.drawable.pin5;
import static com.github.mobile.gauges.R.drawable.pin6;
import static com.github.mobile.gauges.R.drawable.pin7;
import static com.github.mobile.gauges.R.drawable.pin8;
import static com.github.mobile.gauges.R.drawable.pin9;
import static com.github.mobile.gauges.R.drawable.ring0;
import static com.github.mobile.gauges.R.drawable.ring1;
import static com.github.mobile.gauges.R.drawable.ring2;
import static com.github.mobile.gauges.R.drawable.ring3;
import static com.github.mobile.gauges.R.drawable.ring4;
import static com.github.mobile.gauges.R.drawable.ring5;
import static com.github.mobile.gauges.R.drawable.ring6;
import static com.github.mobile.gauges.R.drawable.ring7;
import static com.github.mobile.gauges.R.drawable.ring8;
import static com.github.mobile.gauges.R.drawable.ring9;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

import com.github.mobile.gauges.core.Gauge;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that maps pins and rings to a specific gauge to be used in the air traffic view
 */
public class AirTrafficResourceProvider {

    private final Bitmap[] pins = new Bitmap[10];

    private final Bitmap[] rings = new Bitmap[10];

    private final int pinHeight;

    private final int pinWidth;

    private final int ringHeight;

    private final int ringWidth;

    private int pinIndex = 0;

    private final Map<String, Integer> gaugeColors = new LinkedHashMap<String, Integer>();

    /**
     * Create resource provider
     *
     * @param resources
     */
    public AirTrafficResourceProvider(final Resources resources) {
        Options options = new Options();
        options.inDither = true;
        options.inPreferredConfig = ARGB_8888;

        // Load all the pin and ring images
        int[] pinDrawables = new int[]{pin0, pin1, pin2, pin3, pin4, pin5, pin6, pin7, pin8, pin9};
        int[] ringDrawables = new int[]{ring0, ring1, ring2, ring3, ring4, ring5, ring6, ring7, ring8, ring9};
        for (int colourIndex=0; colourIndex<10; ++colourIndex) {
            pins[colourIndex] = BitmapFactory.decodeResource(resources, pinDrawables[colourIndex], options);
            rings[colourIndex] = BitmapFactory.decodeResource(resources, ringDrawables[colourIndex], options);
        }

        float pinScale = (float) pins[0].getDensity() / DENSITY_DEFAULT;
        pinHeight = Math.round(pins[0].getHeight() / pinScale);
        pinWidth = Math.round(pins[0].getWidth() / pinScale);

        float ringScale = (float) rings[0].getDensity() / DENSITY_DEFAULT;
        ringHeight = Math.round(rings[0].getHeight() / ringScale);
        ringWidth = Math.round(rings[0].getWidth() / ringScale);
    }

    /**
     * Set gauges to be styled
     *
     * @param gauges
     * @return this provider
     */
    public AirTrafficResourceProvider setGauges(final List<Gauge> gauges) {
        gaugeColors.clear();
        pinIndex = 0;
        for (Gauge gauge : gauges) {
            gaugeColors.put(gauge.getId(), pinIndex++);
            if (pinIndex == pins.length)
                pinIndex = 0;
        }
        return this;
    }

    /**
     * Get key for gauge id
     *
     * @param gaugeId
     * @return key, -1 if gauge id has no key
     */
    public int getKey(String gaugeId) {
        Integer key = gaugeColors.get(gaugeId);
        return key != null ? key.intValue() : -1;
    }

    /**
     * Get pin to draw for key
     *
     * @param key
     * @return pin bitmap
     */
    public Bitmap getPin(final int key) {
        return pins[key];
    }

    /**
     * Get pin to draw for gauge with given id
     *
     * @param gaugeId
     * @return bitmap, may be null
     */
    public Bitmap getPin(String gaugeId) {
        int key = getKey(gaugeId);
        return key != -1 ? getPin(key) : null;
    }

    /**
     * Get ring to draw for key
     *
     * @param key
     * @return ring bitmap, may be null
     */
    public Bitmap getRing(final int key) {
        return rings[key];
    }

    /**
     * @return pinHeight
     */
    public int getPinHeight() {
        return pinHeight;
    }

    /**
     * @return pinWidth
     */
    public int getPinWidth() {
        return pinWidth;
    }

    /**
     * @return ringHeight
     */
    public int getRingHeight() {
        return ringHeight;
    }

    /**
     * @return ringWidth
     */
    public int getRingWidth() {
        return ringWidth;
    }
}
