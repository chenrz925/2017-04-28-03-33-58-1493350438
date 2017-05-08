public class BowlingGame {

    static final String charset = "0123456789X/-|";

    public int getBowlingScore(String bowlingCode) {
        int counter = 0;
        int frame = 0;
        int ball = 0;
        char[] bowlingCodeCharArray = bowlingCode.toCharArray();
        int[] needNext = new int[10];
        int[] frameScore = new int[12];
        int[] ballScore = new int[22];
        for (int i = 0; i < ballScore.length; ++i) ballScore[i] = -1;
        for (; ; ) {
            if (frame > 10)
                break;
            if (frame > 9) {
                if (bowlingCodeCharArray.length - counter == 2) {
                    frameScore[frame] = charset.indexOf(bowlingCodeCharArray[counter]);
                    frameScore[frame + 1] = charset.indexOf(bowlingCodeCharArray[counter + 1]);
                    ballScore[frame * 2] = frameScore[frame];
                    ballScore[frame * 2 + 1] = frameScore[frame + 1];
                    break;
                }
            }
            char now = bowlingCodeCharArray[counter];
            //System.out.println(now);
            if (charset.indexOf(now) < 10) {
                frameScore[frame] += charset.indexOf(now);
                ballScore[ball] = charset.indexOf(now);
                ball++;
            }
            else if (now == 'X') {
                frameScore[frame] += 10;
                needNext[frame] = (1 << 1) + 1;
                ballScore[ball] = 10;
                ball += 2;
            } else if (now == '/') {
                ballScore[ball] = 10 - frameScore[frame];
                ball++;
                frameScore[frame] = 10;
                needNext[frame] = 1;
            } else if (now == '-') {
                ballScore[ball] = 0;
                ball++;
            }
            else if (now == '|') {
                if (bowlingCodeCharArray[counter + 1] == '|')
                    counter++;
                frame++;
            }
            counter++;
            if (counter >= bowlingCodeCharArray.length)
                break;
        }
        for (int i = 0; i < 12; ++i) {
            System.out.print(frameScore[i]);
            System.out.print(i == 11 ? '\n' : ' ');
        }
        for (int i = 0; i < 10; ++i) {
            System.out.print(Integer.toBinaryString(needNext[i]));
            System.out.print(i == 9 ? '\n' : ' ');
        }
        for (int i = 0; i < 22; ++i) {
            System.out.print(ballScore[i]);
            System.out.print(i == 21 ? '\n' : ' ');
        }
        /*out:
        for (; ; ) {
            in:
            for (int i = 0; i < 10; ++i) {
                if (i == 9 && needNext[i] != 0) {
                    frameScore[i] += frameScore[i + 1] + frameScore[i + 2];
                    needNext[i] = 0;
                    break in;
                }
                if (i == 8 && needNext[i] != 0) {
                    if ((needNext[i] & (1 << 1)) != 0) {
                        frameScore[i] += frameScore[i + 2];
                        needNext[i] &= ~(1 << 1);
                    }
                }
                if (needNext[i] == 0) {
                    if (i > 0 && (needNext[i - 1] & 1) != 0) {
                        frameScore[i - 1] += frameScore[i];
                        needNext[i - 1] &= ~1;
                    }
                    if (i > 1 && (needNext[i - 2] & (1 << 1)) != 0) {
                        frameScore[i - 2] += frameScore[i];
                        needNext[i - 2] &= ~(1 << 1);
                    }
                }
            }
            int judge = 0;
            for (int i : needNext)
                judge |= i;
            if (judge == 0)
                break out;
        }*/
        int[] backup = new int[12];
        for (int i = 0; i < 12; i++) {
            backup[i] = frameScore[i];
        }
        for (int i = 0; i < 10; ++i) {
            if (needNext[i] != 0) {
                int need;
                if (needNext[i] == 3)
                    need = 2;
                else if (needNext[i] == 1)
                    need = 1;
                else need = 0;
                int needBall = 2 * i + 2;
                while (need != 0) {
                    if (ballScore[needBall] > -1) {
                        frameScore[i] += ballScore[needBall];
                        need--;
                    }
                    needBall++;
                }
            }
        }
        int sum = 0;
        for (int i = 0; i < 10; ++i)
            sum += frameScore[i];
        for (int i = 0; i < 12; ++i) {
            System.out.print(frameScore[i]);
            System.out.print(i == 11 ? '\n' : ' ');
        }
        for (int i = 0; i < 10; ++i) {
            System.out.print(Integer.toBinaryString(needNext[i]));
            System.out.print(i == 9 ? '\n' : ' ');
        }
        System.out.println(sum);
        return sum;
    }

    public static void main(String[] args) {
        (new BowlingGame()).getBowlingScore("X|X|X|X|X|X|X|X|X|X||XX");
    }
}
