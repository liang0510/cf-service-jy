package com.cf;

import com.cf.core.mutidatasource.config.DynamicDataSourceRegister;
import com.cf.core.util.SpringUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @Project : cf-core
 * @Package Name : com.cf.dms
 * @Description : TODO
 * @Author : chenfeng
 * @Creation Date : 2018年07月31日 14:56
 * @ModificationHistory Who When What
 * _________ ________________ ____________________________________________
 */
@SpringBootApplication
@ServletComponentScan //启动器启动时，扫描本目录以及子目录带有的webservlet注解的
@MapperScan({"com.cf.*.mybatis","com.cf.*.base.mybatis"})
@EnableTransactionManagement
@Import({DynamicDataSourceRegister.class})
public class KindergartenApplication {


    @Bean(name = "txManager1")
    public PlatformTransactionManager txManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    public static void main(String[] args) {
        //基础参数

        //项目主程序
        ApplicationContext ctx = SpringApplication.run(KindergartenApplication.class,args);

        //框架工具
        SpringUtil.setApplicationContext(ctx);
        //log.info("============= SpringBoot Start Success =============");
    }
}
