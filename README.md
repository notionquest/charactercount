# Find maximum alphabet count, sort and format the string

## Control Flow:-

1. Get the lower case alphabets
2. Count the alphabets and find the maximum for each alphabets
3. Sort by count in descending order, and ascending order by alphabet when the count is same
4. Format the string

## Classes:-

1. BaseCharacterCounter - Abstract class which has some base methods for filtering, counting and finding maximum
2. CharacterCounterService - Main orchestrator class which executes the steps sequentially
3. MixSortingService - Sorts the alphabets
4. SimpleFormattingService - Formats the sorted map

## Sample Input JSON:-
```json
{
	"strings": ["my&friend&Paul has heavy hats! &", "my friend John has many many friends &"]
}
```
## Output JSON:-

```json
{
    "mixString": "2:nnnnn/1:aaaa/1:hhh/2:mmm/2:yyy/2:dd/2:ff/2:ii/2:rr/=:ee/=:ss"
}
```

## To execute the unit/integration test:-

    mvn clean package

## Test coverage

![Test Coverage Report](https://github.com/notionquest/characterscount/blob/master/Test_Coverage_Report.JPG)