package com.github.hayataka.logexample.action;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hayataka.logexample.dto.SampleDto;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.classic.util.ContextInitializer;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import lombok.Data;

@Data
public class JustSample {

	// TODO 変更したいポイント
	private static final Logger log = LoggerFactory.getLogger(JustSample.class);
		
	public int add (int x, int y) {
		
		
		
		 LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		  lc.setPackagingDataEnabled(true);


		  
		
		log.debug("{} + {}", x , y);
		
		return x + y;
	}
	
	public void test(SampleDto dto) {
		
		if (log.isDebugEnabled()) {
//			log.debug(dto);  debugの１引数は文字列のみ
			log.debug(dto.toString());
		}
		// TODO 上記がNGな理由について
		
		
		
		
		
		
		log.debug("出力する場合は通常はこうすること：{}", dto);
		
		
		
		
		
		
		
		
		
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
