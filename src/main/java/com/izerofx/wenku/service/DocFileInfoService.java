package com.izerofx.wenku.service;

import com.izerofx.framework.basic.common.service.BaseService;
import com.izerofx.wenku.domain.DocFileInfo;

/**
 * 
 * 类名称：DocFileInfoService<br>
 * 类描述：文档文件信息服务接口<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月8日 上午10:55:09<br>
 * @version v1.0
 *
 */
public interface DocFileInfoService extends BaseService<DocFileInfo, String> {

    /**
     * 通过文件hash判断文件是否已存在服务器
     * @param hash
     * @return
     */
    public boolean isExistByHash(String hash);

    /**
     * 通过用户id，文件id删除文件
     * @param userId
     * @param id
     */
    public void deleteByUserIdAndId(String userId, String id);
}
