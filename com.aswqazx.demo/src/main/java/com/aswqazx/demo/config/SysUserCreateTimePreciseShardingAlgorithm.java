package com.aswqazx.demo.config;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

/**
 * 精确分片
 * @author OMNIS
 */
public class SysUserCreateTimePreciseShardingAlgorithm implements PreciseShardingAlgorithm<Date> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> shardingValue) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String dateString = formatter.format(shardingValue.getValue());

        StringBuffer tableName = new StringBuffer();
        tableName.append(shardingValue.getLogicTableName()).append("_").append(dateString);
        return tableName.toString();
    }
}
