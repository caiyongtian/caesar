package com.baiyi.caesar.facade.impl;

import com.baiyi.caesar.builder.CaesarInstanceBuilder;
import com.baiyi.caesar.common.util.HostUtils;
import com.baiyi.caesar.domain.generator.caesar.CsInstance;
import com.baiyi.caesar.domain.vo.caesar.HealthVO;
import com.baiyi.caesar.facade.CaesarInstanceFacade;
import com.baiyi.caesar.service.instance.CsInstanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Author baiyi
 * @Date 2020/9/7 11:06 上午
 * @Version 1.0
 */
@Slf4j
@Service
public class CaesarInstanceFacadeImpl implements CaesarInstanceFacade, InitializingBean {

    @Resource
    private CsInstanceService csInstanceService;

    private static final String HEALTH_OK = "OK";

    @Override
    public boolean isHealth() {
        HealthVO.Health health = checkHealth();
        return HEALTH_OK.equals(health.getStatus());
    }

    @Override
    public HealthVO.Health checkHealth() {
        try {
            InetAddress inetAddress = HostUtils.getInetAddress();
            CsInstance csInstance = csInstanceService.queryCsInstanceByHostIp(inetAddress.getHostAddress());
            if (csInstance == null)
                return getHealth("ERROR");
            if (csInstance.getIsActive()) {
                return getHealth(HEALTH_OK);
            } else {
                return getHealth("INACTIVE");
            }
        } catch (UnknownHostException ignored) {
        }
        return getHealth("ERROR");
    }

    private HealthVO.Health getHealth(String status) {
        HealthVO.Health health = new HealthVO.Health();
        health.setStatus(status);
        return health;
    }

    private void register() {
        try {
            InetAddress inetAddress = HostUtils.getInetAddress();
            if (csInstanceService.queryCsInstanceByHostIp(inetAddress.getHostAddress()) != null) return;
            CsInstance csInstance = CaesarInstanceBuilder.build(inetAddress);
            csInstanceService.addCsInstance(csInstance);
        } catch (UnknownHostException ignored) {
        }
    }

    @Override
    public void afterPropertiesSet() {
        this.register();
    }

}
