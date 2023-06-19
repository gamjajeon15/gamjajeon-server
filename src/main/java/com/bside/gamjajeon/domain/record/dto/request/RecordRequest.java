package com.bside.gamjajeon.domain.record.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecordRequest {

    private Long recordId;

    private Long userId;

    private String content;

    private int moodType;

    @NotNull()
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date recordDate;

    @Size(min=0, max=5)
    private List<String> hashtagList;
}
