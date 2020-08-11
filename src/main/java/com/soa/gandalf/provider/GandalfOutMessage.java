package com.soa.gandalf.provider;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * out message define
 * 调用服务后 返回的统一对象
 *
 * @author think <syj247@qq.com>、
 * @date 2020/8/10、19:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GandalfOutMessage {

    GandalfInMessage gandalfInMessage;
    String result;


}
