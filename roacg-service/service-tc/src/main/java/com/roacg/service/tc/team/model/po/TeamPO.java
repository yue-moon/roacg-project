package com.roacg.service.tc.team.model.po;

import com.roacg.core.model.db.BaseEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * 团队表
 * 表示一个翻译协同组的团队基本信息
 */
@Table(name = "tb_tc_team")
@Entity
@Data
public class TeamPO extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, columnDefinition = "bigint(20)")
    private Long teamId;

    @Column(nullable = false)
    private String teamName;

    //团队简介 varchar最多85汉字
    @Column(columnDefinition = "varchar(255)")
    private String teamProfile;

    //团队说明 text 最大2w+汉字, 但是这个字段用不了这么多. 一两千就够了。
    @Column(columnDefinition = "text")
    private String teamDescription;

    //团队等级,根据等级 相应的权限也有所不同
    @Column(columnDefinition = "tinyint(1)", nullable = false)
    private Integer teamGrade;

    //团队当前人数
    @Column(nullable = false)
    private Integer teamSize;

    //团队可创建的项目数量上限
    @Column(nullable = false)
    private Integer projectCap;

}