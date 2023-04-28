package me.chell.blackout.api.util

import me.chell.blackout.api.event.EventHandler
import me.chell.blackout.api.events.PlayerTickEvent
import me.chell.blackout.impl.features.client.ColorsFeature

object Rainbow {
    val color = Color(-1)

    private var hue = 0f

    @EventHandler
    fun onTick(event: PlayerTickEvent) {
        hue += ColorsFeature.rainbowSpeed.value / 100f
        if(hue >= 1f) hue = 0f

;        color.rgb = java.awt.Color.HSBtoRGB(hue, 1f, 1f)
    }

}