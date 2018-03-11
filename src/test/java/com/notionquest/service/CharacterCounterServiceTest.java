package com.notionquest.service;

import com.notionquest.ApplicationTestConfig;
import com.notionquest.model.CharacterCounterDTO;
import com.notionquest.util.Constants;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApplicationTestConfig.class)
public class CharacterCounterServiceTest {

    @Autowired
    private CharacterCounterService characterCounterService;

    @Rule
    public ExpectedException thrown= ExpectedException.none();

    //@Before
    /*public void init() {
        characterCounterService = new CharacterCounterService();
    }*/

    @Test
    public void shouldRemoveUpperCaseCharacters() {
        assertThat(characterCounterService.getLowercaseAlphabets(Arrays.asList("LS")), containsInAnyOrder(""));
    }

    @Test
    public void shouldGetLowercaseCharacters() {
        assertThat(characterCounterService.getLowercaseAlphabets(Arrays.asList("ab", "fs A b")), containsInAnyOrder("ab", "fsb"));
    }

    @Test
    public void shouldGetLowercaseCharactersRemovingSpecialCharacters() {
        assertThat(characterCounterService.getLowercaseAlphabets(Arrays.asList("bc", "df%^&*($! A&%$£ b")), containsInAnyOrder("bc", "dfb"));
    }

    @Test
    public void shouldGetCharactersCount() {
        Map<Character, Long> charCountMap = characterCounterService.getCharacterCount("ddfb");
        assertEquals(new Long(2), charCountMap.get('d'));
        assertEquals(new Long(1), charCountMap.get('f'));
        assertEquals(new Long(1), charCountMap.get('b'));
    }

    @Test
    public void shouldFindMaximumForAlphabets() {
        Map<Character, Long> alphabetCountMap = characterCounterService.getCharacterCount("ddfb");
        Map<Character, CharacterCounterDTO> maxCharCount = characterCounterService.findMaximumCountForAlphabets(alphabetCountMap, null, 1);
        assertNotNull(maxCharCount);
        assertEquals(new Long(2), maxCharCount.get('d').getAlphabetCount());
        assertThat(maxCharCount.get('d').getStringNames(), containsInAnyOrder(1));
        assertNull(maxCharCount.get('f'));
        assertNull(maxCharCount.get('b'));
    }

    @Test
    public void shouldFindMaximumForTwoAlphabets() {
        Map<Character, Long> alphabetCountMap1 = characterCounterService.getCharacterCount("ddfb");
        Map<Character, CharacterCounterDTO> maxCharCount = characterCounterService.findMaximumCountForAlphabets(alphabetCountMap1, null, 1);
        Map<Character, Long> alphabetCountMap2 = characterCounterService.getCharacterCount("dddfb");
        maxCharCount = characterCounterService.findMaximumCountForAlphabets(alphabetCountMap2, maxCharCount, 2);
        assertNotNull(maxCharCount);
        assertEquals(new Long(3), maxCharCount.get('d').getAlphabetCount());
        assertThat(maxCharCount.get('d').getStringNames(), containsInAnyOrder(2));
        assertNull(maxCharCount.get('f'));
        assertNull(maxCharCount.get('b'));
    }

    @Test
    public void shouldFindMaximumForTwoAlphabetsWhichHasSomeEqualNumbers() {
        Map<Character, Long> alphabetCountMap1 = characterCounterService.getCharacterCount("ddfbccc");
        Map<Character, CharacterCounterDTO> maxCharCount = characterCounterService.findMaximumCountForAlphabets(alphabetCountMap1, null, 1);
        Map<Character, Long> alphabetCountMap2 = characterCounterService.getCharacterCount("dddfbccc");
        maxCharCount = characterCounterService.findMaximumCountForAlphabets(alphabetCountMap2, maxCharCount, 2);
        assertNotNull(maxCharCount);
        assertEquals(new Long(3), maxCharCount.get('d').getAlphabetCount());
        assertThat(maxCharCount.get('d').getStringNames(), containsInAnyOrder(2));
        assertNull(maxCharCount.get('f'));
        assertNull(maxCharCount.get('b'));

        assertEquals(new Long(3), maxCharCount.get('c').getAlphabetCount());
        assertThat(maxCharCount.get('c').getStringNames(), containsInAnyOrder(1, 2));

    }


