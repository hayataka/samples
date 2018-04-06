package org.mybatis.sample.mapper;

import java.util.List;
import java.util.Map;

public interface SampleMapper {

	List<Map<String, Object>> selectTest();
}
