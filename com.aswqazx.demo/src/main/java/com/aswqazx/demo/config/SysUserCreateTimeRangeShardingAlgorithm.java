package com.aswqazx.demo.config;

import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;

/**
 * 精确分片
 * @author OMNIS
 */
public class SysUserCreateTimeRangeShardingAlgorithm implements RangeShardingAlgorithm<Date> {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Date> shardingValue) {
        Collection<String> result = new LinkedHashSet<String>();
        Range<Date> range = shardingValue.getValueRange();
        // 获取范围
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String end = formatter.format(range.lowerEndpoint());
        String start = formatter.format(range.upperEndpoint());
        result.add(shardingValue.getLogicTableName() + "_" + start);
        if (!end.equals(start)) {
            result.add(shardingValue.getLogicTableName() + "_" + end);
        }
        return result;
    }
}
