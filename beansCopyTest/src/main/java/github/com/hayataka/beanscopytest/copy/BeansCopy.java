package github.com.hayataka.beanscopytest.copy;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

public class BeansCopy {

	public static <T> T copy(Object src, T dest) {
		try {
			BeanUtils.copyProperties(dest, src);
		} catch (IllegalAccessException | InvocationTargetException e) {
		
			e.printStackTrace();
		}
		return dest;
	}

	public static <T,S> void copyList(List<S> src, T[] dest) {
		try {
			final int MAX = src.size();
			for (int i = 0; i < MAX; i++) {
				final S data = src.get(i);
				BeanUtils.copyProperties(dest[i], data);
			}
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
