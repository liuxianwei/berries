# Berries-demo-curd-sharding

## 项目介绍
	本项目使用Berries框架实现了数据表的四种水平切分功能。

## 安装教程
	1. 克隆代码 https://gitee.com/liuxianwei/berries.git
	2. Maven加入依赖
		```
		<dependency>
			<groupId>com.lee</groupId>
			<artifactId>berries-core</artifactId>
			<version>2.0.0</version>
		</dependency>
		```
	3. 使用src/main/resources目录下的berries-demo-sharding.sql脚本项目所需的数据表。
	4. 修改src/main/resources目录下generatorConfig.xml文件的数据库连接信息，使用maven  
	   运行mybatis-generator:generate生产PO（项目已经生成好了）,需要配置mybatis的生成器为本项目的生成器，
	   配置方法如下：
	   ```
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
		```
	5. 修改src/test/resources目录下berries-curd-test.properties配置文件中数据库连接信息。
	6. 运行src/test/java包下的测试用例即可。
	7. 如需web方式，则只需将以上说明整合到web项目即可
## 分表规则

	1. mod 取模分表，将表按照指定的字段取模，分散在固定数量的表中，配置方式为{"rule":"mod","tableName":"sharding_mod","columnName":"id","modCount":10}
	2. value 按照值分表， 按照表中某一个字段的值进行分表处理，配置方式为 {"rule":"value","tableName":"sharding_value","columnName":"user_id"}
	3. range 范围分表，按照字段值的范围分表。配置方式为{"rule":"range","tableName":"sharding_range","columnName":"id",rangeCount:100}
	4. date 日期分表，按照表中的时间字段进行分表，配置方式为{"rule":"date","tableName":"sharding_date","columnName":"create_time",dateRule:"DAY"}，其中dateRule可以取值DAY、MONTH、YEAR，分别为按照日月年的方式分表
