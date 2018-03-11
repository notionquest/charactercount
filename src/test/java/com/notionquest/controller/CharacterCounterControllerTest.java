package com.notionquest.controller;

import com.notionquest.CharacterCounterApplication;
import com.notionquest.model.ExceptionResponse;
import com.notionquest.model.MixStrings;
import com.notionquest.model.MixStringsOutcome;
import com.notionquest.util.Constants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.Assert.assertEquals;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CharacterCounterApplication.class)
@TestPropertySource(properties = {"management.port=0"})
public class CharacterCounterControllerTest {

    @LocalServerPort
    private int port;

    @Value("${local.management.port}")
    private int mgt;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void shouldReturn200WhenTwoStringsAreCompared() throws Exception {
        String s1 = "my&friend&Paul has heavy hats! &";
        String s2 = "my friend John has many many friends &";

        MixStrings mixStrings = new MixStrings();
        mixStrings.setStrings(Arrays.asList(s1, s2));

        ResponseEntity<MixStringsOutcome> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + Constants.CHARACTER_COUNT_URI,mixStrings  ,MixStringsOutcome.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals("2:nnnnn/1:aaaa/1:hhh/2:mmm/2:yyy/2:dd/2:ff/2:ii/2:rr/=:ee/=:ss", entity.getBody().getMixString());
    }

    @Test
    public void shouldReturn200WhenStringsHaveUpperCaseOnly() throws Exception {
        String s1 = "AAA";
        String s2 = "BBB";

        MixStrings mixStrings = new MixStrings();
        mixStrings.setStrings(Arrays.asList(s1, s2));

        ResponseEntity<MixStringsOutcome> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + Constants.CHARACTER_COUNT_URI,mixStrings  ,MixStringsOutcome.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals("", entity.getBody().getMixString());
    }

    @Test
    public void shouldReturn200WhenStringsHaveSpecialCharactersOnly() throws Exception {
        String s1 = "&&&&***%%%";
        String s2 = "(())*(*&";

        MixStrings mixStrings = new MixStrings();
        mixStrings.setStrings(Arrays.asList(s1, s2));

        ResponseEntity<MixStringsOutcome> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + Constants.CHARACTER_COUNT_URI,mixStrings  ,MixStringsOutcome.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals("", entity.getBody().getMixString());
    }

    @Test
    public void shouldReturn200WhenThreeStringsAreCompared() throws Exception {
        String s1 = "my&friend&Paul has heavy hats! &";
        String s2 = "my friend John has many many friends &";
        String s3 = "my friend John has many many friends &";

        MixStrings mixStrings = new MixStrings();
        mixStrings.setStrings(Arrays.asList(s1, s2, s3));

        ResponseEntity<MixStringsOutcome> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + Constants.CHARACTER_COUNT_URI,mixStrings  ,MixStringsOutcome.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals("2,3:nnnnn/1:aaaa/1:hhh/2,3:mmm/2,3:yyy/2,3:dd/2,3:ff/2,3:ii/2,3:rr/=:ee/=:ss", entity.getBody().getMixString());

    }

    @Test
    public void shouldReturn400WhenStringsAreEmpty() throws Exception {

        MixStrings mixStrings = new MixStrings();
        mixStrings.setStrings(Arrays.asList());

        ResponseEntity<ExceptionResponse> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + Constants.CHARACTER_COUNT_URI,mixStrings  ,ExceptionResponse.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(entity.getBody().getErrors(), containsInAnyOrder(Constants.ATLEAST_TWO_STRINGS_PRESENT_IN_INPUT));

    }

    @Test
    public void shouldReturn400WhenStringsAreNull() throws Exception {

        MixStrings mixStrings = new MixStrings();
        mixStrings.setStrings(null);

        ResponseEntity<ExceptionResponse> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + Constants.CHARACTER_COUNT_URI,mixStrings  ,ExceptionResponse.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(entity.getBody().getErrors(), containsInAnyOrder(Constants.ERROR_INPUT_STR_NULL));

    }

}
