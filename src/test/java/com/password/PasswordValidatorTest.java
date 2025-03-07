package com.password;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PasswordValidatorTest {

    @Mock
    private PasswordService passwordService;

    @InjectMocks
    private PasswordValidator passwordValidator;

    public PasswordValidatorTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testValidPassword() {
        when(passwordService.isPasswordInBlacklist("ValidPassword")).thenReturn(false);
        assertTrue(passwordValidator.isValidPassword("ValidPassword"));
    }

    @Test
    public void testShortPassword() {
        assertFalse(passwordValidator.isValidPassword("short"));
    }

    @Test
    public void testNullPassword() {
        assertFalse(passwordValidator.isValidPassword(null));
    }

    @Test
    public void testBlacklistedPassword() {
        when(passwordService.isPasswordInBlacklist("BlacklistedPassword")).thenReturn(true);
        assertFalse(passwordValidator.isValidPassword("BlacklistedPassword"));
    }

    @Test
    public void testVerifyServiceCall() {
        passwordValidator.isValidPassword("TestPassword");
        verify(passwordService).isPasswordInBlacklist(anyString());
    }

    @Test
    public void testCaptureArgument() {
        passwordValidator.isValidPassword("CaptureThis");
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(passwordService).isPasswordInBlacklist(captor.capture());
        assertEquals("CaptureThis", captor.getValue());
    }

    @Test
    public void testConsecutiveCalls() {
        when(passwordService.isPasswordInBlacklist("ConsecutivePassword"))
                .thenReturn(false)
                .thenReturn(true);
        assertTrue(passwordValidator.isValidPassword("ConsecutivePassword"));
        assertFalse(passwordValidator.isValidPassword("ConsecutivePassword"));
    }
}