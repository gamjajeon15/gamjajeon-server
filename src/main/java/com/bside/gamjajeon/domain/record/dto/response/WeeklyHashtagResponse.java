package com.bside.gamjajeon.domain.record.dto.response;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyHashtagResponse {
	private List<String> potatoTags = new ArrayList<>();
	private List<String> sweetPotatoTags = new ArrayList<>();
	private int totalPotato;
	private int totalSweetPotato;
}
