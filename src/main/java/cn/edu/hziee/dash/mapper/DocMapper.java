package cn.edu.hziee.dash.mapper;

import cn.edu.hziee.dash.entity.Doc;
import cn.edu.hziee.dash.entity.Version;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Mapper
@Repository
public interface DocMapper {
    List<Doc> findAllByRepos(Integer rid);

    Doc findById(Integer did);

    void createDoc(Doc doc);

    void linkRepos(Integer rid, Integer did);

    void delDoc(Integer did);

    void editTime(Integer did, Date date);

    Version getLatestVersion(Integer did);

    Integer getRidByDid(Integer did);

}