    @Test
    public void shouldFindMaximumForThreeAlphabets() {
        Map<Character, Long> alphabetCountMap1 = characterCounterService.getCharacterCount("ddfb");
        Map<Character, CharacterCounterDTO> maxCharCount = characterCounterService.findMaximumCountForAlphabets(alphabetCountMap1, null, 1);
        Map<Character, Long> alphabetCountMap2 = characterCounterService.getCharacterCount("dddfb");
        maxCharCount = characterCounterService.findMaximumCountForAlphabets(alphabetCountMap2, maxCharCount, 2);
        Map<Character, Long> alphabetCountMap3 = characterCounterService.getCharacterCount("dddaaabbccc");
        maxCharCount = characterCounterService.findMaximumCountForAlphabets(alphabetCountMap3, maxCharCount, 3);
        assertNotNull(maxCharCount);
        assertEquals(new Long(3), maxCharCount.get('d').getAlphabetCount());
        assertThat(maxCharCount.get('d').getStringNames(), containsInAnyOrder(2, 3));
        assertNull(maxCharCount.get('f'));

        assertEquals(new Long(3), maxCharCount.get('a').getAlphabetCount());
        assertThat(maxCharCount.get('a').getStringNames(), containsInAnyOrder(3));

        assertEquals(new Long(2), maxCharCount.get('b').getAlphabetCount());
        assertThat(maxCharCount.get('b').getStringNames(), containsInAnyOrder(3));

        assertEquals(new Long(3), maxCharCount.get('c').getAlphabetCount());
        assertThat(maxCharCount.get('c').getStringNames(), containsInAnyOrder(3));

    }

    @Test
    public void shouldThrowExceptionForNullString() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(Constants.ERROR_INPUT_STR_NULL);
        characterCounterService.getMix(null);
    }

    @Test
    public void shouldThrowExceptionForEmptyArray() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(Constants.ATLEAST_TWO_STRINGS_PRESENT_IN_INPUT);
        characterCounterService.getMix(Arrays.asList());
    }

    @Test
    public void shouldThrowExceptionForOneString() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(Constants.ATLEAST_TWO_STRINGS_PRESENT_IN_INPUT);
        characterCounterService.getMix(Arrays.asList("s1"));
    }

    //Example 1 in PDF
    @Test
    public void shouldGetMixForTwoStrings() {
        String s1 = "my&friend&Paul has heavy hats! &";
        String s2 = "my friend John has many many friends &";
        assertEquals("2:nnnnn/1:aaaa/1:hhh/2:mmm/2:yyy/2:dd/2:ff/2:ii/2:rr/=:ee/=:ss", characterCounterService.getMix(Arrays.asList(s1, s2)));
    }

    //Example 2 in PDF
    @Test
    public void shouldGetMixForTwoStrings2() {
        String s1 = "mmmmm m nnnnn y&friend&Paul has heavy hats! &";
        String s2 = "my frie n d Joh n has ma n y ma n y frie n ds n&";
        assertEquals("1:mmmmmm/=:nnnnnn/1:aaaa/1:hhh/2:yyy/2:dd/2:ff/2:ii/2:rr/=:ee/=:ss", characterCounterService.getMix(Arrays.asList(s1, s2)));
    }

    //Example 3 in PDF : Expected result is wrong in the example. The alphabet r should come first compared to t
    @Test
    public void shouldGetMixForTwoStrings3() {
        String s1 = "Are the kids at home? aaaaa fffff";
        String s2 = "Yes they are here! aaaaa fffff";
        assertEquals("=:aaaaaa/2:eeeee/=:fffff/2:rr/1:tt/=:hh", characterCounterService.getMix(Arrays.asList(s1, s2)));
    }


    @Test
    public void shouldGetMixForTwoStringsInBetweenEquals() {
        String s1 = "mmmmm m nnnnn y&friend&Paul has heavy hats! &xxx";
        String s2 = "my frie n d Joh n has ma n y ma n y frie n ds n&xxx";
        assertEquals("1:mmmmmm/=:nnnnnn/1:aaaa/1:hhh/2:yyy/=:xxx/2:dd/2:ff/2:ii/2:rr/=:ee/=:ss", characterCounterService.getMix(Arrays.asList(s1, s2)));
    }

    @Test
    public void shouldGetMixForThreeStrings() {
        String s1 = "my&friend&Paul has heavy hats! &";
        String s2 = "my friend John has many many friends &";
        String s3 = "my friend John has many many friends &";
        assertEquals("2,3:nnnnn/1:aaaa/1:hhh/2,3:mmm/2,3:yyy/2,3:dd/2,3:ff/2,3:ii/2,3:rr/=:ee/=:ss", characterCounterService.getMix(Arrays.asList(s1, s2, s3)));
    }
}
