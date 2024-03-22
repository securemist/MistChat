package cc.xmist.mistchatserver;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.fill.Property;
import org.junit.Test;

import java.sql.Types;

/**
 * mybatis-plus代码生成器
 * https://baomidou.com/pages/981406/#%E5%85%A8%E5%B1%80%E9%85%8D%E7%BD%AE-globalconfig
 */
public class MybatisPlusGenerator {
    @Test
    public void generate() {
        FastAutoGenerator.create(new DataSourceConfig.Builder("jdbc:mysql://127.0.0.1:3306/mistchat", "root", "root"))
                .globalConfig(builder -> {
                    builder.author("securemist")
                            .fileOverride()
                            .outputDir("src/main/java/mp")
                            .disableOpenDir()
                            .disableServiceInterface();
                })
                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    if (typeCode == Types.SMALLINT) {
                        return DbColumnType.INTEGER;
                    }
                    return typeRegistry.getColumnType(metaInfo);
                }))
                .packageConfig(builder -> {
                    builder.parent("cc.xmist.mistchat.server")
                            .moduleName("user")
                            .entity("entity")
                            .serviceImpl("dao")
//                            .controller("controller")
                            .mapper("mapper")
                            .xml("model.xml");
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder()
                            .enableLombok()
                            .enableTableFieldAnnotation()
                            .naming(NamingStrategy.underline_to_camel)
                            .addTableFills(new Property("create_time", FieldFill.INSERT))
                            .addTableFills(new Property("update_time", FieldFill.UPDATE))
                            .enableFileOverride();
                    builder.controllerBuilder()
                            .enableRestStyle();
                    builder.serviceBuilder()
                            .convertServiceImplFileName((entityName) -> entityName + "Dao");
                }).execute();
    }

}
