package com.bside.gamjajeon.domain.user.dto.request;

import com.bside.gamjajeon.domain.user.entity.Hashtag;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecordRequest {

    private Long recordId;

    @NotNull
    private Long userId;

    private String content;

    private int moodType;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date recordDate;

    private List<String> hashtagList;
}
