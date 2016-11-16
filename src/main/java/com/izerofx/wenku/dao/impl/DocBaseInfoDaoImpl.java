package com.izerofx.wenku.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.izerofx.framework.basic.util.StringUtils;
import com.izerofx.framework.core.persistence.PagingHibernateJdbcDao;
import com.izerofx.wenku.dao.DocBaseInfoDaoCustom;
import com.izerofx.wenku.domain.DocBaseInfo;

/**
 * 
 * 类名称：DocBaseInfoDaoImpl<br>
 * 类描述：文档基本信息数据自定义访问接口实现<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月8日 下午2:32:30<br>
 * @version v1.0
 *
 */
@Repository
public class DocBaseInfoDaoImpl implements DocBaseInfoDaoCustom<DocBaseInfo, String> {

    @Autowired
    private PagingHibernateJdbcDao dao;

    /*
     * (non-Javadoc)
     * @see com.izerofx.wenku.dao.DocBaseInfoDaoCustom#findAll(java.lang.Object, org.springframework.data.domain.Pageable)
     */
    @Override
    public Page<DocBaseInfo> findAll(DocBaseInfo baseInfo, Pageable pageable) {
        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<>();

        sql.append("select");
        sql.append("  a.id, a.title, a.summary, a.type, a.create_time, a.view_count, a.user_id,");
        sql.append("  b.thum_path, b.pages, b.ext, b.con_state, c.nick_name");
        sql.append(" from doc_base_info a");
        sql.append(" left join doc_file_info b ON b.id = a.file_id");
        sql.append(" left join user_info c ON c.id = a.user_id");
        sql.append(" where 1 = 1");

        //按用户id查询
        if (baseInfo != null && StringUtils.isNotBlank(baseInfo.getUserId())) {
            sql.append(" and a.user_id = ?");
            params.add(baseInfo.getUserId());
        }

        //按转换状态查询
        if (baseInfo != null && baseInfo.getConState() > -1) {
            sql.append(" and b.con_state = ?");
            params.add(baseInfo.getConState());
        }

        sql.append(" order by a.create_time desc");

        return dao.findPage(sql.toString(), params.toArray(), pageable, DocBaseInfo.class);
    }

    @Override
    public DocBaseInfo findById(String id) {
        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<>();

        sql.append("select");
        sql.append("  a.id, a.title, a.summary, a.type, a.create_time, a.view_count, a.user_id,");
        sql.append("  b.thum_path, b.swf_path, b.pages, b.ext, b.con_state, c.nick_name");
        sql.append(" from doc_base_info a");
        sql.append(" left join doc_file_info b ON b.id = a.file_id");
        sql.append(" left join user_info c ON c.id = a.user_id");
        sql.append(" where a.id = ?");

        params.add(id);

        List<DocBaseInfo> list = dao.findList(sql.toString(), params.toArray(), DocBaseInfo.class);

        return list != null && !list.isEmpty() ? list.get(0) : null;
    }

}
