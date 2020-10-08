package com.baiyi.caesar.service.auth.impl;

import com.baiyi.caesar.domain.DataTable;
import com.baiyi.caesar.domain.generator.caesar.OcAuthUserRole;
import com.baiyi.caesar.domain.param.auth.UserRoleParam;
import com.baiyi.caesar.mapper.caesar.OcAuthUserRoleMapper;
import com.baiyi.caesar.service.auth.OcAuthUserRoleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author baiyi
 * @Date 2020/2/12 3:21 下午
 * @Version 1.0
 */
@Service
public class OcAuthUserRoleServiceImpl implements OcAuthUserRoleService {

    @Resource
    private OcAuthUserRoleMapper ocAuthUserRoleMapper;

    @Override
    public int countByRoleId(int roleId) {
        Example example = new Example(OcAuthUserRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleId", roleId);
        return ocAuthUserRoleMapper.selectCountByExample(example);
    }

    @Override
    public DataTable<OcAuthUserRole> queryOcAuthUserRoleByParam(UserRoleParam.PageQuery pageQuery) {
        Page page = PageHelper.startPage(pageQuery.getPage(), pageQuery.getLength().intValue());
        List<OcAuthUserRole> ocAuthUserRoleList = ocAuthUserRoleMapper.queryOcAuthUserRoleByParam(pageQuery);
        return new DataTable<>(ocAuthUserRoleList, page.getTotal());
    }

    @Override
    public void addOcAuthUserRole(OcAuthUserRole ocAuthUserRole) {
        ocAuthUserRoleMapper.insert(ocAuthUserRole);
    }

    @Override
    public void deleteOcAuthUserRoleById(int id) {
        ocAuthUserRoleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public OcAuthUserRole queryOcAuthUserRoleById(int id) {
        return ocAuthUserRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public OcAuthUserRole queryOcAuthUserRoleByUniqueKey(OcAuthUserRole ocAuthUserRole) {
        Example example = new Example(OcAuthUserRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleId", ocAuthUserRole.getRoleId());
        criteria.andEqualTo("username", ocAuthUserRole.getUsername());
        return ocAuthUserRoleMapper.selectOneByExample(example);
    }

    @Override
    public boolean authenticationByUsernameAndResourceName(String username, String resourceName) {
        return ocAuthUserRoleMapper.authenticationByUsernameAndResourceName(username, resourceName) >= 1;
    }
}
