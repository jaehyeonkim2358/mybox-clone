package com.clonecoding.myboxclone.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class ResponseBodyDTO {
    private final String message;
    private final Object data;
}
