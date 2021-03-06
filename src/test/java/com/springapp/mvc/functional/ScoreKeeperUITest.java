package com.springapp.mvc.functional;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ScoreKeeperUITest {
    private static final String BASE_URL = "http://localhost:8080/";
    public static final String PLAYER_ONE_BUTTON_SELECTOR = "[data-qa=player-one-button]";
    public static final String PLAYER_TWO_BUTTON_SELECTOR = "[data-qa=player-two-button]";
    public static final String SCORE_SELECTOR = "[data-qa=score]";
    private static WebDriver driver;

    private WebElement playerOneButton;
    private WebElement playerTwoButton;
    private WebElement score;
    private WebElement resetButton;

    @BeforeClass
    public static void openDriver() {
        driver = new FirefoxDriver();
    }

    @Before
    public void setUp() throws Exception {
        driver.get(BASE_URL);
        driver.findElement(By.cssSelector("[data-qa=reset-button]")).click();
    }

    @After
    public void tearDown() throws Exception {
        driver.findElement(By.cssSelector("[data-qa=reset-button]")).click();
    }

    @AfterClass
    public static void closeDriver() {
        driver.close();
    }

    @Test
    public void shouldPlayMatchUntilWin() {
        playerOneButton = driver.findElement(By.cssSelector(PLAYER_ONE_BUTTON_SELECTOR));
        score = driver.findElement(By.cssSelector(SCORE_SELECTOR));
        assertScoreIs("0/0");

        playerOneButton.click();
        score = driver.findElement(By.cssSelector(SCORE_SELECTOR));
        assertScoreIs("15/0");

        playerTwoScores();
        assertScoreIs("15/15");

        playerOneScores();
        assertScoreIs("30/15");

        playerOneScores();
        assertScoreIs("40/15");

        playerTwoScores();
        playerTwoScores();
        assertScoreIs("Deuce");

        playerOneScores();
        assertScoreIs("Advantage/-");

        playerOneScores();
        assertThat(score.getText(), is("Game - Player One"));

        checkButtonsAreDisabled();

        resetScore();
        assertScoreIs("0/0");

        checkButtonsAreEnabled();
    }

    private void resetScore() {
        resetButton = driver.findElement(By.cssSelector("[data-qa=reset-button]"));
        resetButton.click();
        score = driver.findElement(By.cssSelector(SCORE_SELECTOR));
    }

    private void assertScoreIs(String value) {
        assertThat(score.getText(), is(value));
    }

    private void playerOneScores() {
        playerOneButton = driver.findElement(By.cssSelector(PLAYER_ONE_BUTTON_SELECTOR));
        playerOneButton.click();
        score = driver.findElement(By.cssSelector(SCORE_SELECTOR));
    }

    private void playerTwoScores() {
        playerTwoButton = driver.findElement(By.cssSelector(PLAYER_TWO_BUTTON_SELECTOR));
        playerTwoButton.click();
        score = driver.findElement(By.cssSelector(SCORE_SELECTOR));
    }

    private void checkButtonsAreDisabled() {
        playerOneButton = driver.findElement(By.cssSelector(PLAYER_ONE_BUTTON_SELECTOR));
        playerTwoButton = driver.findElement(By.cssSelector(PLAYER_TWO_BUTTON_SELECTOR));
        assertThat(playerOneButton.isEnabled(), is(false));
        assertThat(playerTwoButton.isEnabled(), is(false));
    }

    private void checkButtonsAreEnabled() {
        playerOneButton = driver.findElement(By.cssSelector(PLAYER_ONE_BUTTON_SELECTOR));
        playerTwoButton = driver.findElement(By.cssSelector(PLAYER_TWO_BUTTON_SELECTOR));
        assertThat(playerOneButton.isEnabled(), is(true));
        assertThat(playerTwoButton.isEnabled(), is(true));
    }
}