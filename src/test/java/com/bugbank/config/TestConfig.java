package com.bugbank.config;

public final class TestConfig {
  public static final String BASE_URL = System.getProperty("baseUrl",
      "https://smartbank-j2m0.onrender.com/");
  public static final String EMAIL = System.getProperty("email", "prtwo@gmail.com");
  public static final String PASSWORD = System.getProperty("password", "Pleasework@05");
  public static final long PAGE_LOAD_TIMEOUT_SECONDS =
      Long.getLong("pageLoadTimeoutSeconds", 240L);
  public static final long WAIT_TIMEOUT_SECONDS = Long.getLong("waitTimeoutSeconds", 30L);
  public static final long LONG_WAIT_SECONDS = Long.getLong("longWaitSeconds", 240L);
  public static final long ACTION_PAUSE_MILLIS = Long.getLong("actionPauseMillis", 1500L);

  private TestConfig() {
  }
}