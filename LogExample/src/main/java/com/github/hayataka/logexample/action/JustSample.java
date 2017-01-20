package com.github.hayataka.logexample.action;

import java.net.URL;
import java.util.StringJoiner;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hayataka.logexample.dto.SampleDto;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.classic.util.ContextInitializer;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import lombok.Data;

@Data
public class JustSample {

	// TODO 変更したいポイント 下記及び、よりシンプルな方法は何か？
	private static final Logger log = LoggerFactory.getLogger(JustSample.class);
	private        final Logger log2 = LoggerFactory.getLogger(this.getClass());



	public int add(int x, int y) {

		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		lc.setPackagingDataEnabled(true);

		log.debug("{} + {}", x, y);

		return x + y;
	}

	public void test(SampleDto dto) {

		if (log.isDebugEnabled()) {
			// log.debug(dto); debugの１引数は文字列のみ
			log.debug("データはこれ：{}", dto.toString());
		}
		// TODO 上記がNGな理由について


		log.debug("データはこれ：{}", dto);



	}

	// TODO interceptor系についての話
	public void interceptor(MethodInvocation ic) {

		// Object[] arguments = ic.getArguments();

	}

	private ObjectMapper mapper = new ObjectMapper();

	public void interceptor(Object... args) {

		if (log.isDebugEnabled()) {
			StringJoiner sj = new StringJoiner(",");
			if (args != null) {
				for (Object arg : args) {
					try {
						sj.add(mapper.writeValueAsString(arg));
					} catch (JsonProcessingException e) {
						throw new RuntimeException(e);
					}
				}
			}
			log.debug("data:", sj);
		}
	}

	public static void reloadLogger() {
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

		ContextInitializer ci = new ContextInitializer(loggerContext);
		URL url = ci.findURLOfDefaultConfigurationFile(true);

		try {
			JoranConfigurator configurator = new JoranConfigurator();
			configurator.setContext(loggerContext);
			loggerContext.reset();
			configurator.doConfigure(url);
		} catch (JoranException je) {
			// StatusPrinter will handle this
		}
		StatusPrinter.printInCaseOfErrorsOrWarnings(loggerContext);
	}
}
