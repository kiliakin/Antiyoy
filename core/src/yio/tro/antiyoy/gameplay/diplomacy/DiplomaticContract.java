package yio.tro.antiyoy.gameplay.diplomacy;

import yio.tro.antiyoy.stuff.Yio;
import yio.tro.antiyoy.stuff.object_pool.ReusableYio;

public class DiplomaticContract implements ReusableYio {

    public static final int DURATION_FRIEND = 12;
    public static final int DURATION_PIECE = 9;
    public static final int DURATION_TRAITOR = 20;

    public static final int TYPE_FRIENDSHIP = 0;
    public static final int TYPE_PIECE = 1;
    public static final int TYPE_BLACK_MARK = 2;
    public static final int TYPE_TRAITOR = 3;

    DiplomaticEntity one;
    DiplomaticEntity two;
    int type;
    int dotations; // if positive then one pays money to two
    int expireCountDown;


    @Override
    public void reset() {
        one = null;
        two = null;
        type = -1;
        dotations = 0;
        expireCountDown = 0;
    }


    void onFirstPlayerTurnEnded() {
        if (expireCountDown > 0) {
            expireCountDown--;

            if (expireCountDown == 0) {
                onCountDownReachedZero();
            }
        }
    }


    public static int getDurationByType(int contractType) {
        switch (contractType) {
            default:
                return -1;
            case TYPE_FRIENDSHIP:
                return DURATION_FRIEND;
            case TYPE_PIECE:
                return DURATION_PIECE;
            case TYPE_BLACK_MARK:
                return -1;
            case TYPE_TRAITOR:
                return DURATION_TRAITOR;
        }
    }


    private void onCountDownReachedZero() {
        one.diplomacyManager.onContractExpired(this);
    }


    public boolean contains(DiplomaticEntity diplomaticEntity) {
        return one == diplomaticEntity || two == diplomaticEntity;
    }


    public boolean equals(DiplomaticEntity one, DiplomaticEntity two, int type) {
        if (type != -1 && this.type != type) return false;
        if (!contains(one)) return false;
        if (!contains(two)) return false;

        return true;
    }


    public String getDotationsStringFromEntityPerspective(DiplomaticEntity entity) {
        int dotations = getDotationsFromEntityPerspective(entity);

        if (dotations > 0) {
            return "+" + dotations;
        }

        return "" + dotations;
    }


    public int getDotationsFromEntityPerspective(DiplomaticEntity entity) {
        if (entity == one) {
            return -dotations;
        }

        if (entity == two) {
            return dotations;
        }

        return 0;
    }


    public int getOneColor() {
        if (one == null) {
            return -1;
        }

        return one.color;
    }


    public int getTwoColor() {
        if (two == null) {
            return -1;
        }

        return two.color;
    }


    @Override
    public String toString() {
        return "[Contract:" +
                " type=" + type +
                " one=" + one +
                " two=" + two +
                " dotations=" + dotations +
                "]";
    }


    public void setOne(DiplomaticEntity one) {
        this.one = one;
    }


    public void setTwo(DiplomaticEntity two) {
        this.two = two;
    }


    public void setType(int type) {
        this.type = type;
    }


    public void setDotations(int dotations) {
        this.dotations = dotations;
    }


    public void setExpireCountDown(int expireCountDown) {
        this.expireCountDown = expireCountDown;
    }


    public int getExpireCountDown() {
        return expireCountDown;
    }
}
