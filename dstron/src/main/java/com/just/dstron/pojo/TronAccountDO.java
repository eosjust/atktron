package com.just.dstron.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

@Table("tron_account")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TronAccountDO {
    @Id
    private Integer id;

    @Column("prikey")
    private String prikey;

    @Column("base58")
    private String base58;

}