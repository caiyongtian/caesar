package com.baiyi.caesar.dingtalk.impl;

import com.baiyi.caesar.common.base.BuildType;
import com.baiyi.caesar.common.base.JobType;
import com.baiyi.caesar.dingtalk.IDingtalkNotify;
import com.baiyi.caesar.dingtalk.builder.DingtalkTemplateBuilder;
import com.baiyi.caesar.dingtalk.builder.DingtalkTemplateMap;
import com.baiyi.caesar.jenkins.context.BuildJobContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author baiyi
 * @Date 2020/8/22 11:09 上午
 * @Version 1.0
 */
@Slf4j
@Component("PythonNotify")
public class PythonNotify extends BaseDingtalkNotify implements IDingtalkNotify {

    @Override
    public String getKey() {
        return JobType.PYTHON.getType();
    }

    @Override
    protected int getBuildType(){
        return BuildType.BUILD.getType();
    }

    @Override
    protected Map<String, Object> acqTemplateContent( int noticePhase, BuildJobContext context) {
        DingtalkTemplateMap templateMap = DingtalkTemplateBuilder.newBuilder()
                .paramEntries(super.acqTemplateContent(noticePhase, context))
                .paramEntryVersionName(context.getJobBuild().getVersionName())
                .build();

        return templateMap.getTemplate();
    }

}