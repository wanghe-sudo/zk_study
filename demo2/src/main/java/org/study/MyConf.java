package org.study;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * zk节点存数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyConf {
    // 随便放一个id
    private Long id;
    // 随便放一个String，说明有信息保存在了这个node节点上
    private String conf;
}
