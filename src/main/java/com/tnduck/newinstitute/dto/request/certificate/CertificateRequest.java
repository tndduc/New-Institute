package com.tnduck.newinstitute.dto.request.certificate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class CertificateRequest{
    private String userId;
    private String courseId;
    private String completedPoint;
}
