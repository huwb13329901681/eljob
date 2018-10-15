package com.elastic.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

import java.io.InputStream;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author huwb
 * 在@SpringBootApplication注解上加上exclude，解除自动加载DataSourceAutoConfiguration
 */
@ServletComponentScan
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class JobApplication {

	private static final Map map;
	static {

		map = new HashMap();
		map.put("dev","192.168.110.111");
		map.put("test","192.168.1.2");
		map.put("beta","192.168.1.3");
	}
	public static void main(String[] args) {
		InetAddress address ;
		String host;
		try {
			// 读取配置文件中的数据
			Properties properties = new Properties();
			InputStream inputStream = JobApplication.class.getClassLoader().getResourceAsStream("application.properties");
			properties.load(inputStream);
			String env = properties.getProperty("spring.profiles.active");
			address = InetAddress.getLocalHost();
			String hostAddress = address.getHostAddress();
			if ("dev".equals(env)){
				host = (String) map.get("dev");
				if (!host.equals(hostAddress)){
					System.out.println("启动dev服务失败。ip地址错误");
				}
			}
			if ("test".equals(env)){
				host = (String) map.get("test");
				if (!host.equals(hostAddress)){
					System.out.println("启动test服务失败。ip地址错误");
				}
			}
			if ("beta".equals(env)){
				host = (String) map.get("beta");
				if (!host.equals(hostAddress)){
					System.out.println("启动beta服务失败。ip地址错误");
				}
			}
			SpringApplication.run(JobApplication.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
