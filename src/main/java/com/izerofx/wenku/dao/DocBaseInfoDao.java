package com.izerofx.wenku.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.izerofx.wenku.domain.DocBaseInfo;

/**
 * 
 * 类名称：DocBaseInfoDao<br>
 * 类描述：文档基本信息数据访问接口<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月8日 下午2:30:10<br>
 * @version v1.0
 *
 */
public interface DocBaseInfoDao extends JpaRepository<DocBaseInfo, String>, DocBaseInfoDaoCustom<DocBaseInfo, String> {

    /**
     * 通过用户id，文件id删除文件
     * @param userId
     * @param fileId
     */
    @Query(nativeQuery = true, value = "delete from doc_base_info where user_id = ?1 and file_id = ?2")
    @Modifying
    public void deleteByUserIdAndFielId(String userId, String fileId);
}
