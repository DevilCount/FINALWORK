package com.lis.hl7.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Hl7MessageSegmentVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long messageId;

    private String segmentName;

    private Integer segmentSeq;

    private String segmentContent;

    private String parsedData;

    private LocalDateTime createTime;
}
