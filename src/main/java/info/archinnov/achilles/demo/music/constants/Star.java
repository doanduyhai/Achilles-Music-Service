package info.archinnov.achilles.demo.music.constants;

public enum Star {

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

    public static Star getRating(Long positiveVotes, Long negativeVotes)
    {
        Star star;
        long sum = positiveVotes + negativeVotes;
        int ratio = sum == 0 ? -1 : (int) (positiveVotes * 10 / sum);
        switch (ratio) {
            case 0:
                star = ZERO;
                break;
            case 1:
                star = ONE;
                break;
            case 2:
                star = ONE_HALF;
                break;
            case 3:
                star = TWO;
                break;
            case 4:
                star = TWO_HALF;
                break;
            case 5:
                star = THREE;
                break;
            case 6:
                star = THREE_HALF;
                break;
            case 7:
                star = FOUR;
                break;
            case 8:
                star = FOUR_HALF;
                break;
            case 9:
            case 10:
                star = FIVE;
                break;

            default:
                star = UNDEFINED;
                break;
        }
        return star;
    }
}
