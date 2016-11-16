package com.izerofx.wenku.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.izerofx.framework.basic.common.service.BaseService;
import com.izerofx.wenku.domain.DocBaseInfo;

/**
 * 
 * 类名称：DocBaseInfoService<br>
 * 类描述：文档基本信息服务接口<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月8日 下午2:33:18<br>
 * @version v1.0
 *
 */
public interface DocBaseInfoService extends BaseService<DocBaseInfo, String> {

    /**
     * 分页查询所有
     * @param docBaseInfo
     * @param pageable
     * @return
     */
    public Page<DocBaseInfo> findAll(DocBaseInfo docBaseInfo, Pageable pageable);

    /**
     * 通过用户id分页获取文档
     * @param userId
     * @param pageable
     * @return
     */
    public Page<DocBaseInfo> findAllByUserId(String userId, Pageable pageable);

    /**
     * 通过id查询文档信息
     * @param id
     * @return
     */
    public DocBaseInfo findById(String id);
}
