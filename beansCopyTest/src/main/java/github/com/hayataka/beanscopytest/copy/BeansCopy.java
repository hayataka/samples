package github.com.hayataka.beanscopytest.copy;

import github.com.hayataka.beanscopytest.logic.CreateYield;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BeansCopy {

	private static final Logger log = LogManager.getLogger(BeansCopy.class);

	/**
	 * basic beanUtils copy
	 */
	public static <T> T copy(Object src, T dest) {

		final StopWatch clock = new StopWatch();
		clock.start();

		try {
			BeanUtils.copyProperties(dest, src);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException("beansCopy中にエラーが発生しました", e);
		}
		clock.stop();
		log.info("通常beancopyに {} ミリ秒", clock.getTime());
		return dest;
	}

	/**
	 * list to array support 1
	 * @param src not null
	 * @param dest not null and All Data in array is not null
	 */
	public static <T, S> void copyList(List<S> src, T[] dest) {
		final StopWatch clock = new StopWatch();
		clock.start();

		try {
			final int MAX = src.size();
			for (int i = 0; i < MAX; i++) {
				final S data = src.get(i);
				BeanUtils.copyProperties(dest[i], data);
			}
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException("beansCopy中にエラーが発生しました", e);
		}
		
		clock.stop();
		log.info("Listから配列コピー、事前初期化要パターンに {} ミリ秒", clock.getTime());
	}

	/**
	 * list to array support 2
	 * @param src not null
	 * @param dest not null  ( but all data in arrays is null)
	 */
	public static <T, S> void copyList(List<S> src, T[] dest, CreateYield<T> creator) {

		final StopWatch clock = new StopWatch();
		clock.start();

		try {
			final int MAX = src.size();
			for (int i = 0; i < MAX; i++) {
				final S data = src.get(i);
				dest[i] = creator.create();
				BeanUtils.copyProperties(dest[i], data);
			}
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException("beansCopy中にエラーが発生しました", e);
		}
		clock.stop();
		log.info("Listから配列コピー、yieldパターンに {} ミリ秒", clock.getTime());
	}
}
