package com.atsonic.java8test.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Sonic
 */
@Component
@Slf4j
public class IdGeneratorSnowflake {

	private long workerId = 0L; // 0 ~ 31
	private long datacenterId = 1L; // 0 ~ 31
	private Snowflake snowflake = IdUtil.createSnowflake(workerId, datacenterId);

	@PostConstruct
	public void init() {
		try {
			workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
			log.info("当前机器的workId：{}", workerId);
		} catch (Exception e) {
			e.printStackTrace();
			log.warn("当前机器的workId获取失败：{}", e);
			workerId = NetUtil.getLocalhostStr().hashCode();
		}
	}

	public synchronized long snowflakeId() {
		return snowflake.nextId();
	}

	public synchronized long snowflakeId(long workerId, long datacenterId) {
		Snowflake snowflake = IdUtil.createSnowflake(workerId, datacenterId);
		return snowflake.nextId();
	}

	public static void main(String[] args) {
//		System.out.println(new IdGeneratorSnowflake().snowflakeId());
		IdGeneratorSnowflake generatorSnowflake = new IdGeneratorSnowflake();

		ExecutorService threadPool = Executors.newFixedThreadPool(5);
		for (int i = 0; i < 20; i++) {
			threadPool.submit(() -> {
				System.out.println(generatorSnowflake.snowflakeId());
			});
		}
		threadPool.shutdown();
	}

}
