package info.archinnov.achilles.demo.music.constants;

public enum Rating {

    UNDEFINED,
    ZERO,
    ONE,
    ONE_HALF,
    TWO,
    TWO_HALF,
    THREE,
    THREE_HALF,
    FOUR,
    FOUR_HALF,
    FIVE;

    public static Rating getRating(Long positiveVotes, Long negativeVotes)
    {
        Rating rating;
        long sum = positiveVotes + negativeVotes;
        int ratio = sum == 0 ? -1 : (int) (positiveVotes * 10 / sum);
        switch (ratio) {
            case 0:
                rating = ZERO;
                break;
            case 1:
                rating = ONE;
                break;
            case 2:
                rating = ONE_HALF;
                break;
            case 3:
                rating = TWO;
                break;
            case 4:
                rating = TWO_HALF;
                break;
            case 5:
                rating = THREE;
                break;
            case 6:
                rating = THREE_HALF;
                break;
            case 7:
                rating = FOUR;
                break;
            case 8:
                rating = FOUR_HALF;
                break;
            case 9:
            case 10:
                rating = FIVE;
                break;

            default:
                rating = UNDEFINED;
                break;
        }
        return rating;
    }
}
