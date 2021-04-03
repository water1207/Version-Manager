package cn.edu.hziee.dash.mapper;

import cn.edu.hziee.dash.entity.Version;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VersionMapper {
    Version findById(Integer vid);

    void addVersion(Version version);

    void linkDoc(Integer did, Integer vid);

    List<Version> findAllByDoc(Integer did);
}