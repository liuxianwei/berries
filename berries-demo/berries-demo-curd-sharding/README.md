# Berries-demo-curd-sharding

#### 项目介绍
本项目使用Berries框架实现了数据表的四种水平切分功能。


#### 安装教程

1. 克隆代码 https://gitee.com/liuxianwei/berries.git
2. Maven加入依赖
	<dependency>
		<groupId>com.lee</groupId>
		<artifactId>berries-core</artifactId>
		<version>2.0.0</version>
	</dependency>
3. 使用src/main/resources目录下的berries-demo.sql脚本项目所需的数据表。
4. 修改src/main/resources目录下generatorConfig.xml文件的数据库连接信息，使用maven  
   运行mybatis-generator:generate生产PO（项目已经生成好了）,需要配置mybatis的生成器为本项目的生成器，
   配置方法如下：
	<plugin>
		<groupId>org.mybatis.generator</groupId>
		<artifactId>mybatis-generator-maven-plugin</artifactId>
		<version>1.3.6</version>
		<dependencies>
			<dependency>
				<groupId>com.lee</groupId>
				<artifactId>berries-mybatis-generator</artifactId>
				<version>1.0.8</version>
			</dependency>
		</dependencies>
	</plugin>
5. 修改src/test/resources目录下berries-demo-curd.properties配置文件中数据库连接信息。
6. 运行src/test/java包下的测试用例即可。
7. 如需web方式，则只需将以上说明整合到web项目即可
