package cn.edu.hziee.dash.mapper;

import cn.edu.hziee.dash.entity.Repos;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Mapper
@Repository
public interface ReposMapper {
    List<Repos> findAllByUser(Integer uid);

    Repos findById(Integer rid);

    void addRepos(Repos repos);

    void delRepos(Integer rid);

    void editTime(Integer rid, Date date);
}
