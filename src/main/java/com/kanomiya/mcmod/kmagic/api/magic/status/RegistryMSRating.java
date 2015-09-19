package com.kanomiya.mcmod.kmagic.api.magic.status;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.stats.Achievement;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatisticsFile;

/**
 * @author Kanomiya
 *
 */
public class RegistryMSRating {

	private RegistryMSRating() {  }

	private static Map<StatBase, Float> statMap = Maps.newHashMap();
	private static Map<Achievement, Integer> achievementMap = Maps.newHashMap();

	public static void registerStat(StatBase stat, float rate) {
		statMap.put(stat, rate);
	}

	public static void registerAchievement(Achievement achievement, int extraMp) {
		achievementMap.put(achievement, extraMp);
	}


	public static int evaluate(StatisticsFile stats) {
		if (stats == null) return 0;

		int extraMp = 0;

		for (StatBase stat: statMap.keySet()) {
			int statData = stats.readStat(stat);

			extraMp += (statMap.get(stat) *statData);
		}

		for (Achievement ac: achievementMap.keySet()) {
			if (stats.hasAchievementUnlocked(ac)) {
				extraMp += achievementMap.get(ac);
			}
		}

		return extraMp;
	}


}
