package com.baiyi.caesar.controller;

import com.baiyi.caesar.domain.BusinessWrapper;
import com.baiyi.caesar.domain.DataTable;
import com.baiyi.caesar.domain.HttpResult;
import com.baiyi.caesar.domain.param.gitlab.GitlabInstanceParam;
import com.baiyi.caesar.domain.param.gitlab.GitlabProjectParam;
import com.baiyi.caesar.domain.vo.gitlab.GitlabInstanceVO;
import com.baiyi.caesar.domain.vo.gitlab.GitlabProjectVO;
import com.baiyi.caesar.facade.GitlabFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @Author baiyi
 * @Date 2020/7/20 8:41 上午
 * @Version 1.0
 */
@RestController
@RequestMapping("/gitlab")
@Api(tags = "Gitlab管理")
public class GitlabController {

    @Resource
    private GitlabFacade gitlabFacade;

    @ApiOperation(value = "分页查Gitlab实例配置")
    @PostMapping(value = "/instance/page/query", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpResult<DataTable<GitlabInstanceVO.Instance>> queryGitlabInstancePage(@RequestBody @Valid GitlabInstanceParam.PageQuery pageQuery) {
        return new HttpResult<>(gitlabFacade.queryGitlabInstancePage(pageQuery));
    }

    @ApiOperation(value = "新增Gitlab实例配置")
    @PostMapping(value = "/instance/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpResult<Boolean> addGitlabInstance(@RequestBody @Valid GitlabInstanceVO.Instance instance) {
        return new HttpResult<>(gitlabFacade.addGitlabInstance(instance));
    }

    @ApiOperation(value = "更新Gitlab实例配置")
    @PutMapping(value = "/instance/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpResult<Boolean> updateGitlabInstance(@RequestBody @Valid GitlabInstanceVO.Instance instance) {
        return new HttpResult<>(gitlabFacade.updateGitlabInstance(instance));
    }

    @ApiOperation(value = "删除Gitlab实例配置")
    @DeleteMapping(value = "/instance/del", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpResult<Boolean> deleteGitlabInstanceById(@RequestParam int id) {
        return new HttpResult<>(gitlabFacade.deleteGitlabInstanceById(id));
    }

    @ApiOperation(value = "分页查Gitlab项目")
    @PostMapping(value = "/project/page/query", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpResult<DataTable<GitlabProjectVO.Project>> queryGitlabProjectPage(@RequestBody @Valid GitlabProjectParam.PageQuery pageQuery) {
        return new HttpResult<>(gitlabFacade.queryGitlabProjectPage(pageQuery));
    }

    @ApiOperation(value = "同步Gitlab项目")
    @GetMapping(value = "/project/sync",  produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpResult<Boolean> syncGitlabInstanceProject(@Valid int instanceId) {
        gitlabFacade.syncGitlabInstanceProject(instanceId);
        return new HttpResult<>(BusinessWrapper.SUCCESS);
    }
}
