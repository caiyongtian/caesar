package com.baiyi.caesar.domain.vo.jenkins;

import com.baiyi.caesar.domain.vo.tag.TagVO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @Author baiyi
 * @Date 2020/7/24 1:50 下午
 * @Version 1.0
 */
public class JobTplVO {

    @Data
    @NoArgsConstructor
    @ApiModel
    public static class JobTpl {

        private List<TagVO.Tag> tags;
        private JenkinsInstanceVO.Instance jenkinsInstance;

        private Integer id;
        private Integer jenkinsInstanceId;
        private String name;
        private String tplName;
        private String tplType;
        private Integer tplVersion;
        private String tplHash;
        private Date updateTime;
        private Date createTime;
        private String tplContent;
        private String parameterYaml;
        private Boolean supportRollback;
        private Integer rollbackType;
        private String comment;

    }

}
