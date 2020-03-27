package com.rest.api.chat.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    public void createAndValidToken() {
        String userId = "hong";
        String nickName = "홍아저씨";
        String jwt = jwtTokenProvider.generateToken(nickName);
        assertNotNull(jwt);
        String decToken = jwtTokenProvider.getUserNameFromJwt(jwt);

        assertEquals(nickName, decToken);
    }
}
