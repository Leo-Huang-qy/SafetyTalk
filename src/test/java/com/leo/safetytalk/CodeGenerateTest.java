package com.leo.safetytalk;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Types;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class CodeGenerateTest {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Test
    public void run() {
        try{
            String author = "leo";
            String tableList = "user";
            String currentDirectory = System.getProperty("user.dir");
            FastAutoGenerator.create(dbUrl, username, password)
                    .globalConfig(builder -> {
                        builder.author(author) // 设置作者
                                .enableSwagger() // 开启 swagger 模式
                                .fileOverride() // 覆盖已生成文件
                                .outputDir(currentDirectory + "\\src\\main\\java"); // 指定输出目录
                    })
                    .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                        int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                        if (typeCode == Types.SMALLINT) {
                            // 自定义类型转换
                            return DbColumnType.INTEGER;
                        }
                        return typeRegistry.getColumnType(metaInfo);

                    }))
                    .packageConfig(builder -> {
                        builder.parent("com.leo.bookbreeze") // 设置父包名
//                                .moduleName("Fitontime") // 设置父包模块名
                                .pathInfo(Collections.singletonMap(OutputFile.xml, currentDirectory + "\\src\\main\\java\\com\\leo\\bookbreeze\\mapper\\xml")); // 设置mapperXml生成路径
                    })
                    .strategyConfig(builder -> {
                        builder.addInclude(tableList); // 设置需要生成的表名
//                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                    })
                    .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                    .execute();
            // 在生成代码后，添加断言以验证文件是否存在
            assertTrue(Files.exists(Paths.get(currentDirectory + "\\src\\main\\java\\com\\leo\\bookbreeze\\mapper\\xml\\UserMapper.xml")));
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }

    }

}
