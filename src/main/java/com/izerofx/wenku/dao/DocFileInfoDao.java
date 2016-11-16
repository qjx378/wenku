package com.izerofx.wenku.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.izerofx.wenku.domain.DocFileInfo;

/**
 * 
 * 类名称：DocFileInfoDao<br>
 * 类描述：文档文件信息数据访问接口<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月8日 上午10:53:37<br>
 * @version v1.0
 *
 */
@Repository
public interface DocFileInfoDao extends JpaRepository<DocFileInfo, String> {

    /**
     * 通过文件hash判断文件是否已存在服务器
     * @param hash
     * @return
     */
    @Query(nativeQuery = true, value = "select count(1) from doc_file_info where hash = ?1")
    public int isExistByHash(String hash);

    /**
     * 通过用户id，文件id查找文件信息
     * @param userId
     * @param id
     * @return
     */
    public DocFileInfo findByUserIdAndId(String userId, String id);
}
