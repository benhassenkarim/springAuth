package com.scalablescripts.auth.model;

import java.time.LocalDateTime;

public record Token(String refreshToken, LocalDateTime issuedAt,LocalDateTime expiredAt) {
}
