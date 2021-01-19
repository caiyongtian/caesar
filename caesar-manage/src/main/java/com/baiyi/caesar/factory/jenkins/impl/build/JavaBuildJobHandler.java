package com.baiyi.caesar.factory.jenkins.impl.build;

import com.baiyi.caesar.common.base.JobType;
import com.baiyi.caesar.domain.generator.caesar.CsApplication;
import com.baiyi.caesar.domain.generator.caesar.CsCiJob;
import com.baiyi.caesar.domain.generator.caesar.CsCiJobBuild;
import com.baiyi.caesar.domain.generator.caesar.CsJobBuildArtifact;
import com.baiyi.caesar.domain.param.jenkins.JobBuildParam;
import com.baiyi.caesar.factory.jenkins.DeploymentJobHandlerFactory;
import com.baiyi.caesar.factory.jenkins.IBuildJobHandler;
import com.baiyi.caesar.factory.jenkins.IDeploymentJobHandler;
import com.baiyi.caesar.factory.jenkins.builder.JenkinsJobParamsBuilder;
import com.baiyi.caesar.factory.jenkins.builder.JenkinsJobParamsMap;
import com.baiyi.caesar.factory.jenkins.monitor.MonitorHandler;
import com.baiyi.caesar.jenkins.context.JobParametersContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.baiyi.caesar.common.base.Build.*;

/**
 * @Author baiyi
 * @Date 2020/8/18 3:54 下午
 * @Version 1.0
 */
@Slf4j
@Component("JavaCiJobHandler")
public class JavaBuildJobHandler extends BaseBuildJobHandler implements IBuildJobHandler {

    @Resource
    private MonitorHandler monitorHandler;

    @Override
    public String getKey() {
        return JobType.JAVA.getType();
    }

    @Override
    protected JobParametersContext buildJobParametersContext(CsApplication csApplication, CsCiJob csCiJob, JobBuildParam.BuildParam buildParam) {
        JobParametersContext context = super.buildJobParametersContext(csApplication, csCiJob, buildParam);

        JenkinsJobParamsMap jenkinsJobParamsMap = JenkinsJobParamsBuilder.newBuilder()
                .paramEntry(IS_SONAR, buildParam)
                .paramEntry(JOB_BUILD_NUMBER, String.valueOf(csCiJob.getJobBuildNumber()))
                .paramEntryIsRollback(acqRollbackArtifact(buildParam)) // Rollback
                .build();
        context.putParams(jenkinsJobParamsMap.getParams());
        if (isRollback(buildParam)) {
            CsCiJobBuild csCiJobBuild = queryCiJobBuildById(Integer.parseInt(buildParam.getParamMap().get(ROLLBACK_JOB_BUILD_ID)));
            context.setVersionName(csCiJobBuild.getVersionName());
            context.setVersionDesc(csCiJobBuild.getVersionDesc());
        }
        return context;
    }

    private CsJobBuildArtifact acqRollbackArtifact(JobBuildParam.BuildParam buildParam) {
        if (isRollback(buildParam)) {
            try {
                IDeploymentJobHandler deploymentJobHandler = DeploymentJobHandlerFactory.getDeploymentJobByKey(JobType.JAVA_DEPLOYMENT.getType());
                List<CsJobBuildArtifact> artifacts = deploymentJobHandler.acqBuildArtifacts(Integer.parseInt(buildParam.getParamMap().get(ROLLBACK_JOB_BUILD_ID)));
                if (!CollectionUtils.isEmpty(artifacts))
                    return artifacts.get(0);
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    @Override
    protected void updateHostStatus(CsApplication csApplication, Map<String, String> params, int status) {
        monitorHandler.updateHostStatus(csApplication, params, status);
    }

    @Override
    protected boolean isLimitConcurrentJob() {
        return true;
    }

}
